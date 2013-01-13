use test;
CREATE TABLE User (
  userID        INT NOT NULL,
  name          VARCHAR(50),
  username      VARCHAR(30) NOT NULL,
  passwordHash  VARCHAR(32) NOT NULL,   
  email         VARCHAR(30) NOT NULL,
  nameVisible   INT NOT NULL,
  PRIMARY KEY   (userID)
);
CREATE TABLE UserProp (
  userID     INT NOT NULL,
  name       VARCHAR(30) NOT NULL,
  propValue  VARCHAR(255) NOT NULL,
  KEY        (userID,name)
);
CREATE TABLE Message (
  messageID     INT NOT NULL,
  subject       VARCHAR(255),
  userID        INT NOT NULL,
  body          TEXT,
  Date  VARCHAR(15) NOT NULL,
  PRIMARY KEY   (messageID),
  KEY           (userID)
);