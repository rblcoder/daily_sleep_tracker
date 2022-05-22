ALTER TABLE sleep_info_entries
ADD CONSTRAINT unique_sleep_date_time
UNIQUE(sleep_date_time);

ALTER TABLE sleep_info_entries
ADD CONSTRAINT unique_get_up_date_time
UNIQUE(get_up_date_time);