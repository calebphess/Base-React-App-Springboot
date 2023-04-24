# Base-React-App-Springboot
An example React application with a Java Springboot server connected to a MySQL database

## Getting Started
You can check out the READMEs below for more information on each section
  - [Server](server/thingapp/README.md)
  - [UI](ui/thing-app/README.md) WIP
  - [Database](database/README.md)

## IDE Recommendations
- VS Code
  - Extension Pack for Java
  - Spring Boot Extension Pack
  - Swagger Viewer
  - MySQL
  - vsc-mybatis
  - ES7+ React/Redux/React-Native snippets
  - WSL (Windows with WSL Only)

## TODO
- update readme
- fix VS Code setup so it actually works
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