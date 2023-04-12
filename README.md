# Base-React-App-Springboot
An example React application with a Java Springboot server connected to a MySQL database

## How to run
 - install maven and JDK 20 from oracle
 - build the war `mvn -U clean package`
  - you may need to change the location of .env in ThingApplication.java
 - then run `java -jar ThingApp-SNAPSHOT-VERSION.war` or whatever it's called in target

## TODO
- update readme
- add comments to the config files to explain what they do since they are "one and done" files
- finish config for db
- do web config
- do swagger config
- build user token interceptor
- build security
- build BaseRESTEndpoint.java
- build user stack web > service interface > service > VO > daoInterface > daoInterfaceXml
- build a UI with a landing page and login/signup/view/edit/delete profile pages
- figure out how to track google login to session management to the UserTokenInterceptor
- add a thing table, backend, grid with retrieveAll, retrieveAllForUser, retrieveById, create, update, delete functionality
- add basic story UI
- add donation functionality stripe backend, a stripe token table and a donation ui
