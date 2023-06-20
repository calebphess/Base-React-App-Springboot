# ThingApp Server
This is a base Java Springboot 3 application that demonstrates how to build a functional java backend that utilizes maven, springboot, mybatis and swagger. This application builds to a war so that it may be hosted in a tomcat server although it also can be run without tomcat as springboot can deploy with its own embedded tomcat server.

## Getting Started

### Prerequisites
1. Make sure that you have a database already configured with the proper tables
     - See the database section [here](../../database/README.md)
2. Make sure that you have the Java JDK installed
     - I recommend Java 20 with instructions for install [here](https://docs.oracle.com/en/java/javase/20/install/)
     - TODO: Move java version to whatever the next LTS version will be
3. Make sure that you have maven installed
     - There is a guide for this [here](https://www.baeldung.com/install-maven-on-windows-linux-mac)
     - You can skip the part about installing Java
4. Create a new cert for your dev server to use somewhere on your computer
     - For example: `keytool -genkeypair -alias dev-server -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore dev-server.p12 -validity 3650 -ext san=dns:localhost -storepass password`
          - `password` should actually be a password if you want any security
          - First and Last name will be `localhost`
          - Organizational unit will be `dev`
          - Organization will be `thingapp`
          - City can be `Herndon`
          - State can be `Virginia`
          - Country code can be `US`
          - Then type `yes` and hit enter
          - When asked about the cname type `yes`
     - If you want to make a certificate file that you can share you can do the following
          - `keytool -export -keystore dev-server.p12 -alias dev-server -file devCertificate.crt`
     - If you want to store the cert so you don't get that ugly invalid cert browser waring you can do the following
          - On a mac or windows machine you would just double click the file to add it to your store
               - On mac specifically double click the file
               - If prompted, add it to your `System` keychain
               - Open the `System` keychain and double click the file there (should be the only `Unknown` certificate)
               - Expand the `Trust` section and select `Always Trust` from the top dropdown menu
               - Close the window and your mac should ask for password or fingerprint to save
     - You can see more on setting up https in springboot [here](https://www.thomasvitale.com/https-spring-boot-ssl-certificate/).

5. Create a .env file in this directory (server/thingapp) with your database connection information, and certificate information
     - You can see an example of this in the [`.env.template`](.env.template) file provided
     - I recommend if you are running off of a local database that you use `DB_HOST=localhost`
          - I have encountered some weird issues trying to use loopback `127.0.0.1`
     - If you followed the instructions above for creating a dev-server cert, KEYSTORE_PASSWORD and KEY_PASSWORD should be the same value

### Running the server
IMPORTANT: There are loads of better ways to do this. I recommend at least some sort of IDE setup. But this will work every time with the bare minimum amount of setup.

1. Build the project
     - NOTE: You only need to do this after making changes to the code or running for the first time
     - From this directory run `mvn -U clean package`
2. Run the application
     - From this directory run`java -jar target/thingapp-0.0.1-SNAPSHOT.war`
     - NOTE: You will probably need to change the file name to whatever the .war file is in the target directory
3. See the application running in the browser
     - See the Swagger page at https://localhost:8443/thingapp/api-docs

## Backend workflow / code navigation
### High level overview
- web endpoint <-> biz interface <-> biz <-> service interface <-> service <-> dal <-> dao <-> database
 |
  ---------------------------------- model/VO -------------------------------- <-> database record

- The user token get's created by the UserTokenInterceptor and is always passed around with the request
     - It holds basic user information and roles
     - this is how we do record validation

- The web enpoint handles routing and basic session info
- The biz module handles all business logic like authorization and calls across services
- The service logic handles validation logic before records are submitted to/from the database
- The dal converts the resulting service request into a database request
- The dao is what actually makes the database request

### Other modules / functionality
- BaseRestEndpoint does all of our error handling with the help of the exceptions module
- The util module is for commonly used or complex validation logic
- If there are any asynchronous jobs they would start at the service level and run with the system token instead of a user token, since the system is performing the action automatically

## Personal TODO
 - we will want to add a /user/public endpoint that only returns relevant information for a public user
 - refactor out biz and service logic for all old services
 - need to move auth service to security service
