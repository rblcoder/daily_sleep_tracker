# daily_sleep_tracker



# DevProjects - Daily sleep tracker web app

This is an open source project from [DevProjects](http://www.codementor.io/projects). Feedback and questions are welcome!
Find the project requirements here: [Daily sleep tracker web app](https://www.codementor.io/projects/web/daily-sleep-tracker-web-app-byi4kpk5rt)

## Tech/framework used
Built with spring boot, db used postgresql

## Screenshots and demo
Screenshots of your app and/or a link to your live demo

## Database setup

from terminal:

psql postgres

CREATE ROLE sleep WITH LOGIN PASSWORD 'somepassword';

ALTER ROLE sleep CREATEDB; 

\q

psql postgres -U sleep

CREATE DATABASE sleep;


## Installation
Instructions for other developers on how to install and run your code on their local environment.

## License
[MIT](https://choosealicense.com/licenses/mit/)


