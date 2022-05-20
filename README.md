# daily_sleep_tracker

from terminal:

psql postgres

CREATE ROLE sleep WITH LOGIN PASSWORD 'somepassword';
ALTER ROLE sleep CREATEDB;
\q
psql postgres -U sleep
CREATE DATABASE sleep;
