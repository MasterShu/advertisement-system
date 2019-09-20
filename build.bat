set packageDir=""

echo "creating folder"

RD %packageDir%
MD %packageDir%

echo "execute build"
call mvn clean package -U -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

echo "copying jar file"

copy "ads-eureka\target\ads-eureka-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-gateway\target\ads-gateway-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-binlog-common\target\ads-common-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-binlog-kafka\target\ads-binlog-kafka-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-common\target\ads-common-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-dashboard\target\ads-dashboard-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-search\target\ads-search-1.0-SNAPSHOT.jar" "%packageDir%\"
copy "ads-service\ads-sponsor\target\ads-sponsor-1.0-SNAPSHOT.jar" "%packageDir%\"

echo "Successful!"
