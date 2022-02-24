# Get docker image with tomcat 8 and deploy war into it
FROM tomcat:8
LABEL app=mockchain
COPY target/*.war /usr/local/tomcat/webapps/mockchain.war

# move the files from webapps.dist to webapps 
#RUN mv /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps/
RUN cd /usr/local/tomcat/webapps.dist
RUN cp -R * ../webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]