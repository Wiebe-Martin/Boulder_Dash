


levelname = "map1"

map = ["WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
"WX.....................................W",
"W......................................W",
"W......................................W",
"W......................................W",
"W......................................W",
"W......................................W",
"W......................................W",
"W.......  q.....  q.....  q.....  q....W",
"W.......   .....   .....   .....   ....W",
"W....... d ..... d ..... d ..... d ....W",
"W......................................W",
"W......................................W",
"W......................................W",
"W.......  q.....  q.....  q.....  q....W",
"W.......   .....   .....   .....   ....W",
"W....... d ..... d ..... d ..... d ....W",
"W......................................W",
"W......................................W",
"W......................................W",
"W......................................W",
"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"]
 
mapWidth = len(map[0])
mapHeight = len(map)
 
header = """<?xml version="1.0" encoding="UTF-8"?>
<map version="1.10" tiledversion="1.10.2" orientation="orthogonal" renderorder="left-up" width="{mapWidth}" height="{mapHeight}" tilewidth="32" tileheight="32" infinite="0" nextlayerid="5" nextobjectid="12">
 <tileset firstgid="1" source="sprites/sprites0.tsx"/>
 <layer id="1" name="background" width="{mapWidth}" height="{mapHeight}">
  <data encoding="csv">"""
 
 
xmlResult = header.format(mapWidth=mapWidth, mapHeight=mapHeight) + "\n"
 
for y in range(mapHeight):
    for x in range(mapWidth):
        xmlResult += "61,"
    xmlResult += "\n"
 
 
section2 = """  </data>
 </layer>
 <layer id="2" name="collision" width="{mapWidth}" height="{mapHeight}">
  <data encoding="csv">"""
 
xmlResult += section2.format(mapWidth=mapWidth, mapHeight=mapHeight) + "\n"
 
for y in range(mapHeight):
    for x in range(mapWidth):
        if map[y][x] == "W":
            xmlResult += "64,"
        elif map[y][x] == "w":
            xmlResult += "64,"
        else:
            xmlResult += "0,"
    xmlResult += "\n"
 
section3 = """ </data>
 </layer>
 <layer id="3" name="dirt" width="{mapWidth}" height="{mapHeight}">
  <data encoding="csv">"""
 
xmlResult += section3.format(mapWidth=mapWidth, mapHeight=mapHeight) + "\n"
 
for y in range(mapHeight):
    for x in range(mapWidth):
        if map[y][x] == ".":
            xmlResult += "72,"
        else:
            xmlResult += "0,"
    xmlResult += "\n"
 
 
section4 = """ </data>
 </layer>
 """
xmlResult += section4


 
xmlResult += """<objectgroup id="4" name="entities">"""
 
objectcounter = 1

for y in range(mapHeight):
    for x in range(mapWidth):

        if map[y][x] != "X" and map[y][x] != "d" and map[y][x] != "r" and map[y][x] != "P" and map[y][x] != "q":
            continue

        if map[y][x] == "X":
            gid = 1
        elif map[y][x] == "d":
            gid = 101
        elif map[y][x] == "r":
            gid = 71
        elif map[y][x] == "P":
            gid = 62
        elif map[y][x] == "q":
            gid = 91

        xmlResult += """ <object id="{objectCounter}" gid="{gid}" x="{x}" y="{y}" width="32" height="32"/>""".format(x=x*32, y=y*32 +32, objectCounter=objectcounter, gid=gid)
        xmlResult += "\n"
        objectcounter += 1


xmlResult +="</objectgroup>\n"
xmlResult += "</map>"
print(xmlResult)

with open("../../" + levelname + ".tmx", "w") as f:
    f.write(xmlResult)