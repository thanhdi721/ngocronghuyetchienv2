-- ================================================================
-- NPC "Hành trình mùa" (Battle Pass) – đứng cạnh NPC Điểm online tại Đảo Kame (map 5)
-- Chạy trên database nro_data (bảng npctemplate)
-- Ngoại hình: cải trang 1708 (CT Androi21 Thân thiện) – head/body/leg từ CaiTrang.java case 1708
-- ================================================================
-- Cải trang 1708: headTemp=1517, bodyTemp=1520, legTemp=1521

INSERT INTO `npctemplate` (`npcTemplateId`, `name`, `headId`, `bodyId`, `legId`, `menu`) VALUES
(86, 'Hành trình mùa', 1517, 1520, 1521, '[]');
