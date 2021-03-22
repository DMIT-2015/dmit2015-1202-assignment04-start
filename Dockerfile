FROM jboss/wildfly:21.0.2.Final
#RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

ADD target/dmit2015-assignment04-start.war /opt/jboss/wildfly/standalone/deployments/

USER root
RUN ln -s -f /usr/share/zoneinfo/MST /etc/localtime
