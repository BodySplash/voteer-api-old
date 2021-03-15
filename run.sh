#!/bin/bash

 
mongod=mongod
prog=mongod.sh
RETVAL=0

startIfNotRunning() {
	grep_mongo=`ps aux | grep -v grep | grep "${mongod}"`
    	if [ ${#grep_mongo} -gt 0 ]
	then
		echo "MongoDB is runnig"
	else
		echo "Starting MongoDB"
		`${mongod} --fork`
	fi
} 

startIfNotRunning
mvn clean package
foreman start -p 8182 -e dev.env
