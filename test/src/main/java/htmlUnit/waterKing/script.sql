CREATE TABLE BOARD (
	id BIGINT NOT NULL AUTO_INCREMENT,
	topic VARCHAR(255),
	topicUrl VARCHAR(255),
	starter VARCHAR(255),
	issueDate TIMESTAMP,
	replyNum BIGINT,
	readNum BIGINT,
	lastScanTime TIMESTAMP,
	readLevel BIGINT,
	isVote	BIT,
	lastScanFloor BIGINT,
	lastUpateUser VARCHAR(255),
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8; 

CREATE UNIQUE INDEX topicUrl ON BOARD (topicUrl ASC);

CREATE TABLE BOARD_DETAIL(
	id BIGINT NOT NULL AUTO_INCREMENT,
	floor VARCHAR(255),
	topic VARCHAR(255),
	postId VARCHAR(255),
	postTime TIMESTAMP,
	postMessage LONGTEXT,
	faceNum BIGINT,
	faceDetail VARCHAR(4000),
	pictureNum BIGINT,
	pictureDetail VARCHAR(4000),
	postMessageLength BIGINT,
	lastScanTime TIMESTAMP,
	lastUpateUser VARCHAR(255),
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8; 

CREATE UNIQUE INDEX floor ON BOARD_DETAIL (floor ASC);


select * from Board b where b.lastScanFloor > 1 and b.isVote = false  and b.id > 500 and b.id < 1000 order by b.id asc
select * from BOARD b  where b.id>20000;
select * from BOARD_DETAIL bd order by bd.id desc
select count(*) from BOARD;
select count(*) from BOARD_DETAIL;
select * from BOARD_DETAIL bd where bd.floor = 'pid3856554';
select * from BOARD_DETAIL bd where bd.postId = '用户';
select * from BOARD b where b.topic like '%麦田圈%';
select count(*) from BOARD_DETAIL bd where bd.topic like '%刘德华小%';
select b.replyNum , b.lastScanFloor,b.readLevel from BOARD b where b.replyNum <> b.lastScanFloor and b.lastScanFloor <> 1

select sum(b.replyNum) from BOARD b;
select sum(b.replyNum) from BOARD b where b.readLevel = 255
select * from Board b where b.lastScanFloor > 1 and b.isVote = false;

//select count(*) from Board b where b.lastScanFloor <> b.replyNum
//update Board b set b.lastScanFloor = b.replyNum 
//truncate table BOARD;
//truncate table BOARD_DETAIL;

//drop table BOARD;
//drop table BOARD_DETAIL;
