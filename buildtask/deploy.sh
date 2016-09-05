#!/usr/bin/env bash

service_name=track-service
host_address=192.168.0.21
uid=root
jar_home=/root/.jenkins/workspace/TRACK/
api_service_path=/usr/esapp/api-services



function package()
{
    echo "building package"
    mvn -Dmaven.test.failure.ignore -Dspring.profiles.active=test clean package
}

function startup()
{
    ssh $uid@$host_address /etc/init.d/track start &
}

function deploy()
{
    echo "shutting down..."
    ssh $uid@$host_address /etc/init.d/track stop
    echo "deploying package to server"
    scp $jar_home/target/track-service-0.0.1.jar $uid@$host_address:$api_service_path
}

function build()
{
    package
    deploy
    startup
}

build