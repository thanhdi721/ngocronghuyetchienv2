-- ================================================================
-- NPC "Điểm online" – đổi điểm thời gian tại Đảo Kame (map 5)
-- Chạy trên database nro_data (bảng npctemplate)
-- Ngoại hình: cải trang 1746 (CT SSJ Blue) – head/body/leg lấy từ CaiTrang.java case 1746
-- ================================================================
-- Cải trang 1746 trong CaiTrang.java: headTemp=1563, bodyTemp=1565, legTemp=1566

-- Nếu ID 84 đã tồn tại (đã INSERT trước đó), chạy lệnh UPDATE sau để sửa đúng head/body/leg:
-- UPDATE `npctemplate` SET `name`='Điểm online', `headId`=1563, `bodyId`=1565, `legId`=1566, `menu`='[]' WHERE `npcTemplateId`=84;

INSERT INTO `npctemplate` (`npcTemplateId`, `name`, `headId`, `bodyId`, `legId`, `menu`) VALUES
(84, 'Điểm online', 1563, 1565, 1566, '[]');
