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
 
 To **start REST service with Apache Tomcat** you need to follow the next steps:
 1. Create new project directory by typing in terminal: **_mkdir newProjectName_**
 2. Move to created directory _**cd newProjectName**_ and clone the project to it _**git clone https://github.com/Brest-Java-Course-2017/kozhanenko.git**_
 3. Move to _kozhanenko/rest-app_ directory _**cd kozhanenko/rest-app**_ and run command **_mvn clean install_**
 4. Start your Apache Tomcat Server **_sudo systemctl start tomcat_**
 5. Open in your web browser Apache Tomcat start page http://server_domain_or_IP:8080 (for example http://localhost:8080 - you could have different) then open Tomcat Web Application Manager **_http://server_domain_or_IP:8080/manager/html_**, select WAR file to deploy: **_newProjectName/kozhanenko/rest-app/target/rest-app-1.0-SNAPSHOT.war_** and push deploy button
 6. After this REST service is available in url **_http://server_domain_or_IP:8080/rest-app-1.0-SNAPSHOT/_**
 
 To **start REST service with Jetty** you need to follow the next steps:
 1. Make steps 1.-3. for starting REST service with Apache Tomcat.
 2. from _kozhanenko/rest-app_ type in terminal **_mvn jetty:run_**
 3. After this REST service is available in url **_http://server_domain_or_IP:8080/rest-app-1.0-SNAPSHOT/_**
  
 To **start js-client** you need to follow the next steps:
 1. Move from cloned project directory (_newProjectName_) to js-client directory by typing in terminal **_cd kozhanenko/js-client_**
 2. Install js-client's dependencies by typing in terminal **_npm install_**
 3. Start js-client by typing in terminal **_npm start_**
 4. js-client is available in your browser in url **_http://server_domain_or_IP:3000_** (for example **_http://localhost:3000_**)
 
 **Important:** testing DB has initial data about events, that happening in 13.03.2017-15.03.2017
 
 You can **test REST service** with the next **curl** commands:
 1. Get all categories: _curl -v localhost:8080/rest-app-1.0-SNAPSHOT/categories_
 2. Get list of categories names with number of events in certain time interval (beginning, end): _curl -v localhost:8080/rest-app-1.0-SNAPSHOT/categories/2222222/99999999999_ 
 3. Add new category: _curl -H "Content-Type: application/json" -X POST -d '{"categoryName":"Дискотека"}' -v localhost:8080/rest-app-1.0-SNAPSHOT/category_
 4. Update category: _curl -X PUT -v localhost:8080/rest-app-1.0-SNAPSHOT/category/1/Party_
 5. Delete category: _curl -X DELETE localhost:8080/rest-app-1.0-SNAPSHOT/category/1_
 6. Get all events: _curl -v localhost:8080/rest-app-1.0-SNAPSHOT/events_
 7. Get events of certain category that happening in certain time interval: _curl -v localhost:8080/rest-app-1.0-SNAPSHOT/events/1/1489438800/1489525199_
 8. Get event by identifier: _curl -v localhost:8080/rest-app-1.0-SNAPSHOT/event/1_
 9. Add new event: _curl -H "Content-Type: application/json" -X POST -d '{ "timePeriods": [ { "timePeriodId": 0, "event": { "eventId": -1, "category": { "categoryId": 1, "categoryName": "Театр" }, "eventName": "sdfasdf", "eventPlace": "asdfasdf" }, "beginning": 1494277260584, "end": 1494709260584 } ] }'-v localhost:8080/rest-app-1.0-SNAPSHOT/event_
 10. Update event:  _curl -X PUT -H "Content-Type: application/json" -d '{ "timePeriods": [ { "timePeriodId": 0, "event": { "eventId": 6, "category": { "categoryId": 5, "categoryName": "Музеи" }, "eventName": "\"Камни Беларуси\"", "eventPlace": "\"Краеведческий музей\"" }, "beginning": 1494536460231, "end": 1494709260231 } ] }' localhost:8080/rest-app-1.0-SNAPSHOT/event/4_
 11. Delete event by identifier: _curl -X DELETE localhost:8080/rest-app-1.0-SNAPSHOT/event/1_