local : http://localhost:8000
ec2 : http://15.164.171.236:8000

-- move directory
cd /home/ubuntu/app/step1/myetf_bot

-- project maven build
mvn clean

mvn pakcage

-- move target directory

mv /home/ubuntu/app/step1/myetf_bot/target

-- excute jar file

java -jar MyEtf-0.0.1-SNAPSHOT.jar
