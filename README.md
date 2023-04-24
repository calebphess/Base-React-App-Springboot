# Base-React-App-Springboot
An example React application with a Java Springboot server connected to a MySQL database

## Getting Started
You can check out the READMEs below for more information on each section
  - [Server](server/thingapp/README.md)
  - [UI ***WIP***](ui/thing-app/README.md)
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
- investigate [this] (https://www.javainuse.com/spring/boot-jwt) for auth
  - think about if I want to do access/refresh tokens
  - I like single access token better where the server can just pre-emptively expire it maybe
    - Or maybe it's a short token that the server will auto-refresh every time it's used up to a certain period
    - this would be a jwt with a timeout timestamp and an initialLogin timestamp that goes into the user token interceptor
  - on paper this is a good idea but how do I handle auto-refreshing screens
- fix VS Code setup so it actually works
- add comments to the config files to explain what they do since they are "one and done" files
- [DONE] finish config for db
- [DONE] do web config
- do swagger config
- [V1 Done] build user token interceptor
- build the actual interceptor that handles login appropriately
- build security/login
- [V1 DONE] build BaseRESTEndpoint.java
- Take a full pass through audit to make sure it works properly
- [AUDIT DONE] build an example stack web > service interface > service > VO > daoInterface > daoInterfaceXml
- build a UI with a landing page and login/signup/view/edit/delete profile pages
- figure out how to track google login to session management to the UserTokenInterceptor
- add a thing table, backend, grid with retrieveAll, retrieveAllForUser, retrieveById, create, update, delete functionality
- add donation functionality stripe backend, a stripe token table and a donation ui
- add basic story UI
- create an "Authentication Service" that has it's own database optimized for managing sessions and stores users
  - this will help to demonstrate how the UserTokenInterceptor would interact in environments that have their own existing authentication services
  - multiple of these could be build as "Authentication Plugins" to enable deployment in any environment
  - this works by passing some unique identifier (cert subject, session token, etc.) from the web app to the authentication service to authenticate the user
