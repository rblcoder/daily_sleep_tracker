[![Java CI with Gradle](https://github.com/rblcoder/daily_sleep_tracker/actions/workflows/gradle.yml/badge.svg)](https://github.com/rblcoder/daily_sleep_tracker/actions/workflows/gradle.yml)

JaCoCo Test Coverage  [![coverage](badges/jacoco.svg)](https://github.com/rblcoder/daily_sleep_tracker/actions/workflows/gradle.yml) [![branches coverage](badges/branches.svg)](https://github.com/rblcoder/daily_sleep_tracker/actions/workflows/gradle.yml)

# DevProjects - Daily sleep tracker web app

This is an open source project from [DevProjects](http://www.codementor.io/projects). Feedback and questions are welcome!
Find the project requirements here: [Daily sleep tracker web app](https://www.codementor.io/projects/web/daily-sleep-tracker-web-app-byi4kpk5rt)

## Tech/framework used
Built with spring boot, db used postgresql, thymeleaf for rendering html

## Functionality
This application presently supports only single user.
The user can login, create, view, update and delete entries.

## Database setup

from terminal:

psql postgres
CREATE ROLE sleep WITH LOGIN PASSWORD 'somepassword';
ALTER ROLE sleep CREATEDB;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO sleep;

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO sleep;

    
\q	
psql postgres -U sleep

CREATE DATABASE sleep;

\c sleep


## Installation
I used Java 8, Intellij Idea, TablePlus to check the data 
and a local postgreSQL instance

## Activate profile
Specify dev or prod in SPRING_PROFILES_ACTIVE environment variable

Refer

#### https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#features.profiles
#### https://www.baeldung.com/spring-profiles

## Enviroment variables
Set SPRING_USERNAME, SPRING_PASSWORD environment variables to specify spring security user name and password

Set DB_USERNAME, DB_PASSWORD when using postgresql
## Database logging 
https://vladmihalcea.com/log-sql-spring-boot/
https://github.com/gavlyukovskiy/spring-boot-data-source-decorator

## License
[MIT](https://choosealicense.com/licenses/mit/)


