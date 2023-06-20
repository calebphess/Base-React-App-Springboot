# Database Files
This is where files reside for creating the app database, as well as table creation scripts and eventually version migration scripts as well.

## Getting Started
- Install mysql server wherever you want to run it (I am using Mysql server 8.0.32) which can be found here: https://dev.mysql.com/downloads/mysql/
- Run the database setup script, replace the place-holder password with the actual password you want for your app user
- Then you can run all the scripts in the tables folder
    - MAKE SURE to update all the user-* files to have correct information for the first admin user
      - You can keep the thingappuser as is (it should match what gets created for the system token)
      - but you should replace my seeding info with the info you want to use to log in, etc.
    - MAKE SURE to ALSO replace the PLACEHOLDER in the user-password table with an an actual password bcrypt hash
      - For example the hash of the word password is `$2a$12$yBnkHyZ/N1BgofWF5/ubkuHrR3DHGmCG5niLv4vgqqPAN9p3lvPXq`
    - You need to run the user data script first to meet foreign key constraints
- The server needs to be running and configured properly for the server to come up
- NOTE: Mysql Workbench is great for this, as you can save the login creds to the db and just import and run the .sql files
  - I have also provided the thingappdb.mwb to make setting up the schema that much easier

## Notes For Working With Windows
- First, don't do this on windows.... trust me...... but if you install it
- Make sure to run `sudo mysql_secure_installation` if you are installing on WSL, that won't work right away as you'll have to add a password login to the root user before you can run it which you can do by altering the root user after logging in via `sudo mysql`
- TODO: Figure out if I'm using my windows MySQL server or the WSL one
- Secondly wsl has all kinds of issues with networking
- I had to do the following to get it to work properly
    1. `sudo service mysql stop`
    2. `sudo vim /etc/mysql/my.cnf`
    3. Add the lines provided in the example [`my.cnf.template`](my.cnf.template)
    4. `sudo usermod -d /var/lib/mysql/ mysql`
    5. `sudo service mysql start`
- I also weirdly had to do `nc -vz 127.0.0.1 3306` and connect to the db in terminal before it worked in springboot `mysql -h127.0.0.1 -uthingappuser -p`
```