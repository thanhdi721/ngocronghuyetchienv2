-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 22, 2025 lúc 05:25 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `nro_rank`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `superrank`
--

CREATE TABLE `superrank` (
  `playerId` int(11) NOT NULL,
  `cName` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `charID` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `cgender` tinyint(4) NOT NULL,
  `cPower` bigint(20) NOT NULL,
  `headID` smallint(6) NOT NULL,
  `bodyID` smallint(6) NOT NULL,
  `legID` smallint(6) NOT NULL,
  `bag` int(11) NOT NULL,
  `headICON` smallint(6) NOT NULL,
  `cHPfull` int(11) NOT NULL,
  `cMPfull` int(11) NOT NULL,
  `cDamfull` int(11) NOT NULL,
  `cDeffull` int(11) NOT NULL,
  `cCriticalfull` int(11) NOT NULL,
  `cDefPercentfull` int(11) NOT NULL,
  `cMissPercentfull` int(11) NOT NULL,
  `skills` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `suckHPGoc` int(11) NOT NULL,
  `suckKIGoc` int(11) NOT NULL,
  `ngoc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `superrank`
--

INSERT INTO `superrank` (`playerId`, `cName`, `charID`, `rank`, `cgender`, `cPower`, `headID`, `bodyID`, `legID`, `bag`, `headICON`, `cHPfull`, `cMPfull`, `cDamfull`, `cDeffull`, `cCriticalfull`, `cDefPercentfull`, `cMissPercentfull`, `skills`, `suckHPGoc`, `suckKIGoc`, `ngoc`) VALUES
(6, 'admin', 10800, 0, 0, 17999999999, 1622, 1625, 1626, -1, -1, 682030008, 723934747, 497057547, 300, 7, 0, 0, '[1]', 0, 0, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `superrank`
--
ALTER TABLE `superrank`
  ADD PRIMARY KEY (`playerId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
