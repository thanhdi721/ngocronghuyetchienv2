-- ================================================================
-- Battle Pass / Hành trình mùa – 1 mùa = 1 tháng, reset ngày 1
-- Chạy trên database cùng DB chứa player (nro_root / nro_game)
-- ================================================================

CREATE TABLE IF NOT EXISTS `season_pass` (
  `playerId` INT NOT NULL,
  `seasonId` VARCHAR(10) NOT NULL COMMENT 'yyyy-MM theo tháng',
  `level` INT NOT NULL DEFAULT 0,
  `exp` INT NOT NULL DEFAULT 0,
  `passType` TINYINT NOT NULL DEFAULT 0 COMMENT '0=FREE, 1=PRO, 2=MASTER',
  `lastClaimLevel` INT NOT NULL DEFAULT 0 COMMENT 'Cấp đã nhận quà gần nhất (claim)',
  PRIMARY KEY (`playerId`, `seasonId`)
);

CREATE TABLE IF NOT EXISTS `season_pass_task` (
  `playerId` INT NOT NULL,
  `seasonId` VARCHAR(10) NOT NULL,
  `fideKillCount` INT NOT NULL DEFAULT 0,
  `tieuDoiTruongKillCount` INT NOT NULL DEFAULT 0,
  `superBojackKillCount` INT NOT NULL DEFAULT 0,
  `kingKongKillCount` INT NOT NULL DEFAULT 0,
  `onlineMinutes` INT NOT NULL DEFAULT 0,
  `monsterKillCount` INT NOT NULL DEFAULT 0,
  `monsterMilestoneGiven` TINYINT NOT NULL DEFAULT 0 COMMENT '0=chưa, 1=Free 400k đã nhận, 2=VIP 1M đã nhận',
  `pvpKillCount` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`playerId`, `seasonId`)
);

-- Nếu bảng season_pass_task đã tồn tại trước khi thêm cột monsterMilestoneGiven, chạy:
-- ALTER TABLE `season_pass_task` ADD COLUMN `monsterMilestoneGiven` TINYINT NOT NULL DEFAULT 0 AFTER `monsterKillCount`;
