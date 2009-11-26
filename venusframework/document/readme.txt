运行命令
mvn -Djetty.port=9999 jetty:run

set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8086,server=y,suspend=n
mvn -Djetty.port=9999 jetty:run