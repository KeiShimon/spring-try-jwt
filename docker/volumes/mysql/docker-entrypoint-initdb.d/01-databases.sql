CREATE DATABASE IF NOT EXISTS `tryjwt`;
CREATE USER 'tryjwtuser'@'%' IDENTIFIED BY 'try';
GRANT ALL ON tryjwt.* TO 'tryjwtuser'@'%';

