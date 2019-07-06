create database LappOrderMarkingDB;

use LappOrderMarkingDB;


CREATE TABLE IF NOT EXISTS `UserEntity` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `emailId` char(50) NOT NULL,
  `token` varchar(200) NOT NULL,
  `password` varchar(50) NOT NULL,
  `customerId` varchar(50),
  `utype` int (111) NOT NULL DEFAULT '0',
  `createdtime` datetime NOT NULL,
  `modifiedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY(`emailId`),
  PRIMARY KEY (`uid`)
);

alter table `UserEntity` add column `isEmailConfirmed` varchar(1);


--ADMIN PASSWORD FOR FIRST TIME: lappadmin@123

INSERT INTO UserEntity (`emailId`, `password`,`token`,`customerId`, `utype`,  `isEmailVerified`,`createdtime`, `modifiedtime`) VALUES 
('admin@lapp.com', 'bGFwcGFkbWluQDEyMw==','21232f297a57a5a743894a0e4a801fc3','lappadmin', 1, 1,now(), now());

