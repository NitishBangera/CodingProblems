set @rownr=0;
select @rownr:=@rownr+1 as rank, name, votes from votes order by votes desc;
