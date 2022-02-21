# Get docker image with tomcat 8 and deploy war into it
FROM tomcat:8
LABEL app=mockchain
COPY target/*.war /usr/local/tomcat/webapps/mockchain.war