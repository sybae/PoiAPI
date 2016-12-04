POI API
===============================
직사각형, 원형 범위내 POI 정보를 조회하는 API를 제공합니다.

Spring Boot + Embedded Database H2 사용하였습니다.

공공데이터포털(data.go.kr)의 ‘[전국공중화장실표준데이터](https://www.data.go.kr/subMain.jsp?param=REFUQUdSSURAMTUwMTI4OTI=#/L2NvbW0vY29tbW9uU2VhcmNoL2RhdGFzZXREZXRhaWwkQF4wMTJtMSRAXnB1YmxpY0RhdGFQaz0xNTAxMjg5MiRAXmJybUNkPU9DMDAwMyRAXm9yZ0luZGV4PURBVEFTRVQ=)’를 활용합니다.

csv 데이터로 H2 DB 테이블 생성 및 인덱스를 적용합니다.


## Quick Start

###1. Start
우선적으로 Spring Boot 서버를 구동시킵니다.
* Start Server ```SpringBootInit.java```


###2. Rectangle API
* SCHEME : ```.../rectangle?x1= &y1= &x2= &y2=```
* TEST CASE 1 : [LINK](http://localhost:8080/rectangle?x1=128&y1=34&x2=130&y2=34.7) ```http://localhost:8080/rectangle?x1=128&y1=34&x2=130&y2=34.7```
* TEST CASE 2 : [LINK](http://localhost:8080/rectangle?x1=127.150004228&y1=37.3833556569&x2=127.130609154&y2=37.4140494219) ```http://localhost:8080/rectangle?x1=127.150004228&y1=37.3833556569&x2=127.130609154&y2=37.4140494219```


###3. Circle API
* SCHEME : ```.../circle?x= &y= &radius=```
(※ 'radius' is Meter Unit)
* TEST CASE 1 : [LINK](http://localhost:8080/circle?x=128.44883116420243&y=35.263795115189694&radius=400) ```http://localhost:8080/circle?x=128.44883116420243&y=35.263795115189694&radius=400```
* TEST CASE 2 : [LINK](http://localhost:8080/circle?x=127.150004228&y=37.3833556569&radius=1000) ```http://localhost:8080/circle?x=127.150004228&y=37.3833556569&radius=1000```


###4. JSON Parser
Access [LINK](http://json.parser.online.fr/) ```http://json.parser.online.fr/```

Copy & Paste Result Data.


###5. Check H2 Console
Access [LINK](http://localhost:8080/console/) ```http://localhost:8080/console/```

* Driver Class : ```org.h2.Driver```
* JDBC URL : ```jdbc:h2:mem:testdb```
* User Name: ```sa```
