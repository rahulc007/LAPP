#!/bin/bash

#-------------PURPOSE OF THIS SCRIPT------------------
# DEPLOYMENT SCRIPT FOR OPT-NAUKRI BACKEND APPLICATION
# ----- DEPLOYED BY RAJESH K  -----------
# ----- VERSION 1.0 ---------
# ----- DATE: JUN 1ST 2019 -----
JAR_NAME="LappOrderMarkingService-1.1.0.jar"
JAR_PATH="/tmp/lapp-deploy/LappOrderMarkingService-1.1.0.jar"
DEPLOY_PATH="/home/ubuntu/api-order-marking/"
BACKUP_JAR_NAME="LappOrderMarkingService-backup.jar"
if [ -f "$JAR_PATH" ]
then
sudo kill -9 $(ps -ef | grep $JAR_NAME | grep -v 'grep' | awk '{ printf $2 }')
#REMOVE EXISTING JAR FILE
sudo rm -rf $BACKUP_JAR_NAME
sudo mv $JAR_NAME $BACKUP_JAR_NAME
#sudo rm -rf $JAR_NAME
#COPY NEW JAR FILE
sudo cp $JAR_PATH $DEPLOY_PATH
sudo rm -rf $JAR_PATH
nohup java -jar $JAR_NAME  > /dev/null &
echo "\n*********************************************************************"
echo "DEPLOYED SUCCESSFULLY"
echo "***********************************************************************"
echo "PRESS CTRL + C TO EXIT"
else
        echo "*****************************************************"
        echo " PLEASE UPLOAD YOUR NEW JAR FILE UNDER /tmp/lapp-deploy/"
        echo "********************************************************"
fi

