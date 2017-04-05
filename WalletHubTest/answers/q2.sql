DELIMITER $$
create function initcap(input varchar(255)) returns varchar(255)
begin
    declare len int;
	declare i int;
	
    set len = char_length(input);
    set input = lower(input);
    set i = 0;
    
    while(i < len) do
        if(mid(input, i, 1) = ' ' or i = 0) then
            if (i < len) then
                set input = concat(left(input, i), 
                                upper(mid(input, i + 1, 1)), 
                                right(input, len - i - 1));
            end if;
        end if;
        set i = i + 1;
    end while;
    
    return input;
end$$
DELIMITER ;
