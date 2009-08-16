select count(*) from stock

select count(*) from (select distinct s.code from stock s) ss

select * from stock s where s.AVERAGE_LEVEL = '5'

select * from stock s where s.AVERAGE_LEVEL is null

select * from stock s where s.AVERAGE_LEVEL = ''