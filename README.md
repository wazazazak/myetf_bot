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
