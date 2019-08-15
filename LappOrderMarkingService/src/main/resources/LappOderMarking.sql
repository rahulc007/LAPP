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



alter table user_entity modify column uid int(11) not null AUTO_INCREMENT;
alter table `user_entity` modify column `is_email_confirmed` varchar(1) default 0;
alter table `user_entity` modify column `is_first_time_login` varchar(1) default 0;

--ADMIN PASSWORD FOR FIRST TIME: lappadmin@123

INSERT INTO user_entity (`email_id`, `password`,`token`,`customer_id`, `utype`,  `is_email_confirmed`,`is_first_time_login`,`created_date`, `modified_date`) VALUES 
('admin@lapp.com', 'bGFwcGFkbWluQDEyMw==','21232f297a57a5a743894a0e4a801fc3','lappadmin', 1, 1,0,now(), now());

update user_entity set country_code=0;

alter table user_profile modify column pid int(11) not null AUTO_INCREMENT;


delete from user_entity where utype !=1;
delete from user_profile;
delete from sap_file_info;
delete from user_auth_info;
update hibernate_sequence set next_value=5;
 


alter table user_profile modify column uemail_id varchar(60) unique key;

ALTER TABLE user_profile ADD CONSTRAINT emailCons UNIQUE (uemail_id);