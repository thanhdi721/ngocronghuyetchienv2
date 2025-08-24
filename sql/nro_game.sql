-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 22, 2025 lúc 05:28 AM
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
-- Cơ sở dữ liệu: `nro_game`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `luyentap`
--

CREATE TABLE `luyentap` (
  `id` int(11) NOT NULL,
  `playerId` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `head` smallint(6) NOT NULL,
  `body` smallint(6) NOT NULL,
  `leg` smallint(6) NOT NULL,
  `level` int(11) NOT NULL,
  `timeFight` int(11) NOT NULL,
  `last` bigint(20) NOT NULL,
  `gift` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `luyentap`
--

INSERT INTO `luyentap` (`id`, `playerId`, `name`, `head`, `body`, `leg`, `level`, `timeFight`, `last`, `gift`) VALUES
(146495, 6, 'admin', 1622, 1625, 1626, 7, 2878, 1747817466957, 0),
(146496, 0, '', 0, 0, 0, 0, 0, 0, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `luyentap`
--
ALTER TABLE `luyentap`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `luyentap`
--
ALTER TABLE `luyentap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146497;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
