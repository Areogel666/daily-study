#!/bin/sh

base_home='/root/testSh'
app_name='sflcpsbc-1.0'

gclog_file=$base_home/log/gc.log
dump_dir=$base_home/heapdump

pid=`ps -ef|grep ${app_name}|grep -v grep|grep -v restart|awk '{print$2}'`
if [ -n "${pid}" ] ;then
        kill -9 ${pid}
	echo "$app_name stopped successfully!"
        sleep 5
fi

