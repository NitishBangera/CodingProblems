DELIMITER $$
create procedure split_column(delimiter char(1))
begin
    declare id int default 0;
    declare value varchar(50);
    declare occurance int default 0;
    declare i int default 0;
    declare split_value varchar(50);
    declare done int default 0;
    declare curl cursor for select sometbl.ID, sometbl.NAME from sometbl where sometbl.NAME != '';
    declare continue handler for not found set done = 1;
    
    drop temporary table if exists sometbl2;
    create temporary table sometbl2(ID int, NAME varchar(50)) engine=Memory;
    
    open curl;
        read_loop: LOOP
            fetch curl into id, value;
            if done then
                leave read_loop;
            end if;
            
            set occurance = (select length(value) - length(replace(value, delimiter, '')) + 1);
            set i = 1;
            
            while i <= occurance do
                set split_value = (select replace(substring(substring_index(value, delimiter, i), length(substring_index(value, delimiter, i - 1)) + 1), delimiter, ''));
                insert into sometbl2 values(id, split_value);
                set i = i + 1;
            end while;
            
        end LOOP;
        select * from sometbl2;
    close curl;
end$$

DELIMITER ;
