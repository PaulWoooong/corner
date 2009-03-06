REP=http://dev.bjmaxinfo.com:5180/artifactory/repo

javac -d support/bootstrap support/bootstrap/Grabber.java
java -cp support/bootstrap Grabber $REP/org/apache/ant/ant/1.7.0/ant-1.7.0.jar support/bootstrap/ant-1.7.0.jar
java -cp support/bootstrap Grabber $REP/org/apache/ant/ant-launcher/1.7.0/ant-launcher-1.7.0.jar support/bootstrap/ant-launcher-1.7.0.jar
java -cp support/bootstrap Grabber $REP/org/apache/ant/ant-nodeps/1.7.0/ant-nodeps-1.7.0.jar support/bootstrap/ant-nodeps-1.7.0.jar

java -cp "support/bootstrap/ant-launcher-1.7.0.jar" org.apache.tools.ant.launch.Launcher -lib support/ant-lib $*
