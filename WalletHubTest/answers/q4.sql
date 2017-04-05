DELIMITER $$
create procedure get_bugs(start_date date, end_date date)
begin
    declare count int default 0;
    drop temporary table if exists bug_count;
    create temporary table bug_count(today date, count int) engine=Memory;
    while start_date <= end_date do
        set count = (select count(*) as count from bugs where date(open_date) <= start_date and date(closed_date) >= start_date);
        insert into bug_count values (start_date, count);
        set start_date = date_add(start_date, interval 1 day);
    end while;
    select * from bug_count;
end$$
DELIMITER ;
