-- Migration: Thêm cột cho tính năng "Thời gian online tích lũy – Ở lại nhận quà"
-- Chạy từng lệnh dưới trên database chứa bảng `player` (nro_root). Nếu cột đã tồn tại thì bỏ qua lệnh đó.

ALTER TABLE `player` ADD COLUMN `todayOnlineMinutes` int(11) NOT NULL DEFAULT 0;
ALTER TABLE `player` ADD COLUMN `todayClaimedBoxes` int(11) NOT NULL DEFAULT 0;
ALTER TABLE `player` ADD COLUMN `onlinePoints` int(11) NOT NULL DEFAULT 0;
ALTER TABLE `player` ADD COLUMN `lastOnlineDate` varchar(20) NOT NULL DEFAULT '';
