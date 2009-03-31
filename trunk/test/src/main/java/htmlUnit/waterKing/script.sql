--CREATE TABLE BOARD (
--	id BIGINT NOT NULL AUTO_INCREMENT,
--	topic VARCHAR(255),
--	topicUrl VARCHAR(255),
--	starter VARCHAR(255),
--	issueDate TIMESTAMP DEFAULT 0,
--	replyNum BIGINT,
--	readNum BIGINT,
--	lastScanTime TIMESTAMP DEFAULT 0,
--	readLevel BIGINT,
--	isVote	BIT,
--	lastScanFloor BIGINT,
--	lastUpateUser VARCHAR(255),
--	skip BIT,
--	PRIMARY KEY (id)
--) ENGINE=MyISAM DEFAULT CHARSET=utf8; 
--
--CREATE UNIQUE INDEX topicUrl ON BOARD (topicUrl ASC);

--alter table BOARD add column lastUpateUser VARCHAR(255)
--alter table BOARD add column skip BIT
--update Board b set b.skip = false
--update Board b set b.skip = true where b.id = 2961

--CREATE TABLE BOARD_DETAIL(
--	id BIGINT NOT NULL AUTO_INCREMENT,
--	floor VARCHAR(255),
--	topic VARCHAR(255),
--	postId VARCHAR(255),
--	postTime TIMESTAMP DEFAULT 0,
--	postMessage LONGTEXT,
--	faceNum BIGINT,
--	faceDetail VARCHAR(4000),
--	pictureNum BIGINT,
--	pictureDetail VARCHAR(4000),
--	postMessageLength BIGINT,
--	lastScanTime TIMESTAMP DEFAULT 0,
--	lastUpateUser VARCHAR(255),
--  floorNum BIGINT,
--	PRIMARY KEY (id)
--) ENGINE=MyISAM DEFAULT CHARSET=utf8; 
--
--CREATE UNIQUE INDEX floor ON BOARD_DETAIL (floor ASC);


--ALTER TABLE `BOARD_DETAIL` CHANGE `postTime` `postTime` TIMESTAMP NOT NULL DEFAULT 0
--alter table BOARD_DETAIL add column floorNum BIGINT;  
--alter table BOARD_DETAIL add column lastScanTime TIMESTAMP;
--alter table BOARD_DETAIL add column lastUpateUser VARCHAR(255);
--update Board b set b.lastScanFloor = 33 where b.id = 2961;



select * from Board b where b.lastScanFloor > 1 and b.isVote = false  and b.id > 500 and b.id < 1000 order by b.id asc
select * from BOARD b  order by b.issueDate asc
select * from BOARD_DETAIL bd order by bd.id desc
select count(*) from BOARD b where b.issueDate>date_format('2009-3-21','%Y-%m-%d');
select count(*) from BOARD_DETAIL bd  --1029528
select * from BOARD_DETAIL bd where bd.floor = 'pid3856554';
select * from BOARD_DETAIL bd where bd.postId = '用户';
select * from BOARD b where b.topic like '%毕业答辩%';
select * from BOARD_DETAIL bd where bd.postId = ‘% %’;
select b.replyNum , b.lastScanFloor,b.readLevel from BOARD b where  b.lastScanFloor < 0 

select sum(bd.faceNum) from BOARD_DETAIL bd; --589940
select sum(bd.pictureNum) from BOARD_DETAIL bd; --37228
select sum(bd.postMessageLength) from BOARD_DETAIL bd; --60571256

--sum 1200699
select sum(b.replyNum) from BOARD b;
--can be scan 1119123
select sum(b.replyNum) from BOARD b where b.readLevel <=100 and b.isVote = false
--remain scan
select count(*) from Board b where b.lastScanFloor > 1 and b.isVote = false and b.readLevel <=100 ;

--select count(*) from Board b where b.lastScanFloor <> b.replyNum
--update Board b set b.lastScanFloor = b.replyNum 
--truncate table BOARD;
--truncate table BOARD_DETAIL;

--drop table BOARD;
--drop table BOARD_DETAIL;
