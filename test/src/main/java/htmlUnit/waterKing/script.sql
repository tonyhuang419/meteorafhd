CREATE TABLE water_king (
	id BIGINT NOT NULL AUTO_INCREMENT,
	topic VARCHAR(255),
	topicUrl VARCHAR(255),
	starter VARCHAR(255),
	issueDate TIMESTAMP,
	replyNum BIGINT,
	readNum BIGINT,
	lastScanTime TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE UNIQUE INDEX topicUrl ON water_king (topicUrl ASC);

