# ThingApp Server
This is a base Java Springboot 3 application that demonstrates how to build a functional java backend that utilizes maven, springboot, mybatis and swagger. This application builds to a war so that it may be hosted in a tomcat server although it also can be run without tomcat as springboot can deploy with its own embedded tomcat server.

## Getting Started

### Prerequisites
1. Make sure that you have the Java JDK installed
     - I recommend Java 20 with instructions for install [here](https://docs.oracle.com/en/java/javase/20/install/)
2. Make sure that you have maven installed
     - There is a guid for this [here](https://www.baeldung.com/install-maven-on-windows-linux-mac)
3. Make sure that you have a databse already configured with the proper tables
     - See the database section [here](../../database/README.md)
4. Create a .env file in this directory with your database connection information
     - You can see an example of this in the []`.env.template`](.env.template) file provided

### Running the server
IMPORTANT: There are loads of better ways to do this. I recommend at least some sort of IDE setup. But this will work every time with the bare minimum amount of setup.

1. Build the project
     - NOTE: You only need to do this after making changes to the code or running for the first time
     - From this directory run `mvn -U clean package`
2. Run the application
     - From this directory run`java -jar target/thingapp-0.0.1-SNAPSHOT.war`
     - NOTE: You will probably need to change the file name to whatever the .war file is in the target directory
3. See the application running in the browser
     - See the Swagger page at http://localhost:8080/thingapp/swagger-ui.html
     - See one of the endpoints here http://localhost:8080/thingapp/api/audit