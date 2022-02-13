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
  `name` varchar(100) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  `isSale` tinyint(4) NOT NULL,
  `groups` int(11) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  `depth` int(11) DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `category_ibfk_1` (`groups`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`groups`) REFERENCES `category` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.category:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`code`, `name`, `stock`, `isSale`, `groups`, `orders`, `depth`) VALUES
	(1, 'TOP', 100, 1, 1, 1, NULL),
	(2, 'BOTTOM', 100, 1, 2, 1, NULL),
	(3, 'ACC', 200, 1, 3, 1, NULL),
	(110, 'TOP/shirt', 50, 1, 1, 3, 1),
	(120, 'TOP/sweater', 20, 1, 1, 2, 1),
	(210, 'BOTTOM/pants', 50, 1, 2, 2, 1);
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
	(15, '천은정', 'cjsdmswjd010@naver.com', '1Ie8erVxGVEWx24661UKlA8xGVjgA9mXk_r7vQo9dRsAAAF-0gOm6Q');
/*!40000 ALTER TABLE `kakao_user` ENABLE KEYS */;

-- 테이블 project.orderinfo 구조 내보내기
CREATE TABLE IF NOT EXISTS `orderinfo` (
  `orderCode` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(50) DEFAULT NULL,
  `payway` varchar(50) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `user` varchar(50) DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT current_timestamp(),
  `receiver_address` varchar(50) DEFAULT NULL,
  `receiver_postcode` varchar(50) DEFAULT NULL,
  `receiver_name` varchar(50) DEFAULT NULL,
  `receiver_phone` varchar(50) DEFAULT NULL,
  `receiver_same` varchar(50) DEFAULT NULL,
  `user_address` varchar(50) DEFAULT NULL,
  `user_phone` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_postcode` int(11) DEFAULT NULL,
  `givePoint` int(11) DEFAULT 0,
  PRIMARY KEY (`orderCode`),
  KEY `user` (`user`),
  CONSTRAINT `orderinfo_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.orderinfo:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
INSERT INTO `orderinfo` (`orderCode`, `state`, `payway`, `point`, `total`, `user`, `datetime`, `receiver_address`, `receiver_postcode`, `receiver_name`, `receiver_phone`, `receiver_same`, `user_address`, `user_phone`, `user_name`, `user_postcode`, `givePoint`) VALUES
	(39, '주문확인중', '카카오페이', 0, 70000, 'aa', '2022-02-13 16:44:02', NULL, '', '', '', '주문자와 동일', '대구 남구 경상길 1  (대명동)', 1011111111, '주문자', 42463, 0),
	(43, '주문확인중', '카카오페이', 15000, 37500, 'aa', '2022-02-13 20:05:33', '울산 중구 강정3길 57  (교동)', '44468', '받는자', '010456456', NULL, '부산 강서구 가달1로 7  (생곡동)', 10123123, '주문자', 46729, 0);
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;

-- 테이블 project.order_details 구조 내보내기
CREATE TABLE IF NOT EXISTS `order_details` (
  `p_code` int(11) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `option` varchar(50) NOT NULL DEFAULT '옵션없음',
  `givePoint` int(11) DEFAULT 0,
  KEY `order_num` (`order_num`),
  KEY `p_code` (`p_code`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_num`) REFERENCES `orderinfo` (`orderCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`p_code`) REFERENCES `product` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.order_details:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` (`p_code`, `order_num`, `count`, `option`, `givePoint`) VALUES
	(6, 39, 1, 'S', 0),
	(5, 43, 1, 'Red', 0);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;

-- 테이블 project.pointlist 구조 내보내기
CREATE TABLE IF NOT EXISTS `pointlist` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `contents` varchar(300) DEFAULT NULL,
  `point` varchar(50) DEFAULT NULL,
  `datetime` timestamp NOT NULL DEFAULT current_timestamp(),
  `total` int(100) DEFAULT NULL,
  PRIMARY KEY (`num`),
  KEY `id` (`id`),
  CONSTRAINT `pointlist_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.pointlist:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `pointlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `pointlist` ENABLE KEYS */;

-- 테이블 project.product 구조 내보내기
CREATE TABLE IF NOT EXISTS `product` (
  `code` int(11) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `descr` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `isSale` tinyint(4) DEFAULT NULL,
  `detail_desc` text DEFAULT NULL,
  `material` varchar(50) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `manufacturer` varchar(50) DEFAULT NULL,
  `caution` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `point_t` varchar(50) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `ship` varchar(50) DEFAULT NULL,
  `files` varchar(50) DEFAULT NULL,
  `mainPhoto` varchar(50) DEFAULT NULL,
  `mainCategory` int(255) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `product_ibfk_1` (`mainCategory`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`mainCategory`) REFERENCES `category` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.product:~8 rows (대략적) 내보내기
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`code`, `category`, `name`, `descr`, `type`, `isSale`, `detail_desc`, `material`, `size`, `manufacturer`, `caution`, `price`, `point_t`, `stock`, `ship`, `files`, `mainPhoto`, `mainCategory`) VALUES
	(1, 'TOP/sweater(120)', 'pinkSweater', '출처 우신사', 'hit,new,disc,recom,best', 1, '출처 우신사스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 50000, '판매가기준 설정비율', 10, '무료배송', 'sweater1.jpg,sweater1detail.jpg', 'sweater1.jpg', 120),
	(2, 'ACC(3)', '래빗키링', '우신사스토어', 'hit,new', 1, '사진출처 우신사스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 20000, '판매가기준 설정비율', 20, '쇼핑몰 기본설정 사용', '키링.jpg,키링디테일.jpg', '키링.jpg', 3),
	(3, 'TOP/shirt(110)', 'shirt', '우신사스토어제품', 'new,recom', 1, '참고자료 우신사스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 60000, '판매가기준 설정비율', 30, '쇼핑몰 기본설정 사용', 'shirt1.jpg,shirt1detail.jpg', 'shirt1.jpg', 110),
	(4, 'TOP(1)', 'tee', '상세페이지 참고', 'hit,recom,best', 1, '우신사 스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 35000, '판매가기준 설정비율', 10, '쇼핑몰 기본설정 사용', 'tee.jpg,teedetail.jpg', 'tee.jpg', 1),
	(5, 'TOP(1)', 'mtm', '상세페이지 참고', 'disc,recom,best', 1, '우신사 스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 50000, '판매가기준 설정비율', 20, '쇼핑몰 기본설정 사용', 'mtm.jpg,mtmdetail.jpg', 'mtm.jpg', 1),
	(6, 'BOTTOM(2)', '오버롤st', '상세페이지 참고', 'new,best', 1, '우신사 스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 70000, '판매가기준 설정비율', 50, '무료배송', 'pants1.jpg,pants1detail.jpg', 'pants1.jpg', 2),
	(7, 'BOTTOM/pants(210)', 'pants', '상세페이지 참고', 'disc,recom', 1, '우신사 스토어 제품', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 40000, '판매가기준 설정비율', 50, '쇼핑몰 기본설정 사용', 'pants2.jpg,pants1detail.jpg', 'pants2.jpg', 210),
	(8, 'ACC(3)', '지비츠', '상세페이지 참고', 'hit,disc,recom', 1, '우신사 스토어', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', '상세페이지 참고', 5000, '판매가기준 설정비율', 9, '쇼핑몰 기본설정 사용', '지비츠.jpg,지비츠detail.jpg', '지비츠.jpg', 3);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 테이블 project.p_options 구조 내보내기
CREATE TABLE IF NOT EXISTS `p_options` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `p_code` int(50) DEFAULT NULL,
  `option` varchar(50) NOT NULL DEFAULT '옵션없음',
  PRIMARY KEY (`num`),
  KEY `p_options_ibfk_1` (`p_code`),
  CONSTRAINT `p_options_ibfk_1` FOREIGN KEY (`p_code`) REFERENCES `product` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.p_options:~18 rows (대략적) 내보내기
/*!40000 ALTER TABLE `p_options` DISABLE KEYS */;
INSERT INTO `p_options` (`num`, `p_code`, `option`) VALUES
	(81, 1, '핑크'),
	(82, 1, '베이지'),
	(83, 2, '옵션없음'),
	(85, 3, 'S'),
	(86, 3, 'M'),
	(87, 3, 'L'),
	(88, 4, 'S'),
	(89, 4, 'M'),
	(90, 5, 'Red'),
	(91, 5, 'White'),
	(92, 5, 'Black'),
	(93, 6, 'S'),
	(94, 6, 'M'),
	(95, 6, 'L'),
	(96, 6, 'XL'),
	(97, 7, 'S'),
	(98, 7, 'M'),
	(99, 8, '체리곰');
/*!40000 ALTER TABLE `p_options` ENABLE KEYS */;

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
  `datetime` timestamp NULL DEFAULT current_timestamp(),
  `isAccountNonExpired` tinyint(4) DEFAULT NULL,
  `isAccountNonLocked` tinyint(4) DEFAULT NULL,
  `isCredentialsNonExpired` tinyint(4) DEFAULT NULL,
  `isEnabled` tinyint(4) DEFAULT NULL,
  `oauth` varchar(50) DEFAULT NULL,
  `postcode` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `point` int(255) NOT NULL DEFAULT 0,
  `auth` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.user:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`u_id`, `password`, `name`, `datetime`, `isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled`, `oauth`, `postcode`, `address`, `phone`, `point`, `auth`) VALUES
	('aa', '$2a$10$6WzNlbuGuE3slRPH6rgqxeuUPldWyXXMCtR0xk/SiCOI8eJ.VLO5W', 'AAA', '2022-02-06 23:50:34', 1, 1, 1, 1, NULL, 42923, '대구 달성군 다사읍 서재리 202 경신그린빌 102', '01044445555', 985000, 'ADMIN'),
	('cjsdmswjd010@naver.com', '$2a$10$zHo8eZRfGHvw4t.73Z73meE3KcRJMTQA9BvgLbK9vJNGZb7le1GsG', '천은정', '2022-01-28 11:52:50', 1, 0, 1, 1, 'kakao', NULL, NULL, NULL, 0, NULL),
	('zz', '$2a$10$HkU6fIv6rGhsv649s4zDwelshW2wtodh0qxi.7V.EofPSVFIFZs0m', 'zz', '2022-02-07 21:47:01', 1, 1, 1, 1, NULL, 123, 'dd  ', '111111111', 0, 'ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 테이블 project.u_auth 구조 내보내기
CREATE TABLE IF NOT EXISTS `u_auth` (
  `u_id` varchar(50) NOT NULL,
  `u_auth` varchar(50) NOT NULL,
  PRIMARY KEY (`u_id`,`u_auth`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.u_auth:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `u_auth` DISABLE KEYS */;
INSERT INTO `u_auth` (`u_id`, `u_auth`) VALUES
	('aa', 'ROLE_ADMIN'),
	('aa', 'ROLE_USER'),
	('cjsdmswjd010@naver.com', 'ROLE_ADMIN'),
	('cjsdmswjd010@naver.com', 'ROLE_USER'),
	('zz', 'ROLE_ADMIN'),
	('zz', 'ROLE_USER');
/*!40000 ALTER TABLE `u_auth` ENABLE KEYS */;

-- 테이블 project.wishlist 구조 내보내기
CREATE TABLE IF NOT EXISTS `wishlist` (
  `wishitem` int(11) NOT NULL,
  `id` varchar(100) NOT NULL,
  `option` varchar(50) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `num` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`num`),
  KEY `id` (`id`),
  KEY `wishitem` (`wishitem`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`wishitem`) REFERENCES `product` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

-- 테이블 데이터 project.wishlist:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` (`wishitem`, `id`, `option`, `count`, `num`) VALUES
	(8, 'aa', '체리곰', 1, 14),
	(6, 'aa', 'S', 1, 15),
	(2, 'aa', '옵션없음', 1, 16);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
