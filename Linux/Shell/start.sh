#!/bin/bash

base_home='/root/testSh'
app_name='sflcpsbc-1.0'

gclog_file=$base_home/log/gc.log
dump_dir=$base_home/heapdump

nohup java -Xmx1g -Xms1g -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:+PrintGCDetails  -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation  -XX:GCLogFileSize=128M  -XX:NumberOfGCLogFiles=1  -Xloggc:${gclog_file} -XX:HeapDumpPath=${dump_dir} -jar $base_home/app/${app_name}.jar --psbc.config.path=$base_home/config/psbc.properties >> $base_home/log/${app_name}_$(date +'%Y%m%d').log 2>&1 &

echo "$app_name start successfully!"
