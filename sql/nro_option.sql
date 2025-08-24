-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th2 24, 2025 lúc 06:58 AM
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
-- Cơ sở dữ liệu: `nro_option`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `options`
--

CREATE TABLE `options` (
  `playerId` int(11) NOT NULL,
  `cName` varchar(30) NOT NULL,
  `cgender` tinyint(4) NOT NULL,
  `head` smallint(6) NOT NULL,
  `ctaskId` tinyint(4) NOT NULL,
  `ctaskIndex` int(11) NOT NULL DEFAULT 0,
  `ctaskCount` int(11) NOT NULL DEFAULT 0,
  `cPower` bigint(20) NOT NULL,
  `cPowerLimit` tinyint(4) NOT NULL,
  `mapTemplateId` tinyint(3) UNSIGNED NOT NULL,
  `cx` smallint(6) NOT NULL,
  `cy` smallint(6) NOT NULL,
  `nClassId` tinyint(4) NOT NULL,
  `xu` bigint(20) NOT NULL,
  `luong` int(11) NOT NULL,
  `luongKhoa` int(11) NOT NULL,
  `cHPGoc` int(11) NOT NULL,
  `cMPGoc` int(11) NOT NULL,
  `cHP` int(11) NOT NULL,
  `cMP` int(11) NOT NULL,
  `cDamGoc` int(11) NOT NULL,
  `cDefGoc` int(11) NOT NULL,
  `cCriticalGoc` int(11) NOT NULL,
  `cTiemNang` bigint(20) NOT NULL,
  `skills` varchar(5000) NOT NULL DEFAULT '[]',
  `arrItemBody` longtext NOT NULL,
  `typeTeleport` tinyint(4) NOT NULL DEFAULT 1,
  `KSkill` varchar(200) NOT NULL DEFAULT '[-1,-1,-1,-1,-1]',
  `OSkill` varchar(200) NOT NULL DEFAULT '[-1,-1,-1,-1,-1]',
  `CSkill` varchar(100) NOT NULL DEFAULT '[]',
  `itemTimes` varchar(5000) NOT NULL DEFAULT '[]',
  `cStamina` smallint(6) NOT NULL,
  `cMaxStamina` smallint(6) NOT NULL,
  `cspeacialSkill` varchar(100) NOT NULL DEFAULT '[-1,0,0]',
  `clanId` int(11) NOT NULL DEFAULT -1,
  `securityCode` int(11) NOT NULL DEFAULT -1,
  `timeSecurity` bigint(20) NOT NULL DEFAULT -1,
  `items` longtext NOT NULL,
  `lastTime` bigint(20) NOT NULL,
  `pointEvent` int(11) NOT NULL DEFAULT 0,
  `radas` varchar(5000) NOT NULL DEFAULT '[]',
  `totalGold` int(11) NOT NULL DEFAULT 0,
  `isCan` tinyint(1) NOT NULL DEFAULT 0,
  `yesterday` bigint(20) NOT NULL DEFAULT 0,
  `timeReceiveNamek` bigint(20) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `options`
--

INSERT INTO `options` (`playerId`, `cName`, `cgender`, `head`, `ctaskId`, `ctaskIndex`, `ctaskCount`, `cPower`, `cPowerLimit`, `mapTemplateId`, `cx`, `cy`, `nClassId`, `xu`, `luong`, `luongKhoa`, `cHPGoc`, `cMPGoc`, `cHP`, `cMP`, `cDamGoc`, `cDefGoc`, `cCriticalGoc`, `cTiemNang`, `skills`, `arrItemBody`, `typeTeleport`, `KSkill`, `OSkill`, `CSkill`, `itemTimes`, `cStamina`, `cMaxStamina`, `cspeacialSkill`, `clanId`, `securityCode`, `timeSecurity`, `items`, `lastTime`, `pointEvent`, `radas`, `totalGold`, `isCan`, `yesterday`, `timeReceiveNamek`) VALUES
(1, 'admin', 0, 64, 34, 0, 0, 10026010, 0, 5, 1300, 408, 0, 4998798177, 10000000, 10000000, 10000000, 10000000, 10000191, 9000294, 10000000, 0, 0, 27212, '[[0,1740365918605]]', '[[0,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,2]],\"\",\"\",\"\",0,false,64,14,58,-1,-1,-1],[6,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,30]],\"\",\"\",\"\",0,false,64,57,15,-1,-1,-1],[1028,8,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[147,5],[77,5],[103,5],[95,5],[96,5],[93,5],[30,0]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,1740883257351]]', 1, '[0]', '[0]', '[0]', '[]', 9994, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1740364857336, 0, '[]', 0, 0, 1740364857347, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`playerId`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `options`
--
ALTER TABLE `options`
  MODIFY `playerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
