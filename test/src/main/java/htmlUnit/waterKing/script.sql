CREATE TABLE BOARD (
	id BIGINT NOT NULL AUTO_INCREMENT,
	topic VARCHAR(255),
	topicUrl VARCHAR(255),
	starter VARCHAR(255),
	issueDate TIMESTAMP,
	replyNum BIGINT,
	readNum BIGINT,
	lastScanTime TIMESTAMP,
	raedLevel BIGINT,
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8; 

CREATE UNIQUE INDEX topicUrl ON BOARD (topicUrl ASC);

CREATE TABLE BOARD_DETAIL(
	id BIGINT NOT NULL AUTO_INCREMENT,
	floor VARCHAR(255),
	topic VARCHAR(255),
	postId VARCHAR(255),
	postTime TIMESTAMP,
	postMessage VARCHAR(4000),
	faceNum BIGINT,
	faceDetail VARCHAR(4000),
	pictureNum BIGINT,
	pictureDetail VARCHAR(4000),
	postMessageLength BIGINT,
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8; 

CREATE UNIQUE INDEX floor ON BOARD_DETAIL (floor ASC);


truncate table BOARD;
truncate table BOARD_DETAIL;
