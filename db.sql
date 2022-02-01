-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.6.4-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- project 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `project`;

-- 테이블 project.category 구조 내보내기
CREATE TABLE IF NOT EXISTS `category` (
  `code` int(255) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `isSale` tinyint(4) NOT NULL,
  `groups` int(11) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `depth` int(11) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.category:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`code`, `name`, `stock`, `isSale`, `groups`, `orders`, `depth`) VALUES
	(1, 'test', 1111, 1, 1, 1, NULL),
	(110, 'test2', 2222, 1, 1, 3, 1),
	(120, 'test3', 333, 1, 1, 2, 1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 테이블 project.kakao_user 구조 내보내기
CREATE TABLE IF NOT EXISTS `kakao_user` (
  `k_number` int(11) NOT NULL AUTO_INCREMENT,
  `k_name` varchar(50) DEFAULT NULL,
  `k_email` varchar(50) NOT NULL,
  `access_token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`k_number`,`k_email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.kakao_user:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `kakao_user` DISABLE KEYS */;
INSERT INTO `kakao_user` (`k_number`, `k_name`, `k_email`, `access_token`) VALUES
	(15, '천은정', 'cjsdmswjd010@naver.com', 'SdgxqF-C50rx-3Dn3w6EDImY-ubonoGtzWacNworDNQAAAF-o32rYA');
/*!40000 ALTER TABLE `kakao_user` ENABLE KEYS */;

-- 테이블 project.spring_session 구조 내보내기
CREATE TABLE IF NOT EXISTS `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- 테이블 데이터 project.spring_session:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;

-- 테이블 project.spring_session_attributes 구조 내보내기
CREATE TABLE IF NOT EXISTS `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- 테이블 데이터 project.spring_session_attributes:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;

-- 테이블 project.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `u_id` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isAccountNonExpired` tinyint(4) DEFAULT NULL,
  `isAccountNonLocked` tinyint(4) DEFAULT NULL,
  `isCredentialsNonExpired` tinyint(4) DEFAULT NULL,
  `isEnabled` tinyint(4) DEFAULT NULL,
  `oauth` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.user:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`u_id`, `password`, `name`, `datetime`, `isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled`, `oauth`) VALUES
	('cjsdmswjd010@naver.com', '$2a$10$zHo8eZRfGHvw4t.73Z73meE3KcRJMTQA9BvgLbK9vJNGZb7le1GsG', '천은정', '2022-01-28 11:52:50', 1, 1, 1, 1, 'kakao'),
	('zz', '$2a$10$JSSKVJGkwx/g5q2deVEbeeNztil6xpC6u43T3rllDCjPcg.kj57H2', 'zz', '2022-01-28 10:27:34', 1, 1, 1, 1, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 테이블 project.u_auth 구조 내보내기
CREATE TABLE IF NOT EXISTS `u_auth` (
  `u_id` varchar(50) NOT NULL,
  `u_auth` varchar(50) NOT NULL,
  PRIMARY KEY (`u_id`,`u_auth`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.u_auth:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `u_auth` DISABLE KEYS */;
INSERT INTO `u_auth` (`u_id`, `u_auth`) VALUES
	('cjsdmswjd010@naver.com', 'ROLE_USER'),
	('zz', 'ROLE_USER');
/*!40000 ALTER TABLE `u_auth` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
