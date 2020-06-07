# tccbootx
TCC分布式事务demo (Spring, Spring Boot, MyBatis, Dubbo, tcc-transaction)

运行方式：<br/>
1. 导入数据库sql文件<br/>
2. 导入Idea工程<br/>
3. 依次运行UserApplication、ItemApplication、OrderApplication、GatewayApplication<br/>
4. 浏览器访问http://localhost:8080/order/createOrder?userId=1&itemId=1&itemAmount=2&redPacketAmount=2000<br/>
 （表示下单的用户、商品、商品数、使用的红包金额）<br/>
5. 后台可看到TCC记录
vmess://ew0KICAidiI6ICIyIiwNCiAgInBzIjogIiIsDQogICJhZGQiOiAiZC5sb3N0c3RhcnNmLnh5eiIsDQogICJwb3J0IjogIjQ0MyIsDQogICJpZCI6ICJiMWFiZjU5MC02NDJiLTExZWEtODI3ZS0wMDE2M2NmOGRmMzciLA0KICAiYWlkIjogIjE2IiwNCiAgIm5ldCI6ICJ3cyIsDQogICJ0eXBlIjogIm5vbmUiLA0KICAiaG9zdCI6ICIiLA0KICAicGF0aCI6ICIvZUROb1BYRXIvIiwNCiAgInRscyI6ICJ0bHMiDQp9
