import sys
import time
import urllib.request
import urllib.parse
from bs4 import BeautifulSoup
import re
import json
import urllib
import datetime
from pymysql import *

city_dict = {'dongcheng': '东城区', 'xicheng': '西城区', 'chaoyang': '朝阳区', 'haidian': '海淀区', 'fengtai': '丰台区',
             'shijingshan': '石景山区', 'tongzhou': '通州区', 'changping': '昌平区', 'daxing': '大兴区', 'shunyi': '顺义区',
             'fangshan': '房山区', 'pinggu': '平谷区', 'huairou': '怀柔区', 'miyun': '密云区', 'yanqing': '延庆区'}


# 价格
def getPrice(data, price):
    for div in data:
        temp_str = str(div.find("span", class_="content__list--item-price"))
        temp_price = re.findall(r'(\d+)', temp_str)
        for i in range(len(temp_price)):
            temp_price[i] = int(temp_price[i])
        price.extend(temp_price)
        # print(re.findall(r'(\d+)', temp_str))


# 楼层
def getHigh(data, high):
    for div in data:
        temp_str = str(div.find("span", class_="hide"))
        temp_high = re.findall(r'(\d+)', temp_str)
        for i in range(len(temp_high)):
            temp_high[i] = int(temp_high[i])
        high.extend(temp_high)


# 姓名
def getName(data, name):
    for div in data:
        temp_str = div.find("a", class_="twoline").string
        temp_str = temp_str.replace("  ", "")
        temp_str = temp_str.replace("\n", '')
        name.append(temp_str)


# 位置
def getPosition(data, position):
    for div in data:
        v = div.find("p", class_="content__list--item--des")
        v1 = v.find_all("a")
        # temp = list()
        temp = ""
        for i in v1:
            # temp.append(i.string)
            temp += i.string
        position.append(temp)


# 得到面积大小
def getSize(data, size):
    for div in data:
        temp_str = str(div.find("p", class_="content__list--item--des"))
        temp = re.findall(r'(\d+\.\d+)㎡', temp_str)
        size.extend(temp)
    for i in range(len(size)):
        size[i] = float(size[i])


# 得到房型
def getShape(data, shape):
    for div in data:
        temp_str = str(div.find("p", class_="content__list--item--des"))
        temp = re.findall(r'\d+室\d+厅\d+卫', temp_str)
        if len(temp) == 0:
            shape.append("")
        else:
            shape.extend(temp)


# 得到查询地点的经纬度
def getPlaceLon(place, city):
    if place == "民族大学":
        place = "中央民族大学"  # 迫不得已
    if city == '':
        urlStart = "https://apis.map.qq.com/ws/geocoder/v1/?address="
        url_p = urllib.parse.quote("北京市" + place)
        # city = urllib.parse.quote("北京市")
        urlEnd = "&key=3HRBZ-5NUWK-65VJN-AARKC-QN6XJ-GDFDS"

        URL = ""
        URL += urlStart
        URL += url_p
        # URL += "region=" + city
        URL += urlEnd
    else:
        urlStart = "https://apis.map.qq.com/ws/geocoder/v1/?address="
        url_p = urllib.parse.quote("北京市" + city_dict[city] + place)
        # city = urllib.parse.quote("北京市")
        urlEnd = "&key=3HRBZ-5NUWK-65VJN-AARKC-QN6XJ-GDFDS"

        URL = ""
        URL += urlStart
        URL += url_p
        # URL += "region=" + city
        URL += urlEnd

    result = urllib.request.urlopen(URL)

    data = json.loads(result.read())

    # print(data)

    return data['result']['location']['lng'], data['result']['location']['lat']


# 传入位置（position）,得到经纬度 location
def getLongitudeAndLat(position, location):
    for p in position:
        urlStart = "https://apis.map.qq.com/ws/geocoder/v1/?address="
        url_p = urllib.parse.quote(p)
        urlEnd = "&key=3HRBZ-5NUWK-65VJN-AARKC-QN6XJ-GDFDS"  # 我自己的密钥

        # 得到完整的URL链接
        URL = ""
        URL += urlStart
        URL += url_p
        URL += urlEnd

        # print("-----------------------")
        #
        # print(URL)
        # 请求
        result = urllib.request.urlopen(URL)

        data = json.loads(result.read())

        # print(data)

        dic = dict()
        dic['lng'] = data['result']['location']['lng']
        dic['lat'] = data['result']['location']['lat']

        location.append(dic)

        time.sleep(0.1)  # 每秒有上限

        # print(data['result']['location']['lng'])
        # print(data['result']['location']['lat'])
        # print(type(data))


def getDistance(location, place, distance, city=''):
    placeLoc = getPlaceLon(place, city)
    # for loc in location:
    urlStart = "https://apis.map.qq.com/ws/distance/v1/matrix/?mode=walking&"
    urlEnd = "&key=3HRBZ-5NUWK-65VJN-AARKC-QN6XJ-GDFDS"

    for loc in location:
        url_ = "from=" + str(placeLoc[1]) + "," + str(placeLoc[0]) + "&to="
        url_ = url_ + str(loc['lat']) + "," + str(loc['lng'])
        url = ""
        url += urlStart
        url += url_
        url += urlEnd
        # print(url)

        result = urllib.request.urlopen(url)
        data = json.loads(result.read())

        # print(data)
        distance.append(data['result']['rows'][0]['elements'][0]['distance'])
        time.sleep(0.5)  # 每秒有上限

    # print(url_)


def connectDataBase(name, position, high, size, shape, price, distance, thisTime, place):
    con = connect(host='localhost', port=3306, user='root', passwd='1780206379', database='JavaWork')
    cur = con.cursor()
    # insert into house values('整租·民族大学家属院 2室1厅 南/北',9500,14,'海淀魏公村民族大学家属院',62.17,'2室1厅1卫','民族大学',905,'2021-10-29');
    for i in range(len(name)):
        try:
            sql = "insert into house values(%(name)s,%(price)s,%(high)s,%(position)s,%(size)s,%(shape)s,%(destination)s,%(distance)s,%(day)s);"
            values = {"name": name[i], "price": price[i], "high": high[i], "position": position[i], "size": size[i],
                      "shape": shape[i], "destination": place
                , "distance": distance[i], "day": str(thisTime)}
            cur.execute(sql, values)
            con.commit()
        except:
            pass

    con.close()
    print("添加数据完成")


def connectL(Lat_Lng):
    con = connect(host='localhost', port=3306, user='root', passwd='1780206379', database='JavaWork')
    cur = con.cursor()

    for lo in Lat_Lng.keys():
        try:
            sql = "insert into Location values (%(po)s,%(Lat)s,%(Lng)s);"
            values = {'po': lo, "Lat": Lat_Lng[lo]['lat'], "Lng": Lat_Lng[lo]['lng']}
            cur.execute(sql, values)
            con.commit()
        except:
            pass

    con.close()


def main(place, city=''):
    url = "https://bj.lianjia.com/zufang"

    if city != '':
        url += ('/' + city)

    url += '/rs'

    # 对汉语进行编码
    url += urllib.parse.quote(place)
    html = urllib.request.urlopen(url, timeout=30)
    soup = BeautifulSoup(html.read().decode('utf-8'), "lxml")

    data1 = soup.find("div", class_="content__list")
    data = data1.find_all("div", class_="content__list--item--main")  # 返回满足条件的【所有】结果，通常用它
    # print(data)

    name = list()
    getName(data, name)
    # print("姓名：", name)

    position = list()
    getPosition(data, position)
    # print("位置：", position)

    high = list()
    getHigh(data, high)
    # print("楼层:", high)

    size = list()
    getSize(data, size)
    # print("面积：", size)

    shape = list()
    getShape(data, shape)
    # print("户型：", shape)

    price = list()
    getPrice(data, price)
    # print("价格：", price)

    location = list()
    getLongitudeAndLat(position, location)  # 经纬度
    #
    distance = list()
    getDistance(location, place, distance, city=city)

    Lat_Lng = dict()
    for i in range(len(position)):
        if position[i] not in Lat_Lng.keys():
            Lat_Lng[position[i]] = location[i]

    placeLoc = getPlaceLon(place, city)
    if place not in Lat_Lng.keys():
        Lat_Lng[place] = {'lng': placeLoc[0], 'lat': placeLoc[1]}

    # print('距离：', distance)

    thisTime = datetime.date.today()

    # print("当前时间", thisTime)

    connectL(Lat_Lng)

    connectDataBase(name, position, high, size, shape, price, distance, thisTime, place)


if __name__ == '__main__':
    # for i in range(1, len(sys.argv)):
    #     place = sys.argv[i]
    #     main(place)
    get_sys = sys.argv[len(sys.argv) - 1]
    sys_res = get_sys.split('+')

    main(place=sys_res[0], city=sys_res[1])
