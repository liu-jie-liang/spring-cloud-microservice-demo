-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        11.1.2-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 test 的数据库结构
DROP DATABASE IF EXISTS `test`;
CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `test`;

-- 导出  表 test.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `sno` varchar(3) DEFAULT NULL,
  `sname` varchar(9) DEFAULT NULL,
  `ssex` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  test.student 的数据：~3 rows (大约)
INSERT INTO `student` (`sno`, `sname`, `ssex`) VALUES
	('001', 'KangKang', 'M '),
	('002', 'Mike5', 'M'),
	('004', 'Mariadb', 'F ');

-- 导出  表 test.syslog 结构
CREATE TABLE IF NOT EXISTS `syslog` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(50) DEFAULT NULL,
  `OPERATION` varchar(50) DEFAULT NULL,
  `TIME` int(10) unsigned DEFAULT NULL,
  `METHOD` varchar(200) DEFAULT NULL,
  `PARAMS` varchar(500) DEFAULT NULL,
  `IP` varchar(64) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  test.syslog 的数据：~92 rows (大约)
INSERT INTO `syslog` (`ID`, `USERNAME`, `OPERATION`, `TIME`, `METHOD`, `PARAMS`, `IP`, `CREATE_TIME`) VALUES
	(1, 'mrbird', '执行方法一', 8, 'com.example.demo.controller.TestController.methodOne()', '  name: null', '127.0.0.1', '2023-09-08 13:59:19'),
	(2, 'mrbird', '执行方法一', 0, 'com.example.demo.controller.TestController.methodOne()', '  name: aaa', '127.0.0.1', '2023-09-08 13:59:34'),
	(3, 'mrbird', '执行方法二', 2001, 'com.example.demo.controller.TestController.methodTwo()', '', '127.0.0.1', '2023-09-08 14:00:10'),
	(4, 'mrbird', '执行方法二', 2007, 'com.example.demo.controller.TestController.methodTwo()', '', '127.0.0.1', '2023-09-08 14:00:15'),
	(5, 'mrbird', '执行方法三', 0, 'com.example.demo.controller.TestController.methodThree()', '  name: aaa  age: 12', '127.0.0.1', '2023-09-08 14:00:27'),
	(6, 'mrbird', '执行方法三', 0, 'com.example.demo.controller.TestController.methodThree()', '  name: aaa  age: 12', '127.0.0.1', '2023-09-08 14:00:29'),
	(7, 'mrbird', 'getSysLog', 481, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:11:51'),
	(8, 'mrbird', 'getSysLog', 490, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:16'),
	(9, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:18'),
	(10, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:19'),
	(11, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:19'),
	(12, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:19'),
	(13, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:20'),
	(14, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:20'),
	(15, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:20'),
	(16, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:20'),
	(17, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:21'),
	(18, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:47'),
	(19, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:47'),
	(20, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:47'),
	(21, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:48'),
	(22, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:48'),
	(23, 'mrbird', 'getSysLog', 7, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:17:48'),
	(24, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:18:37'),
	(25, 'mrbird', 'getSysLog', 513, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:45'),
	(26, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:47'),
	(27, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:48'),
	(28, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:48'),
	(29, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:49'),
	(30, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:49'),
	(31, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:49'),
	(32, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(33, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(34, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(35, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(36, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(37, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:50'),
	(38, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:51'),
	(39, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:51'),
	(40, 'mrbird', 'getSysLog', 7, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:51'),
	(41, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:51'),
	(42, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:51'),
	(43, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:52'),
	(44, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:52'),
	(45, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:52'),
	(46, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:52'),
	(47, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:52'),
	(48, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:53'),
	(49, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:53'),
	(50, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:53'),
	(51, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:53'),
	(52, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:53'),
	(53, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:54'),
	(54, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:54'),
	(55, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:54'),
	(56, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:54'),
	(57, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:54'),
	(58, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:55'),
	(59, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:55'),
	(60, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:55'),
	(61, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:55'),
	(62, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:55'),
	(63, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:56'),
	(64, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:56'),
	(65, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 15:33:56'),
	(66, 'mrbird', 'getSysLog', 517, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 16:14:05'),
	(67, 'mrbird', 'getSysLog', 504, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-08 16:53:41'),
	(68, 'mrbird', 'getSysLog', 517, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:03:05'),
	(69, 'mrbird', 'getSysLog', 471, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:06:21'),
	(70, 'mrbird', 'queryStudentBySno', 3, 'com.example.demo.controller.TestController.queryStudentBySno()', '  sno: 004', '127.0.0.1', '2023-09-11 16:06:25'),
	(71, 'mrbird', 'getSysLog', 458, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:16'),
	(72, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:19'),
	(73, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:19'),
	(74, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:20'),
	(75, 'mrbird', 'getSysLog', 2, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:20'),
	(76, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:20'),
	(77, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:20'),
	(78, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:21'),
	(79, 'mrbird', 'getSysLog', 1, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:40:21'),
	(80, 'mrbird', 'queryStudentBySno', 2, 'com.example.demo.controller.TestController.queryStudentBySno()', '  sno: 004', '127.0.0.1', '2023-09-11 16:40:24'),
	(81, 'mrbird', 'queryStudentBySno', 1, 'com.example.demo.controller.TestController.queryStudentBySno()', '  sno: 004', '127.0.0.1', '2023-09-11 16:40:25'),
	(82, 'mrbird', 'queryStudentBySno', 2, 'com.example.demo.controller.TestController.queryStudentBySno()', '  sno: 004', '127.0.0.1', '2023-09-11 16:40:25'),
	(83, 'mrbird', 'getSysLog', 4, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-11 16:41:36'),
	(84, 'mrbird', 'queryStudentBySno', 1, 'com.example.demo.controller.TestController.queryStudentBySno()', '  sno: 004', '127.0.0.1', '2023-09-11 16:41:39'),
	(85, 'mrbird', 'getSysLog', 556, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:10:19'),
	(86, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:11:36'),
	(87, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:13:37'),
	(88, 'mrbird', 'getSysLog', 543, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:15:21'),
	(89, 'mrbird', 'getSysLog', 489, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:47:35'),
	(90, 'mrbird', 'getSysLog', 7860, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:52:31'),
	(91, 'mrbird', 'getSysLog', 4557, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 10:53:41'),
	(92, 'mrbird', 'getSysLog', 3, 'com.example.demo.controller.TestController.getSysLog()', '  id: 1', '127.0.0.1', '2023-09-15 11:01:38');

-- 导出  表 test.test 结构
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) unsigned NOT NULL DEFAULT 0,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  test.test 的数据：~3 rows (大约)
INSERT INTO `test` (`id`, `timestamp`, `date`) VALUES
	(1, 1672534861000, '2023-01-01 09:01:01'),
	(2, 1672534861000, '2023-01-01 09:01:01'),
	(3, 1672534861000, '2023-01-01 09:01:01');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
