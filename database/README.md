# Database Files

This is where files reside for creating the app database, as well as table creation scripts and eventually version migration scripts as well.

## Getting Started
- Install mysql server wherever you want to run it (I am using Mysql server 8.0)
- Run the database setup script, replace the place-holder password with the actuall password you want for your app user
- Then you can run all the scripts in the tables folder
    - These scripts assume "USE thingappdb;" has already been set
- The server needs to be running and configured properly for the server to come up
- NOTE: Mysql Workbench is great for this, as you can save the login creds to the db and just import and run the .sql files

## Notes For Working With Windows
- First, don't do this on windows.... trust me......
- Secondly wsl has all kinds of issues with netowrking
- I had to do the following to get it to work properly
    1. `sudo service mysql stop`
    2. `sudo vim /etc/mysql/mysql.conf.d/mysql.cnf`
       1. this MAY not exist, if not then just add the lines in `/etc/mysql/my.cnf`
    3. Add the below lines to the file
    4. `sudo usermod -d /var/lib/mysql/ mysql`
    5. `sudo service mysql start`
- I also weirdly had to do `nc -vz 127.0.0.1 3309` and connect to the db in terminal before it worked in springboot `mysql -h127.0.0.1 -uthingappuser -p`

### /etc/mysql/mysql.conf.d/mysql.cnf
```
[mysql]
bind-address = localhost
socket=/var/lib/mysql/mysql.sock
```