create or replace procedure p_test(
namex  in  VARCHAR2,
age  in number)
as
begin
insert into test values(namex,age);
end p_test;
/
