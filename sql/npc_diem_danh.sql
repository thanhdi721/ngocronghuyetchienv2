-- ================================================================
-- NPC "Điểm Danh" – nhận quà chuỗi đăng nhập 7 ngày tại Đảo Kame (map 5)
-- Chạy trên database nro_data (bảng npctemplate)
-- Ngoại hình: cải trang 1803 (CT Goku SSJ 4 Red) – head/body/leg từ CaiTrang.java case 1803
-- ================================================================
-- Cải trang 1803: headTemp=1622, bodyTemp=1625, legTemp=1626

INSERT INTO `npctemplate` (`npcTemplateId`, `name`, `headId`, `bodyId`, `legId`, `menu`) VALUES
(85, 'Điểm Danh', 1622, 1625, 1626, '[]')
ON DUPLICATE KEY UPDATE `name`='Điểm Danh', `headId`=1622, `bodyId`=1625, `legId`=1626, `menu`='[]';
