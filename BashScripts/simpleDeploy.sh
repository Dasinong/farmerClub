#!/bin/sh

# Setup color constants
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

# Verify user inputs
if [ "$#" -ne 1 ]; then
    echo "${RED}Illegal number of parameters${NC}"
	echo "Example usage: ./BashScripts/simpleDeploy.sh [stage|prod]"
	exit
fi

if [ "$1" != "stage" ] && [ "$1" != "prod" ]; then
	echo "${RED}Wrong parameter${NC}"
	echo "Example usage: ./BashScripts/simpleDeploy.sh [stage|prod]"
	exit
fi

# Determine server
if [ "$1" == "prod" ]; then
	SERVER="120.26.208.198"
else
	SERVER="182.254.129.101"
fi

# Check if script is run from directory root
if [[ ! -f "./pom.xml" ]]; then
	echo "${RED}You must run this script from the root of code base${NC}"
	exit
fi

# Parse version number first
VERSION=`xml2 < pom.xml | grep "/project/version" | sed -e 's#.*=\(\)#\1#'`

# Confirm version number
while true; do
	read -p "Do you want to deploy version ${VERSION}? [y/n]" yn
	case $yn in
		[Yy]* ) break;;
		[Nn]* ) exit;;
		* ) echo "Please answer yes or no.";;
	esac
done

# Verify data source
printf "Validate datasource\t"
if [ "$1" == "prod" ]; then
	xml2 < src/main/webapp/WEB-INF/spring/root-context.xml | grep "/database/DataSource.xml" &> /dev/null
	if [ $? -ne 0 ]; then
		echo "${RED}[Fail]${NC}"
		echo "Please use production datasource"
		exit
	fi
else
	xml2 < src/main/webapp/WEB-INF/spring/root-context.xml | grep "/database/StageDataSource.xml" &> /dev/null
	if [ $? -ne 0 ]; then
		echo "${RED}[Fail]${NC}"
		echo "Please use stage datasource"
		exit
	fi
fi
echo "${GREEN}[Success]${NC}"

# Maven install
printf "mvn package\t"
mvn package | grep "BUILD SUCCESS" &> /dev/null
if [ $? -ne 0 ]; then
	echo "${RED}[FAIL]${NC}"
	exit
fi
echo "${GREEN}[SUCCESS]${NC}"

# Verify if IKAnalyzer2012FF_u1.jar exits
printf "Validate IKAnalyzer2012EF_u1.jar\t"
IKANALYZER="target/farmerClub-$VERSION/WEB-INF/lib/IKAnalyzer2012FF_u1.jar"
if [[ ! -f $IKANALYZER ]]; then
	if [ -f src/main/resources/IKAnalyzer2012FF_u1.jar ]; then 
		cp src/main/resources/IKAnalyzer2012FF_u1.jar $IKANALYZER 
	else
		echo "${RED}[Fail]${NC}"
		echo "IKAnalyzer2012FF_u1.jar is not presenet"
		exit
	fi
fi
echo "${GREEN}[Success]${NC}"

# Move war to server
WARFILE="farmerClub-${VERSION}.war"
printf "scp ${WARFILE}\t"
scp target/$WARFILE root@$SERVER:~ &> /dev/null
if [ $? -ne 0 ]; then
	echo "${RED}[FAIL]${NC}"
	exit
fi
echo "${GREEN}[SUCCESS]${NC}"

# copy the uploaded war file to tomcat app base
UPLOADED_WAR_FILE="~/farmerClub-${VERSION}.war"
EXISTING_WAR_FILE="/usr/local/tomcat7/webapps/farmerClub.war"
TIMESTAMP=`date +'%Y-%m-%d-%H-%M-%S'`
BACKUP_FILE="/data/farmerClubBackup/farmerClub-${TIMESTAMP}.war" 
ssh root@$SERVER -t "sudo cp $EXISTING_WAR_FILE $BACKUP_FILE; sudo cp $UPLOADED_WAR_FILE $EXISTING_WAR_FILE"
echo "Remote update ${GREEN}[SUCCESS]${NC}"
