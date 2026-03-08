-- ================================================================
-- SQL tạo bảng Đạo Lữ cho database nro_player
-- Chạy file này trên database nro_player
-- ================================================================

-- Bảng lưu dữ liệu Đạo Lữ (1 player tối đa 1 Đạo Lữ)
-- Cấu trúc giống bảng petzs (lưu pet) nhưng thêm các field tu tiên
CREATE TABLE IF NOT EXISTS `daolus` (
    `playerId`        INT NOT NULL,                -- ID player sở hữu (FK)
    `have`            TINYINT(1) DEFAULT 0,        -- Có Đạo Lữ hay không (0=không, 1=có)

    -- ===== Thông tin cơ bản =====
    `name`            VARCHAR(50) DEFAULT '',       -- Tên Đạo Lữ do player đặt
    `typeDaoLu`       TINYINT DEFAULT 1,            -- Phẩm: 1=Nhất, 2=Nhị, 3=Tam
    `cgender`         TINYINT DEFAULT 0,            -- Giới tính: 0=TD, 1=NM, 2=XD
    `status`          TINYINT DEFAULT 0,            -- Trạng thái: 0=Follow, 2=Attack, 3=GoHome

    -- ===== Hệ thống tu tiên =====
    `pointTuVi`       INT DEFAULT 0,                -- Điểm Tu Vi (0-1000)
    `pointCapTinh`    INT DEFAULT 0,                -- Cấp tinh hiện tại
    `pointCapCanhGioi` INT DEFAULT 0,               -- Cấp bậc cảnh giới (0-10)
    `timeThangCap`    BIGINT DEFAULT 0,             -- Thời điểm thăng cấp gần nhất (ms)
    `isTransform`     TINYINT(1) DEFAULT 0,         -- Đang biến hình
    `isMacDo`         TINYINT(1) DEFAULT 0,         -- Cho mặc đồ hiển thị
    `var2`            INT DEFAULT 0,                -- Biến dự phòng mở rộng

    -- ===== Chỉ số chiến đấu =====
    `cPower`          BIGINT DEFAULT 0,             -- Sức mạnh
    `cPowerLimit`     TINYINT DEFAULT 0,            -- Giới hạn sức mạnh
    `cHPGoc`          INT DEFAULT 0,                -- HP gốc
    `cMPGoc`          INT DEFAULT 0,                -- MP gốc
    `cDamGoc`         INT DEFAULT 0,                -- Sát thương gốc
    `cDefGoc`         INT DEFAULT 0,                -- Phòng thủ gốc
    `cCriticalGoc`    INT DEFAULT 0,                -- Chí mạng gốc
    `cTiemNang`       BIGINT DEFAULT 0,             -- Tiềm năng
    `cHP`             INT DEFAULT 0,                -- HP hiện tại
    `cMP`             INT DEFAULT 0,                -- MP hiện tại
    `cStamina`        SMALLINT DEFAULT 1000,        -- Thể lực hiện tại
    `cMaxStamina`     SMALLINT DEFAULT 1000,        -- Thể lực tối đa

    -- ===== Trang bị & Skill (JSON) =====
    `arrItemBody`     TEXT DEFAULT '[]',            -- Trang bị đang mặc (JSON array)
    `skills`          TEXT DEFAULT '[]',            -- Danh sách skill (JSON array)

    -- ===== Khóa chính =====
    PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================================================
-- Thêm item template cho Đạo Lữ vào database nro_data
-- Chạy SQL này trên database nro_data (bảng `itemtemplate`)
-- Item ID từ 2070 trở đi, icon ID từ 24000 trở đi (không trùng item khác)
-- ================================================================

-- Nếu đã INSERT trong nro_data.sql thì bỏ qua phần này (dùng IGNORE để tránh lỗi trùng)

-- Hồn Đạo Lữ (ID 2070) - dùng để chiêu mộ Đạo Lữ
INSERT IGNORE INTO `itemtemplate` (`id`, `type`, `gender`, `name`, `description`, `level`, `strRequire`, `iconID`, `part`, `isUpToUp`, `isNew`)
VALUES (2070, 27, 3, 'Hồn Đạo Lữ', 'Dùng để chiêu mộ đạo lữ song tu, có 5% chiêu mộ được đạo lữ tam phẩm, 30% nhận được đạo lữ nhị phẩm, còn lại là đạo lữ nhất phẩm', 1, 0, 24000, -1, 0, 1);

-- 10 loại Đan Dược (ID 2071-2080) - nguyên liệu tu luyện Đạo Lữ
INSERT IGNORE INTO `itemtemplate` (`id`, `type`, `gender`, `name`, `description`, `level`, `strRequire`, `iconID`, `part`, `isUpToUp`, `isNew`)
VALUES
  (2071, 27, 3, 'Tụ Khí Tán', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Khí', 1, 0, 24001, -1, 0, 1),
  (2072, 27, 3, 'Thanh Linh Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Giả', 1, 0, 24002, -1, 0, 1),
  (2073, 27, 3, 'Địa Linh Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Sư', 1, 0, 24003, -1, 0, 1),
  (2074, 27, 3, 'Huyền Linh Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đại Đấu Sư', 1, 0, 24004, -1, 0, 1),
  (2075, 27, 3, 'Ngưng Linh Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Linh', 1, 0, 24005, -1, 0, 1),
  (2076, 27, 3, 'Hoàng Cực Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Vương', 1, 0, 24006, -1, 0, 1),
  (2077, 27, 3, 'Thiên Tông Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Hoàng', 1, 0, 24007, -1, 0, 1),
  (2078, 27, 3, 'Thanh Hồn Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Tông', 1, 0, 24008, -1, 0, 1),
  (2079, 27, 3, 'Vô Thượng Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Tôn', 1, 0, 24009, -1, 0, 1),
  (2080, 27, 3, 'Vô Thượng Thánh Đan', 'Đan Dược chuyên dùng cho Đạo Lữ cấp bậc Đấu Thánh', 1, 0, 24010, -1, 0, 1);

-- Mảnh Đà Xá Cổ Đế Ngọc (ID 2081) - nguyên liệu thăng Đấu Đế
INSERT IGNORE INTO `itemtemplate` (`id`, `type`, `gender`, `name`, `description`, `level`, `strRequire`, `iconID`, `part`, `isUpToUp`, `isNew`)
VALUES (2081, 27, 3, 'Mảnh Đà Xá Cổ Đế Ngọc', 'Thu Thập Đủ 4 Mảnh Để Đột Phá Đạo Lữ Lên Cấp Bậc Cao Nhất - Cấp Bậc Đấu Đế!', 1, 0, 24011, -1, 0, 1);
