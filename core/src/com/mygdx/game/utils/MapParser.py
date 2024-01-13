
map = ["WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
"W...... ..d.r .....r.r....... ....r....W",
"W.rXr...... .........rd..r.... ..... ..W",
"W.......... ..r.....r.r..r........r....W",
"Wr.rr.........r......r..r....r...r.....W",
"Wr. r......... r..r........r......r.rr.W",
"W... ..r........r.....r. r........r.rr.W",
"Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww...r..r.W",
"W. ...r..d. ..r.r..........d.rd...... .W",
"W..d.....r..... ........rr r..r....r...W",
"W...r..r.r..............r .r..r........W",
"W.r.....r........rrr.......r.. .d....r.W",
"W.d.. ..r. .....r.rd..d....r...r..d. ..W",
"W. r..............r r..r........d.....rW",
"W........wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwW",
"W r.........r...d....r.....r...r.......W",
"W r......... r..r........r......r.rr..PW",
"W. ..r........r.....r. ....d...r.rr....W",
"W....rd..r........r......r.rd......r...W",
"W... ..r. ..r.rr.........r.rd...... ..rW",
"W.d.... ..... ......... .r..r....r...r.W",
"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",]
 
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

        if map[y][x] != "X" and map[y][x] != "d" and map[y][x] != "r" and map[y][x] != "P":
            continue

        if map[y][x] == "X":
            gid = 1
        elif map[y][x] == "d":
            gid = 101
        elif map[y][x] == "r":
            gid = 71
        elif map[y][x] == "P":
            gid = 62

        xmlResult += """ <object id="{objectCounter}" gid="{gid}" x="{x}" y="{y}" width="32" height="32"/>""".format(x=x*32, y=y*32 +32, objectCounter=objectcounter, gid=gid)
        xmlResult += "\n"
        objectcounter += 1


xmlResult +="</objectgroup>\n"
xmlResult += "</map>"
print(xmlResult)