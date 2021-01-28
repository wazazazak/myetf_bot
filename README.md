나만의 ETF (MYETF)<br>

TELEGRAM을 기반으로 포트폴리오를 설정하여 리밸런싱을 할 수 있고 사람들과 포트폴리오를 공유할 수 있습니다.<br>

local : http://localhost:8000<br>
ec2 : http://15.164.171.236:8000<br>

-- move directory<br>
cd /home/ubuntu/app/step1/myetf_bot<br>

-- project maven build<br>
mvn clean<br>

mvn pakcage<br>

-- move target directory<br>

mv /home/ubuntu/app/step1/myetf_bot/target<br>

-- excute jar file<br>

java -jar MyEtf-0.0.1-SNAPSHOT.jar<br>
