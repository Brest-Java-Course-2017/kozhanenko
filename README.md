**EVENT AGGREGATOR**

This is the basis of EventAggregator application.

In this version you can:
 - add, delete and edit event categories
 - add, delete and edit information about events (all of them have information about time periods of happening)
 - get information about number of events of certain categories that happening in certain intervals (within an hour from now, within 2 hours from now, from now till the end of day, on a specific date)
 - see information about events of certain category that happening in certain intervals (within an hour from now, within 2 hours from now, from now till the end of day, on a specific date)
 
 To start this application you need to have the next development environment : 
 - JDK 8 or higher
 - Git
 - Apache Tomcat Server
 - Maven
 - Node.js at least 6.9.x or higher
 - npm at least 3.x.x or higher
 - Web Browser
 
 To start REST service you need to follow the next steps:
 1. Create new project directory by typing in terminal: **_mkdir newProjectName_**
 2. Move to created directory _**cd newProjectName**_ and clone the project to it _**git clone https://github.com/Brest-Java-Course-2017/kozhanenko.git**_
 3. Move to _kozhanenko/rest-app_ directory _**cd kozhanenko/rest-app**_ and run command **_mvn clean install_**
 4. Start your Apache Tomcat Server **_sudo systemctl start tomcat_**
 5. Open in your web browser Apache Tomcat start page http://server_domain_or_IP:8080 (for example http://localhost:8080 - you could have different) then open Tomcat Web Application Manager **_http://server_domain_or_IP:8080/manager/html_**, select WAR file to deploy: **_newProjectName/kozhanenko/rest-app/target/rest-app-1.0-SNAPSHOT.war_** and push deploy button
 6. After this REST service is available in url **_http://server_domain_or_IP:8080/rest-app-1.0-SNAPSHOT/_**
  
 
 