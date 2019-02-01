#!/usr/bin/env bash

set -x

start-dfs.sh
start-yarn.sh
mapred --daemon start
hadoop dfsadmin -safemode leave
HADOOP_CONF_DIR=/usr/local/Cellar/hadoop/3.1.1/libexec/etc/hadoop \
spark-submit --master yarn \
/usr/local/Cellar/apache-spark/2.4.0/libexec/examples/src/main/python/pi.py 1000
