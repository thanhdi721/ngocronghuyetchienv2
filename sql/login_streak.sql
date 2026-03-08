-- ================================================================
-- Chuỗi đăng nhập hàng ngày (Daily Login Streak) – 7 ngày, quà 10 thỏi vàng 457 (khóa giao dịch)
-- Chạy trên database nro_root (cùng DB chứa bảng player)
-- ================================================================

CREATE TABLE IF NOT EXISTS `login_streak` (
  `playerId` INT PRIMARY KEY,
  `lastLoginDate` VARCHAR(10) NOT NULL COMMENT 'Ngày nhận quà gần nhất, yyyy-MM-dd',
  `streakDays` INT NOT NULL DEFAULT 1 COMMENT 'Số ngày đăng nhập liên tục hiện tại (1-7)'
);
