-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.5.8-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- project 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `project`;

-- 테이블 project.category 구조 내보내기
CREATE TABLE IF NOT EXISTS `category` (
  `code` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `isSale` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 project.category:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 테이블 project.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `u_id` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` int(50) DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isAccountNonExpired` tinyint(4) DEFAULT NULL,
  `isAccountNonLocked` tinyint(4) DEFAULT NULL,
  `isCredentialsNonExpired` tinyint(4) DEFAULT NULL,
  `isEnabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 project.user:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 테이블 project.u_auth 구조 내보내기
CREATE TABLE IF NOT EXISTS `u_auth` (
  `u_id` varchar(50) NOT NULL,
  `u_auth` varchar(50) NOT NULL,
  PRIMARY KEY (`u_id`,`u_auth`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 project.u_auth:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `u_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `u_auth` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
