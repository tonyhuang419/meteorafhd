select * from stock s order by s.code asc

select * from stock s where s.AVERAGE_LEVEL_DATE like '%20%' or s.AVERAGE_LEVEL_DATE like'%1%'

select count(*) from stock 

select count(*) from (select distinct s.code from stock s) ss

select * from stock s where s.AVERAGE_LEVEL = '5'

select * from stock s where s.AVERAGE_LEVEL is null

select * from stock s where s.AVERAGE_LEVEL = ''