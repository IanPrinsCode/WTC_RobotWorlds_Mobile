[![CodeScene Code Health](https://codescene.wethinkco.de/60/status-badges/code-health)](https://codescene.wethinkco.de/60/analyses/latest/dashboard)
# 0220-robot-worlds
# WeThinkCode_Group_Project

This is Team 0220 Group Project for WeThinkCode_ Year 2

Robot Worlds project, where players can launch different types of robots and issue it commands using a mobile client

To run please follow the instructions:

To run the server:

1. make build - to clean, initalize, and compile project
2. make package - to package project into a jar file with all dependencies
3. make build_docker - to build a docker image of the server
4. make run_default_docker - run the docker image in it's container


To run the mobile client:

1. make init_flutter - to clean project folder and get flutter dependencies
2. make build_client - to build an executable apk
3. install executable apk onto a mobile device or android emulator
4. run the application using the ip address of the server in the docker container


