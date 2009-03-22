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
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8; 

CREATE UNIQUE INDEX floor ON BOARD_DETAIL (floor ASC);


select count(*) from BOARD;
select count(*) from BOARD_DETAIL;
select * from BOARD_DETAIL bd where bd.floor = 'pid3856554';
select * from BOARD_DETAIL bd where bd.postId = '用户';
select * from BOARD b where b.topic like '%谁能找到林志炫那版的夏虫%';
select count(*) from BOARD_DETAIL bd where bd.topic like '%刘德华小%';
select b.replyNum , b.lastScanFloor,b.readLevel from BOARD b where b.replyNum <> b.lastScanFloor and b.lastScanFloor <> 1

select sum(b.replyNum) from BOARD b;
select sum(b.replyNum) from BOARD b where b.readLevel = 255
select * from Board b where b.lastScanFloor > 1 and b.isVote = false;
//update Board b set b.lastScanFloor = 1 where b.lastScanFloor < 1 and b.isVote = false;
//truncate table BOARD;
//truncate table BOARD_DETAIL;

//drop table BOARD;
//drop table BOARD_DETAIL;
