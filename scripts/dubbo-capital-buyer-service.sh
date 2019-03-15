#!/bin/sh

## java opt
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.191.b12-1.el7_6.x86_64
export JRE_HOME=$JAVA_HOME/jre

SERVICE_NAME="dubbo-capital-buyer-service"
SERVICE_DIR="/opt/jecp/$SERVICE_NAME"
JAR_NAME="$SERVICE_DIR/$SERVICE_NAME.jar"

pid=`ps -ef | grep $SERVICE_NAME | grep java | grep -v "grep" | awk '{print $2}'`
case "$1" in
        start)
                if [ -n "$pid" ]; then 
                    echo "error: the $SERVICE_NAME already started!"
                    echo "pid: $pid"
                    exit 1
                else
                    nohup $JRE_HOME/bin/java -Xms4096M -Xmx4096M -Xss512k -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:PermSize=128M -XX:MaxPermSize=256M -Dlog.dir="$SERVICE_DIR" -jar $JAR_NAME >/dev/null 2>&1 &
                    echo "start $SERVICE_NAME success!"
                      fi
                ;;
        stop)

                if [ "$pid" = "" ]; then
                    echo "$SERVICE_NAME is not running!"
                else
                    echo "$SERVICE_NAME pid is: $pid"
                    kill -9 $pid
                    echo "$SERVICE_NAME has stopped!"
                fi
                ;;
        restart)
                $0 stop
                sleep 2
                $0 start
                ;;
           *)
                $0 stop
                sleep 2
                $0 start
                ;;
esac
exit 0
