-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 22, 2025 lúc 05:24 AM
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
-- Cơ sở dữ liệu: `nro_root`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `gmail` varchar(100) NOT NULL DEFAULT '0',
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `money` bigint(20) NOT NULL DEFAULT 100000000,
  `level` tinyint(4) NOT NULL,
  `verified` bigint(20) NOT NULL,
  `time` datetime NOT NULL DEFAULT current_timestamp(),
  `playerId` int(11) NOT NULL DEFAULT -1,
  `isLock` tinyint(4) NOT NULL DEFAULT 0,
  `isAdmin` tinyint(4) NOT NULL DEFAULT 0,
  `lastIP` varchar(100) NOT NULL DEFAULT '127.0.0.1',
  `isLoad` tinyint(1) NOT NULL DEFAULT 1,
  `lastlogout` int(11) NOT NULL DEFAULT 0,
  `isCan` tinyint(3) NOT NULL,
  `toalGold` int(11) NOT NULL,
  `active` int(11) NOT NULL DEFAULT 1,
  `spin_free` int(11) NOT NULL DEFAULT 0,
  `last_spin_time` datetime DEFAULT NULL,
  `diem_nap` int(11) NOT NULL DEFAULT 0,
  `phone` text DEFAULT NULL,
  `pass_cap2` varchar(255) DEFAULT NULL,
  `tongnap` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `gmail`, `username`, `password`, `money`, `level`, `verified`, `time`, `playerId`, `isLock`, `isAdmin`, `lastIP`, `isLoad`, `lastlogout`, `isCan`, `toalGold`, `active`, `spin_free`, `last_spin_time`, `diem_nap`, `phone`, `pass_cap2`, `tongnap`) VALUES
(1, '0', '1', '1', 10010000, 0, 1, '2025-05-19 14:18:03', 6, 0, 1, '163.61.111.226', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(102, '0', '3', '3', 0, 0, 0, '2025-05-20 13:01:02', 14, 0, 1, '116.102.30.152', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(103, '0', '2', '2', 500000, 0, 0, '2025-05-20 13:01:06', 13, 0, 1, '116.102.30.152', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(104, '0', '5', '5', 0, 0, 0, '2025-05-20 13:01:37', 8, 0, 1, '163.61.111.226', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(105, '0', '4', '4', 0, 0, 0, '2025-05-20 13:01:41', 15, 0, 1, '116.102.30.152', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(106, '0', '6', '6', 0, 0, 0, '2025-05-20 13:02:36', 16, 0, 1, '116.102.30.152', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(107, '0', '7', '7', 0, 0, 0, '2025-05-20 13:02:39', -1, 0, 1, '127.0.0.1', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(108, '0', '8', '8', 0, 0, 0, '2025-05-21 12:07:35', 17, 0, 1, '163.61.111.226', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(109, '0', 'tindzvcl', 'tin123', 0, 0, 0, '2025-05-21 15:42:19', 9, 0, 1, '123.20.129.160', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(110, '0', 'tindzvcll', 'tin123', 0, 0, 0, '2025-05-21 15:42:31', 10, 0, 1, '123.20.129.160', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(111, '0', 'tindzvclll', 'tin123', 0, 0, 0, '2025-05-21 15:42:43', 11, 0, 1, '123.20.129.160', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(112, '0', 'tindzvcllll', 'tin123', 0, 0, 0, '2025-05-21 15:42:53', 12, 0, 1, '123.20.129.160', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0),
(113, '0', 'nguyendz', '1', 100000000, 0, 0, '2025-05-21 23:14:41', -1, 0, 0, '127.0.0.1', 1, 0, 0, 0, 1, 0, NULL, 0, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `baiviet`
--

CREATE TABLE `baiviet` (
  `id` int(11) NOT NULL,
  `account_id` text NOT NULL,
  `top_baiviet` int(11) NOT NULL,
  `new` text NOT NULL,
  `binhluan` int(11) NOT NULL DEFAULT 0,
  `tieude` text NOT NULL,
  `noidung` text NOT NULL,
  `time` varchar(99) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `card_denominations`
--

CREATE TABLE `card_denominations` (
  `id` int(11) NOT NULL,
  `value` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `card_denominations`
--

INSERT INTO `card_denominations` (`id`, `value`, `created_at`, `updated_at`) VALUES
(1, '10.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(2, '20.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(3, '50.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(4, '100.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(5, '200.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(6, '300.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(7, '500.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(8, '1.000.000 VNĐ', '2025-03-01 18:43:14', '2025-03-01 18:43:14');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `card_networks`
--

CREATE TABLE `card_networks` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `card_networks`
--

INSERT INTO `card_networks` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'Viettel', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(2, 'Vinaphone', '2025-03-01 18:43:14', '2025-03-01 18:43:14'),
(3, 'Mobifone', '2025-03-01 18:43:14', '2025-03-01 18:43:14');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `card_recharge_rates`
--

CREATE TABLE `card_recharge_rates` (
  `id` int(11) NOT NULL,
  `rate` varchar(10) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `card_recharge_rates`
--

INSERT INTO `card_recharge_rates` (`id`, `rate`, `created_at`, `updated_at`) VALUES
(1, '100%', '2025-03-01 18:43:14', '2025-03-02 04:41:49');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `card_transactions`
--

CREATE TABLE `card_transactions` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `network` varchar(50) NOT NULL,
  `denomination` varchar(20) NOT NULL,
  `code` varchar(15) NOT NULL,
  `serial` varchar(15) NOT NULL,
  `status` enum('pending','success','failed','success_wrong_value','maintenance') DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `card_transactions`
--

INSERT INTO `card_transactions` (`id`, `user_id`, `network`, `denomination`, `code`, `serial`, `status`, `created_at`, `updated_at`) VALUES
(1, 211, 'Viettel', '50.000 VNĐ', '719913813887904', '10010904744863', 'pending', '2025-03-20 13:52:52', '2025-03-20 13:52:52'),
(2, 297, 'Mobifone', '50.000 VNĐ', '614212857557', '101035000054257', 'pending', '2025-03-21 03:55:40', '2025-03-21 03:55:40'),
(3, 297, 'Mobifone', '100.000 VNĐ', '925862703598', '101056000003098', 'pending', '2025-03-21 17:03:50', '2025-03-21 17:03:50'),
(4, 201, 'Viettel', '20.000 VNĐ', '814684829066170', '10010773513141', 'pending', '2025-03-24 08:01:35', '2025-03-24 08:01:35'),
(5, 201, 'Viettel', '20.000 VNĐ', '814864829066170', '10010773513141', 'pending', '2025-03-24 08:11:50', '2025-03-24 08:11:50'),
(6, 190, 'Viettel', '500.000 VNĐ', '13257719910362', '19009278091265', 'failed', '2025-03-25 07:45:02', '2025-03-25 07:45:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment_bai_viet`
--

CREATE TABLE `comment_bai_viet` (
  `id` int(11) NOT NULL,
  `baiviet_id` text NOT NULL,
  `khach_id` text NOT NULL,
  `noidung` text NOT NULL,
  `time` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `money_bank`
--

CREATE TABLE `money_bank` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `received` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` enum('wait','complete','cancel') NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `magd` varchar(255) DEFAULT NULL,
  `bank_code` varchar(100) DEFAULT NULL,
  `content` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `naptien`
--

CREATE TABLE `naptien` (
  `id` int(11) NOT NULL,
  `uid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sotien` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `seri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `loaithe` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `time` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `noidung` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tinhtrang` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tranid` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `magioithieu` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `pakage_new`
--

CREATE TABLE `pakage_new` (
  `id` int(11) NOT NULL,
  `recharge_all` int(11) NOT NULL,
  `luong` int(25) NOT NULL,
  `xu` int(25) NOT NULL,
  `yen` int(25) NOT NULL,
  `gift_name` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name_package` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `pakage_new`
--

INSERT INTO `pakage_new` (`id`, `recharge_all`, `luong`, `xu`, `yen`, `gift_name`, `name_package`) VALUES
(1, 20, 0, 0, 0, '[{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":50,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":38},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":10,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":383}]', 'Gói Cơ Bản'),
(2, 100, 0, 0, 0, '[{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":50,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":38},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":10,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":384},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":1,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":797}]', 'Gói Nâng Cao'),
(3, 300, 0, 0, 0, '[{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":100,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":38},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":10,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":385},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":1,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":820}]', 'Gói Trung Cấp'),
(4, 500, 0, 0, 0, '[{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":200,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":38},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":10,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":385},{\"isLock\":true,\"new\":true,\"yen\":0,\"quantity\":1,\"updated_at\":1693032915855,\"expire\":-1,\"created_at\":1693032915855,\"id\":851}]', 'Gói Cao Cấp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `pakage_spending`
--

CREATE TABLE `pakage_spending` (
  `id` int(11) NOT NULL,
  `recharge_all` int(11) NOT NULL,
  `luong` int(25) NOT NULL,
  `xu` int(25) NOT NULL,
  `yen` int(25) NOT NULL,
  `gift_name` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name_package` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `pakage_spending`
--

INSERT INTO `pakage_spending` (`id`, `recharge_all`, `luong`, `xu`, `yen`, `gift_name`, `name_package`) VALUES
(1, 50000, 0, 0, 0, '[]', 'Gói Cơ Bản'),
(2, 100000, 0, 0, 0, '[]', 'Gói Nâng Cao'),
(3, 200000, 0, 0, 0, '[]', 'Gói Trung Cấp'),
(4, 500000, 0, 0, 0, '[]', 'Gói Cao Cấp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `player`
--

CREATE TABLE `player` (
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
  `isCan` tinyint(1) NOT NULL DEFAULT 1,
  `yesterday` bigint(20) NOT NULL DEFAULT 0,
  `timeReceiveNamek` bigint(20) NOT NULL DEFAULT 0,
  `tongnap` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `player`
--

INSERT INTO `player` (`playerId`, `cName`, `cgender`, `head`, `ctaskId`, `ctaskIndex`, `ctaskCount`, `cPower`, `cPowerLimit`, `mapTemplateId`, `cx`, `cy`, `nClassId`, `xu`, `luong`, `luongKhoa`, `cHPGoc`, `cMPGoc`, `cHP`, `cMP`, `cDamGoc`, `cDefGoc`, `cCriticalGoc`, `cTiemNang`, `skills`, `arrItemBody`, `typeTeleport`, `KSkill`, `OSkill`, `CSkill`, `itemTimes`, `cStamina`, `cMaxStamina`, `cspeacialSkill`, `clanId`, `securityCode`, `timeSecurity`, `items`, `lastTime`, `pointEvent`, `radas`, `totalGold`, `isCan`, `yesterday`, `timeReceiveNamek`, `tongnap`) VALUES
(6, 'admin', 0, 31, 31, 1, 0, 17999999999, 0, 5, 306, 288, 0, 931901631000, 0, 4994008, 500000000, 500000000, 682030008, 723934747, 500000000, 0, 0, 1468874, '[[1,1747881140521]]', '[[232,0,5,1,false,0,0,17000000,0,0,0,0,0,0,-1,-1,false,false,[[47,300],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,64,157,58,-1,-1,-1],[244,1,5,1,false,0,0,17000000,0,0,0,0,0,0,-1,-1,false,false,[[6,22000],[27,3500],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,64,57,158,-1,-1,-1],[255,2,5,1,false,0,0,20000000,0,0,0,0,0,0,-1,-1,false,false,[[0,1000],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[269,3,5,1,false,0,0,34000000,0,0,0,0,0,0,-1,-1,false,false,[[7,24000],[28,3000],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[57,4,5,1,false,0,0,6000,0,0,0,0,0,0,-1,-1,false,false,[[14,2],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[1803,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[50,29],[77,24],[103,27],[94,6],[108,9],[5,8],[14,5],[154,0]],\"\",\"\",\"\",0,false,1622,1625,1626,-1,-1,-1],[531,6,5,1,false,0,0,0,0,3000,0,0,0,0,-1,-1,false,false,[[9,468],[107,9],[97,45],[102,9]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[532,9,5,1,false,0,0,0,0,1000,0,0,0,0,-1,-1,false,false,[[89,0]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[1046,10,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[77,10],[103,14],[50,9],[93,5]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,1748395840226],[974,11,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[0,206],[50,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]]', 3, '[0]', '[0]', '[0]', '[]', 9897, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747693870312, 0, '[[829,1,0,false],[828,2,0,false],[832,1,0,false],[837,1,0,false]]', 0, 1, 1747873035845, 0, 0),
(8, 'xuuuukrm', 1, 29, 12, 1, 0, 76538725, 0, 33, 1212, 360, 1, 500094000, 7641, 0, 2100, 2200, 1356, 1874, 1132, 0, 0, 13494593, '[[14,1747837175542]]', '[[1,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,2]],\"\",\"\",\"\",0,false,29,10,60,-1,-1,-1],[7,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,29,59,11,-1,-1,-1],[12,4,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[14,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[421,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[50,26],[77,26],[103,19],[101,1],[5,10],[14,5],[106,0],[93,3]],\"\",\"\",\"\",0,false,314,315,316,-1,-1,1748180516932]]', 1, '[2]', '[2]', '[2]', '[]', 9076, 10000, '[-1,0,0]', 165, -1, 0, '[]', 1747820532220, 0, '[]', 0, 1, 1747820532261, 0, 0),
(9, 'adminnn', 2, 6, 2, 0, 0, 1754, 0, 14, 629, 408, 2, 500000000, 0, 0, 100, 100, 120, 46, 15, 0, 0, 1754, '[[28,1747834476715]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1]]', 1, '[4]', '[4]', '[4]', '[]', 9936, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747829876312, 0, '[]', 0, 0, 1747829876314, 0, 0),
(10, 'tindzvcll', 2, 28, 1, 1, 0, 1336, 0, 14, 735, 408, 2, 500000000, 10000, 0, 100, 100, 120, 100, 15, 0, 0, 1336, '[[28,1747838240422]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1],[12,4,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[14,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]]', 1, '[4]', '[4]', '[4]', '[]', 9834, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747830307092, 0, '[]', 0, 0, 1747830307095, 0, 0),
(11, 'tindzvclll', 2, 28, 1, 0, 0, 1200, 0, 14, 665, 408, 2, 500000000, 10000, 0, 100, 100, 120, 100, 15, 0, 0, 1200, '[[28,0]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1]]', 1, '[4]', '[4]', '[4]', '[]', 10000, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747830411511, 0, '[]', 0, 0, 1747830411515, 0, 0),
(12, 'tindzvcllz', 2, 28, 1, 0, 0, 1200, 0, 14, 532, 408, 2, 500000000, 10000, 0, 100, 100, 120, 100, 15, 0, 0, 1200, '[[28,0]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1]]', 1, '[4]', '[4]', '[4]', '[]', 10000, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747830455907, 0, '[]', 0, 0, 1747830455910, 0, 0),
(13, '432432', 1, 9, 22, 3, 65, 18000944894, 5, 5, 1222, 408, 1, 3941622554837, 10385128, 9995439, 450000, 2200, 102906, 3700, 10071, 0, 0, 7845633705, '[[15,1747883838511],[23,1747853458815],[49,0]]', '[[157,1,5,1,false,0,0,90000,0,0,0,0,0,0,-1,-1,false,false,[[6,1200],[27,480],[107,6],[97,30],[102,6]],\"\",\"\",\"\",0,false,29,59,68,-1,-1,-1],[160,2,5,1,false,0,0,60000,0,0,0,0,0,0,-1,-1,false,false,[[0,55],[107,6],[97,30],[102,6],[72,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[165,3,5,1,false,0,0,70000,0,0,0,0,0,0,-1,-1,false,false,[[7,1500],[28,300],[107,6],[97,30],[102,6]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[12,4,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[14,1],[107,6],[97,30],[102,6]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[464,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[50,24],[117,15],[114,25],[77,24],[30,0],[154,0]],\"\",\"\",\"\",0,false,409,410,411,-1,-1,-1],[1160,8,5,1,false,0,0,0,0,0,0,0,20,0,4028,-1,false,false,[[50,20],[95,10],[96,10],[30,0],[203,4]],\"\",\"\",\"\",0,true,-1,-1,-1,99,-1,-1],[350,9,5,1,false,0,0,0,0,500,0,0,0,0,-1,-1,false,false,[[85,0]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]]', 3, '[2]', '[2]', '[2]', '[[32005,1,313,-9999,[1902,4,3,195,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[101,500]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]],[32006,1,313,-9999,[1903,5,3,96,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[101,700]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]],[32003,1,329,-9999,[1900,64,3,95,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[101,200]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]],[32004,1,329,-9999,[1901,63,3,95,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[101,300]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]]]', 10000, 10000, '[9,28,0]', 165, -1, 0, '[]', 1747832637282, 0, '[[828,1,0,false],[829,1,0,false],[832,2,0,false],[834,1,0,false],[968,2,8,true]]', 0, 1, 1747846800006, 0, 0),
(14, '77653', 2, 6, 13, 2, 6, 115766861, 0, 5, 1239, 408, 2, 225117000, 6510, 0, 10100, 2100, 12042, 1898, 1326, 0, 0, 25745729, '[[29,1747853927935],[56,0],[35,0]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1],[12,4,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[14,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[765,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[50,22],[77,19],[103,19],[80,5],[28,5],[177,0],[93,3]],\"\",\"\",\"\",0,false,427,428,429,-1,-1,1748180006589]]', 1, '[4]', '[4]', '[4]', '[[4387,1,3547,0]]', 10000, 10000, '[-1,0,0]', 165, -1, 0, '[]', 1747834237347, 0, '[[828,2,0,false],[833,2,0,false],[829,1,0,false],[836,3,0,false]]', 0, 1, 1747847973802, 0, 0),
(15, 'hgfh5', 2, 27, 13, 3, 0, 87650445, 0, 32, 1260, 288, 2, 500116000, 7179, 0, 12100, 2100, 11938, 1101, 1276, 0, 0, 2560557, '[[29,1747838731148],[56,0],[35,0]]', '[[2,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,3]],\"\",\"\",\"\",0,false,28,16,58,-1,-1,-1],[8,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,20]],\"\",\"\",\"\",0,false,28,57,17,-1,-1,-1],[12,4,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[14,1]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[765,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[50,22],[77,19],[103,19],[80,5],[28,5],[177,0],[93,3]],\"\",\"\",\"\",0,false,427,428,429,-1,-1,1748180538650]]', 1, '[4]', '[4]', '[4]', '[]', 9610, 10000, '[-1,0,0]', 165, -1, 0, '[]', 1747834731946, 0, '[[830,1,0,false],[835,1,0,false],[832,1,0,false]]', 0, 0, 1747834731949, 0, 0),
(16, 'dfsd45', 1, 29, 13, 0, 0, 113226146, 0, 15, 920, 312, 1, 500190000, 507947, 0, 2100, 20200, 2496, 20561, 1142, 0, 0, 37845770, '[[15,1747839210212],[49,0],[21,0]]', '[[1,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,2],[131,0],[143,0],[30,0]],\"\",\"\",\"\",0,false,29,10,60,-1,-1,-1],[22,2,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[0,4],[132,0],[144,0],[30,0]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[765,5,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,true,false,[[50,22],[77,19],[103,19],[80,5],[28,5],[177,0],[93,3]],\"\",\"\",\"\",0,false,427,428,429,-1,-1,1748180905602]]', 1, '[2]', '[2]', '[2]', '[]', 9422, 10000, '[-1,0,0]', 165, -1, 0, '[]', 1747834805546, 0, '[[833,1,0,false]]', 0, 0, 1747834805548, 0, 0),
(17, 'loilacno1', 0, 31, 1, 0, 2, 14228, 0, 1, 540, 408, 0, 500000000, 19994, 0, 200, 100, 11, 0, 12, 0, 0, 14228, '[[0,1747839020387]]', '[[0,0,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[47,2]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1],[6,1,5,1,false,0,0,0,0,0,0,0,0,0,-1,-1,false,false,[[6,30]],\"\",\"\",\"\",0,false,-1,-1,-1,-1,-1,-1]]', 1, '[0]', '[0]', '[0]', '[]', 9660, 10000, '[-1,0,0]', -1, -1, 0, '[]', 1747837452326, 0, '[]', 0, 0, 1747837452331, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

CREATE TABLE `posts` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `slug` varchar(255) NOT NULL,
  `title` text NOT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `img_url` varchar(255) NOT NULL,
  `views` int(11) NOT NULL DEFAULT 0,
  `coin` int(25) DEFAULT NULL,
  `published` int(25) DEFAULT NULL,
  `server` int(25) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recharge`
--

CREATE TABLE `recharge` (
  `id` int(11) NOT NULL,
  `bank_name` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `max_money` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `recharge`
--

INSERT INTO `recharge` (`id`, `bank_name`, `status`, `max_money`) VALUES
(1, 'Thẻ Cào', 1, 1000000),
(2, 'Momo', 0, 0),
(3, 'Ngân Hàng', 1, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `order_id` int(11) NOT NULL DEFAULT 0,
  `net_amount` int(25) DEFAULT NULL,
  `balance_before` int(11) NOT NULL DEFAULT 0,
  `balance_change` int(11) NOT NULL DEFAULT 0,
  `balance_after` int(11) NOT NULL DEFAULT 0,
  `luong_before` int(10) UNSIGNED DEFAULT 0,
  `luong_change` int(10) UNSIGNED DEFAULT 0,
  `luong_after` int(10) UNSIGNED DEFAULT 0,
  `status` enum('wait','complete','cancel','') NOT NULL,
  `extras` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `notes` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `items` longtext NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `transactions`
--

INSERT INTO `transactions` (`id`, `user_id`, `order_id`, `net_amount`, `balance_before`, `balance_change`, `balance_after`, `luong_before`, `luong_change`, `luong_after`, `status`, `extras`, `notes`, `items`, `created_at`, `updated_at`) VALUES
(1, 211, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Đang Chờ Xử Lý', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, 297, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Đang Chờ Xử Lý', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(3, 297, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Đang Chờ Xử Lý', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(4, 201, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Đang Chờ Xử Lý', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(5, 201, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Đang Chờ Xử Lý', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(6, 190, 0, NULL, 0, 0, 0, 0, 0, 0, '', 'Nạp Card', 'Thẻ Lỗi', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `web_info`
--

CREATE TABLE `web_info` (
  `id` int(11) NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `info` int(11) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `web_info`
--

INSERT INTO `web_info` (`id`, `key`, `value`, `name`, `info`, `created_at`, `updated_at`) VALUES
(1, 'bank_token', '', '', NULL, NULL, NULL),
(2, 'bank_mk', '', '', NULL, NULL, NULL),
(3, 'bank_stk', '', '', NULL, NULL, NULL),
(4, 'bank_nd', 'HZ', '', NULL, NULL, NULL),
(5, 'atm', 'MB Bank', 'phuc', 364517289, NULL, NULL),
(6, 'bank_img', '970422', '', NULL, NULL, NULL),
(7, 'bank_bonus', '0', '', NULL, NULL, NULL),
(8, 'exp_server', '1', '', NULL, NULL, NULL),
(9, 'key_api', '', '', NULL, '', NULL),
(10, 'tongnap_thang', 'true', NULL, NULL, NULL, NULL),
(11, 'link_bank', '', NULL, NULL, NULL, NULL),
(12, 'partner_id', '65664901788', NULL, NULL, NULL, NULL),
(13, 'partner_key', '0dd0c7371d318dffda2c08407f0a5e24', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `web_settings`
--

CREATE TABLE `web_settings` (
  `id` int(11) NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `info` int(11) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `web_settings`
--

INSERT INTO `web_settings` (`id`, `key`, `value`, `name`, `info`, `created_at`, `updated_at`) VALUES
(0, 'link_zalo_1', 'https://zalo.me/g/wqqkds293', '', NULL, NULL, NULL),
(1, 'link_zalo', 'https://zalo.me/g/gdqyby211', '', NULL, NULL, NULL),
(2, 'link_page', 'https://web.facebook.com/people/Ng%E1%BB%8Dc-R%E1%BB%93ng-HanZi/61550947551477/', '', NULL, NULL, NULL),
(3, 'active_fee', '0', '', NULL, NULL, NULL),
(4, 'exchange_amounts', '{\"10000\":20,\"20000\":40,\"30000\":60,\"50000\":100,\"100000\":200,\"200000\":400,\"500000\":1100,\"1000000\":2300}', '', NULL, NULL, NULL),
(5, 'exchange_bonus', '10', '', NULL, NULL, NULL),
(10, 'ios', '', 'Phiên Bản Ios IPA', NULL, NULL, NULL),
(11, 'ios', 'https://testflight.apple.com/join/M7FH1r7V', 'Phiên Bản Ios TestFight', NULL, NULL, NULL),
(13, 'java', 'https://www.mediafire.com/file/xfxtkkj1fjz125s/HanZi237.jar/file', 'Phiên Bản JAR', NULL, NULL, NULL),
(14, 'java', 'https://www.mediafire.com/file/xfxtkkj1fjz125s/HanZi237.jar/file', '', NULL, NULL, NULL),
(15, 'pc', 'https://drive.google.com/file/d/1B3XODQgUSWWgsLQkS5PSK19aCmLB5HXQ/view?usp=sharing', '\r\nPhiên bản Cho PC', NULL, NULL, NULL),
(16, 'ratio_atm', '100', NULL, NULL, NULL, NULL),
(22, 'title_web', 'FAVO Nro', NULL, NULL, NULL, NULL),
(23, 'slideshow', 'https://cdn.jsdelivr.net/gh/L0veY0u372/cc/uploads/2024/03/687474703a2f2f6e676f63726f6e676f6e6c696e652e636f6d2f696d616765732f62616e6e65725f322e706e67.png', NULL, NULL, NULL, NULL),
(25, 'logo_img', '/images/logo.png', NULL, NULL, NULL, NULL),
(26, 'server', 'Nro Favo', '', NULL, NULL, NULL),
(27, 'server', 'Nro Favo', '', NULL, NULL, NULL),
(28, 'status_server', 'true', '', NULL, '', NULL),
(31, 'title', 'Nro Favo', NULL, NULL, NULL, NULL),
(32, 'link_web', 'favonro.online', NULL, NULL, NULL, NULL),
(34, 'apk', 'https://drive.google.com/file/d/1ua9tn_zoHPlulzFY33aRm2CLQPcqeAff/view?usp=sharing', 'Phiên Bản Cho Android', NULL, NULL, NULL),
(36, 'thong_bao', 'admin có đẹp trai không nào', '', NULL, NULL, NULL),
(37, 'price_sprin', '5000', NULL, NULL, NULL, NULL),
(38, 'thong_bao_web', 'Chào Mừng Bạn Đã Đến Với Server Ngọc Rồng Favo Chúc Các Bạn Chơi Game Vui Vẻ.', NULL, NULL, NULL, NULL),
(39, 'vxmm', 'close', NULL, NULL, NULL, NULL),
(40, 'cua_hang', 'close', NULL, NULL, NULL, NULL),
(41, 'top_nap', 'close', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `wheels`
--

CREATE TABLE `wheels` (
  `id` int(11) NOT NULL,
  `is_free_spin` tinyint(1) DEFAULT NULL,
  `items` longtext DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `published` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `wheels`
--

INSERT INTO `wheels` (`id`, `is_free_spin`, `items`, `name`, `price`, `published`, `updated_at`, `created_at`) VALUES
(1, 1, '[\n    {\"id\": 1, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 70},\n    {\"id\": 475, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 35},\n    {\"id\": 340, \"quantity\": 50, \"isLock\": true, \"expire\":-1, \"ratio\":10},\n    {\"id\": 539, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 12},\n    {\"id\": 540, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 10},\n    {\"id\": 10, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 15},\n    {\"id\": 11, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 10},\n    {\"id\": 383, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 5},\n    {\"id\": 384, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 2},\n    {\"id\": 385, \"quantity\": 1, \"isLock\": true, \"expire\":-1, \"ratio\": 1}\n]\n', 'Vòng quay may mắn', 5000, 1, '2025-03-01 14:01:33', '2024-06-08 12:15:23');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `baiviet`
--
ALTER TABLE `baiviet`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `card_denominations`
--
ALTER TABLE `card_denominations`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `card_networks`
--
ALTER TABLE `card_networks`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `name` (`name`) USING BTREE;

--
-- Chỉ mục cho bảng `card_recharge_rates`
--
ALTER TABLE `card_recharge_rates`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `card_transactions`
--
ALTER TABLE `card_transactions`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `user_id` (`user_id`) USING BTREE;

--
-- Chỉ mục cho bảng `comment_bai_viet`
--
ALTER TABLE `comment_bai_viet`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `money_bank`
--
ALTER TABLE `money_bank`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `naptien`
--
ALTER TABLE `naptien`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `pakage_new`
--
ALTER TABLE `pakage_new`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `pakage_spending`
--
ALTER TABLE `pakage_spending`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`playerId`);

--
-- Chỉ mục cho bảng `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `recharge`
--
ALTER TABLE `recharge`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `transactions_user_id_foreign` (`user_id`) USING BTREE;

--
-- Chỉ mục cho bảng `web_info`
--
ALTER TABLE `web_info`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `web_settings`
--
ALTER TABLE `web_settings`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `wheels`
--
ALTER TABLE `wheels`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT cho bảng `baiviet`
--
ALTER TABLE `baiviet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `card_denominations`
--
ALTER TABLE `card_denominations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `card_networks`
--
ALTER TABLE `card_networks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `card_recharge_rates`
--
ALTER TABLE `card_recharge_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `card_transactions`
--
ALTER TABLE `card_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `comment_bai_viet`
--
ALTER TABLE `comment_bai_viet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `money_bank`
--
ALTER TABLE `money_bank`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `naptien`
--
ALTER TABLE `naptien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=584;

--
-- AUTO_INCREMENT cho bảng `pakage_new`
--
ALTER TABLE `pakage_new`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT cho bảng `pakage_spending`
--
ALTER TABLE `pakage_spending`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT cho bảng `player`
--
ALTER TABLE `player`
  MODIFY `playerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `posts`
--
ALTER TABLE `posts`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `web_info`
--
ALTER TABLE `web_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
