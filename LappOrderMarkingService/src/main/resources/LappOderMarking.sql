create database LappOrderMarkingDB;

use LappOrderMarkingDB;


CREATE TABLE IF NOT EXISTS `user_entity` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email_id` char(50) NOT NULL,
  `token` varchar(200) NOT NULL,
  `password` varchar(50) NOT NULL,
  `customer_id` varchar(50),
  `utype` int (111) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY(`email_id`),
  PRIMARY KEY (`uid`)
);

alter table `user_entity` add column `is_email_confirmed` varchar(1);


--ADMIN PASSWORD FOR FIRST TIME: lappadmin@123

INSERT INTO user_entity (`email_id`, `password`,`token`,`customer_id`, `utype`,  `is_email_confirmed`,`created_date`, `modified_date`) VALUES 
('admin@lapp.com', 'bGFwcGFkbWluQDEyMw==','21232f297a57a5a743894a0e4a801fc3','lappadmin', 1, 1,now(), now());

