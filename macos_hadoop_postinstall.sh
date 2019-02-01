#!/usr/bin/env bash

# run `brew install apache-spark` firstly.

set -x

: ${HADOOP_PWD:=/usr/local/Cellar/hadoop/3.1.1}

cat << EOF > ~/.ssh/config
Host *
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
EOF

cat ~/.ssh/id_rsa.pub >> ~/.ssh/known_hosts

cat << EOF > ${HADOOP_PWD}/libexec/etc/hadoop/core-site.xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
EOF

hdfs namenode -format

echo 'export PATH="/usr/local/sbin:/usr/local/Cellar/hadoop/3.1.1/libexec/etc/hadoop/:$PATH"' >> ~/.bash_profile

cat << EOF > ${HADOOP_PWD}/libexec/etc/hadoop/mapred-site.xml
<configuration>
	<property>
	        <name>mapreduce.framework.name</name>
	        <value>yarn</value>
	    </property>
	    <property>
	        <name>mapreduce.jobhistory.address</name>
	        <value>localhost:10020</value>
	    </property>
	    <property>
	        <name>mapreduce.jobhistory.webapp.address</name>
	        <value>localhost:19888</value>
	    </property>
</configuration>
EOF

cp ${HADOOP_PWD}/libexec/share/hadoop/tools/sls/sample-conf/yarn-site.xml ${HADOOP_PWD}/libexec/etc/hadoop/yarn-site.xml

chmod a+x ${HADOOP_PWD}/libexec/etc/hadoop/*.sh

export PATH="/usr/local/sbin:${HADOOP_PWD}/libexec/etc/hadoop/:${PATH}"

start-dfs.sh

hadoop dfsadmin -safemode leave

hdfs dfs -mkdir -p `echo ~`/input

stop-dfs.sh
