package dragon.t;

import dragon.object.Char;
import dragon.object.Item;
import dragon.object.ItemOption;
import dragon.object.Map;
import dragon.object.Mob;
import dragon.object.Skill;
import dragon.object.ZoneMap;
import dragon.server.Server;
import dragon.server.Session_ME;
import dragon.server.mResources;
import dragon.u.Util;

/**
 * DaoLu - Hệ thống Đạo Lữ (bạn đồng hành tu tiên) cho cbmeil/NgocRongZeno.
 * Port từ gameplayopen DaoLu.java, adapt sang kiến trúc cbmeil (Char-based).
 *
 * Đạo Lữ là NPC companion:
 * - Kế thừa từ Char (giống hệ thống pet hiện tại myPet)
 * - Có hệ thống cảnh giới tu tiên (Đấu Khí → Đấu Đế)
 * - Có thể đi theo chủ, tự đánh quái, về nhà
 * - Buff chỉ số HP/KI/SD cho chủ dựa trên phẩm + cảnh giới + cấp tinh
 * - Tích lũy Tu Vi khi đánh quái → thăng tinh → thăng cảnh giới
 *
 * Khác biệt so với gameplayopen:
 * - gameplayopen: DaoLu extends Player (Player riêng biệt)
 * - cbmeil: DaoLu dùng Char trực tiếp (giống myPet), quản lý bằng field trên Char chủ
 * - Dùng charID âm (-charID * 111) để phân biệt với pet và player
 *
 * @author Port by AI from gameplayopen
 */
public class DaoLu {

    // ======================== Tham chiếu đến chủ nhân ========================
    /** Char gốc của Đạo Lữ (entity trên map, kế thừa Char) */
    public Char charDaoLu;

    /** Char của chủ nhân sở hữu Đạo Lữ */
    public Char master;

    // ======================== Thuộc tính Đạo Lữ ========================
    /** Tên đặt cho Đạo Lữ (do người chơi đặt khi tạo) */
    public String nameDaoLu = "";

    /** Loại phẩm: 1=Nhất Phẩm, 2=Nhị Phẩm, 3=Tam Phẩm */
    public byte typeDaoLu = ConstDaoLu.TYPE_NHAT_PHAM;

    /** Trạng thái hiện tại: FOLLOW(0), ATTACK(2), GOHOME(3) */
    public byte status = ConstDaoLu.STATUS_FOLLOW;

    /** Điểm Tu Vi (0-1000), tích lũy bằng đánh quái */
    public int pointTuVi = 0;

    /** Cấp tinh hiện tại (0-9/10/11 tùy cảnh giới) */
    public int pointCapTinh = 0;

    /** Cấp bậc cảnh giới hiện tại (0-10: Đấu Khí → Đấu Đế) */
    public int pointCapCanhGioi = 0;

    /** Thời điểm thăng cấp gần nhất (ms) - dùng cho ranking */
    public long timeThangCap = 0;

    /** Đạo Lữ có đang biến hình không (toggle hiển thị đồ mặc) */
    public boolean isTransform = false;

    /** Đã thăng Đấu Đế chưa (dùng cho hiệu ứng) */
    public boolean isThangDauDe = false;

    /** Có cho Đạo Lữ mặc đồ hiển thị không */
    public boolean isMacDo = false;

    /** Biến dự phòng mở rộng */
    public int var2 = 0;

    // ======================== Biến nội bộ cho AI ========================
    /** Đếm số đòn tấn công mob (dùng tính Tu Vi) */
    private int numAttackMob = 0;

    /** Số đòn cần để +1 Tu Vi (tính theo capTinh * capCanhGioi + 1) */
    private int numToTuVi = 0;

    /** Thời điểm chết gần nhất (ms) - dùng tính thời gian hồi sinh */
    public long lastTimeDie = 0;

    /** Đang trong quá trình về nhà (thread đang chạy) */
    private boolean goingHome = false;

    /** Mob đang tấn công (trong ATTACK mode) */
    private Mob mobAttack = null;

    /** Thời điểm idle move gần nhất (ms) */
    private long lastTimeMoveIdle = 0;

    /** Thời gian chờ giữa các lần idle move (ms) - random */
    private int timeMoveIdle = 0;

    /** Có đang ở trạng thái idle (không có quái) */
    private boolean idle = false;

    /** Thời điểm xin đậu thần gần nhất (ms) */
    private long lastTimeAskPea = 0;

    /** Thời điểm di chuyển ở nhà gần nhất (ms) */
    private long lastTimeMoveAtHome = 0;

    /** Hướng di chuyển ở nhà (-1 hoặc 1) */
    private byte directAtHome = -1;

    /** Thời điểm auto tăng chỉ số gần nhất (ms) */
    private long lastTimeIncreasePoint = 0;

    /** Thời điểm tấn công mob gần nhất (ms) - dùng cooldown */
    private long lastTimeAttack = 0;
    /** Cooldown chat khi tu luyện (5 giây) */
    private long lastTimeChatAttack = 0;
    private static final long CHAT_ATTACK_COOLDOWN = 5000;

    /** Cooldown giữa các lần tấn công (ms) = 2 giây */
    private static final long ATTACK_COOLDOWN = 2000;

    /** Cờ vừa hồi sinh (để chat thông báo 1 lần) */
    private boolean justRevived = false;

    // ======================== CONSTRUCTOR ========================

    /**
     * Tạo Đạo Lữ mới cho 1 player.
     * @param master Char của chủ nhân
     */
    public DaoLu(Char master) {
        this.master = master;
        // Tạo Char entity cho Đạo Lữ (tương tự cách tạo pet trong cbmeil)
        this.charDaoLu = new Char();
        this.charDaoLu.me = false;              // Không phải player chính
        this.charDaoLu.isDaoLu = true;          // Đánh dấu là Đạo Lữ
        this.charDaoLu.charID = -master.charID * 111;  // ID âm, nhân 111 để không trùng pet (-charID)
        this.charDaoLu.myChar = master;         // Tham chiếu ngược về chủ
    }

    // ======================== KHỞI TẠO ĐẠO LỮ MỚI ========================

    /**
     * Khởi tạo Đạo Lữ mới với chỉ số ngẫu nhiên theo phẩm.
     * Gọi khi người chơi dùng item Hồn Đạo Lữ để tạo mới.
     *
     * @param name      Tên do người chơi đặt
     * @param type      Phẩm: 1=Nhất, 2=Nhị, 3=Tam
     * @param gender    Giới tính (0=Trái Đất, 1=Namek, 2=Xayda)
     */
    public void initNew(String name, byte type, byte gender) {
        this.nameDaoLu = name;
        this.typeDaoLu = type;

        // Gán thuộc tính cho Char entity
        Char c = this.charDaoLu;
        c.cName = "$[" + ConstDaoLu.getTenPham(type) + "] " + name;
        c.cgender = gender;

        // Chỉ số gốc random theo phẩm
        // Nhất Phẩm: dame thấp, Nhị Phẩm: dame trung, Tam Phẩm: dame cao
        int[] data = getDataByType(type);
        c.cHPGoc = data[0];
        c.cMPGoc = data[1];
        c.cDamGoc = data[2];
        c.cDefGoc = data[3];
        c.cCriticalGoc = data[4];

        // Sức mạnh ban đầu
        // Nhất Phẩm bắt đầu yếu (2000), Nhị+Tam bắt đầu mạnh hơn (1500000)
        if (type == ConstDaoLu.TYPE_NHAT_PHAM) {
            c.cPower = 2000;
            c.cTiemNang = 2000;
        } else {
            c.cPower = 1500000;
            c.cTiemNang = 1500000;
        }

        // Stamina
        c.cStamina = 1000;
        c.cMaxStamina = 1000;

        // Tạo arrItemBody trống cho Đạo Lữ (slot trang bị)
        c.arrItemBody = new dragon.object.Item[Char.MAXBODY_PET];
        c.arrItemBag = new dragon.object.Item[20];
        c.arrItemBox = new dragon.object.Item[20];

        // Gán skill theo chủng tộc của chủ
        initSkills(master.cgender);

        // Cập nhật chỉ số
        c.updateAll();

        // [FIX] Gán ngoại hình (head/body/leg) cho Đạo Lữ - thiếu sẽ bị vô hình
        c.head = getHead();
        c.body = getBody();
        c.leg = getLeg();

        // Set HP/MP đầy
        c.cHP = c.cHPFull;
        c.cMP = c.cMPFull;

        // Reset trạng thái
        this.status = ConstDaoLu.STATUS_FOLLOW;
        this.pointTuVi = 0;
        this.pointCapTinh = 0;
        this.pointCapCanhGioi = 0;
        this.timeThangCap = 0;
    }

    /**
     * Random chỉ số gốc theo phẩm Đạo Lữ.
     * @param type 1=Nhất Phẩm, 2=Nhị Phẩm, 3=Tam Phẩm
     * @return int[5] = {hp, mp, dame, def, crit}
     */
    private int[] getDataByType(byte type) {
        int[] data = new int[5];
        switch (type) {
            case ConstDaoLu.TYPE_TAM_PHAM:
                // Tam Phẩm - mạnh nhất
                data[0] = Util.gI().nextInt(40, 110) * 20;   // HP gốc: 800-2200
                data[1] = Util.gI().nextInt(40, 110) * 20;   // MP gốc: 800-2200
                data[2] = Util.gI().nextInt(50, 130);         // Dame: 50-130
                data[3] = Util.gI().nextInt(9, 50);           // Def: 9-50
                data[4] = Util.gI().nextInt(0, 2);            // Crit: 0-2
                break;
            case ConstDaoLu.TYPE_NHI_PHAM:
                // Nhị Phẩm - trung bình
                data[0] = Util.gI().nextInt(40, 105) * 20;   // HP gốc: 800-2100
                data[1] = Util.gI().nextInt(40, 105) * 20;   // MP gốc: 800-2100
                data[2] = Util.gI().nextInt(50, 120);         // Dame: 50-120
                data[3] = Util.gI().nextInt(9, 50);           // Def: 9-50
                data[4] = Util.gI().nextInt(0, 2);            // Crit: 0-2
                break;
            default:
                // Nhất Phẩm - yếu nhất
                data[0] = Util.gI().nextInt(40, 105) * 20;   // HP gốc: 800-2100
                data[1] = Util.gI().nextInt(40, 105) * 20;   // MP gốc: 800-2100
                data[2] = Util.gI().nextInt(20, 45);          // Dame: 20-45
                data[3] = Util.gI().nextInt(9, 50);           // Def: 9-50
                data[4] = Util.gI().nextInt(0, 2);            // Crit: 0-2
                break;
        }
        return data;
    }

    /**
     * Gán skill cho Đạo Lữ theo chủng tộc.
     * Dùng cùng bộ skill như pet hiện tại trong cbmeil.
     * @param gender 0=Trái Đất, 1=Namek, 2=Xayda
     */
    private void initSkills(byte gender) {
        // Skill theo chủng tộc (giống cách gameplayopen gán skill)
        int[][] SKILLS = {
            {0, 1, 6, 9},      // Trái Đất: Kamejoko, Galick, Makankosappo, Masenko
            {2, 3, 7, 11},     // Namek: Antomic, Demon, Laze, Spirit
            {4, 5, 8, 13}      // Xayda: Dragon, Energy, Destructo, Finish Buster
        };
        int gIdx = (gender >= 0 && gender <= 2) ? gender : 0;
        charDaoLu.skills.clear();
        for (int skillId : SKILLS[gIdx]) {
            if (skillId >= 0 && skillId < Skill.arrSkill.length) {
                Skill skill = Skill.arrSkill[skillId].clone();
                charDaoLu.skills.add(skill);
            }
        }
    }

    // ======================== THAY ĐỔI TRẠNG THÁI ========================

    /**
     * Thay đổi trạng thái Đạo Lữ (FOLLOW, ATTACK, GOHOME).
     * @param newStatus Trạng thái mới
     */
    public void changeStatus(byte newStatus) {
        // Chặn trạng thái không hợp lệ (1=PROTECT và 4=FUSION đã bị bỏ)
        if (newStatus == 1 || newStatus == 4) {
            if (master.session != null) {
                master.session.service.chatTHEGIOI("", "Đạo lữ không có trạng thái này", null, 0);
            }
            return;
        }
        // Không thể đổi trạng thái khi đang trong quá trình về nhà
        if (goingHome) {
            if (master.session != null) {
                master.session.service.chatTHEGIOI("", "Không thể thực hiện", null, 0);
            }
            return;
        }
        // Chat thông báo trạng thái mới (chỉ chủ thấy)
        String chatText = getTextStatus(newStatus);
        if (!chatText.isEmpty() && master.session != null) {
            // Chat từ Đạo Lữ cho chủ thấy
            chatFromDaoLu(chatText);
        }
        // Xử lý về nhà
        if (newStatus == ConstDaoLu.STATUS_GOHOME) {
            goHome();
        }
        this.status = newStatus;
        this.idle = false;
        this.mobAttack = null;
    }

    /**
     * Lấy text chat khi đổi trạng thái.
     */
    private String getTextStatus(byte st) {
        switch (st) {
            case ConstDaoLu.STATUS_FOLLOW:  return ConstDaoLu.CHAT_FOLLOW;
            case ConstDaoLu.STATUS_ATTACK:  return ConstDaoLu.CHAT_ATTACK_MODE;
            case ConstDaoLu.STATUS_GOHOME:  return ConstDaoLu.CHAT_GOHOME;
            default: return "";
        }
    }

    // ======================== DI CHUYỂN ========================

    /**
     * Đạo Lữ nhập map của chủ (khi chủ đổi map).
     * Teleport Đạo Lữ đến cùng zone với chủ.
     */
    public void joinMapMaster() {
        if (status == ConstDaoLu.STATUS_GOHOME || charDaoLu.isDie) {
            return;
        }
        // [FIX] Luôn cập nhật ngoại hình trước khi join map
        charDaoLu.head = getHead();
        charDaoLu.body = getBody();
        charDaoLu.leg = getLeg();
        // Nếu Đấu Đế thì set aura
        if (isThangDauDe) {
            charDaoLu.idAuraEff = 22;
        }
        // Thoát map cũ nếu đang ở map khác
        if (charDaoLu.zoneMap != null) {
            charDaoLu.zoneMap.exit(charDaoLu, 0);
        }
        // Nhập map mới cùng chủ, vị trí gần chủ
        if (master.zoneMap != null) {
            int x = master.cx + Util.gI().nextInt(-10, 10);
            int y = master.cy;
            master.zoneMap.join(charDaoLu, 0, x, y);
        }
    }

    /**
     * Đạo Lữ bay về nhà (map 21/22/23 tùy chủng tộc chủ).
     * Chạy trên thread riêng, có delay 2 giây trước khi thực sự về.
     */
    public void goHome() {
        if (this.status == ConstDaoLu.STATUS_GOHOME) {
            return; // Đã ở nhà rồi
        }
        goingHome = true;
        new Thread(() -> {
            try {
                // Chờ 2 giây (animation bay đi)
                Thread.sleep(ConstDaoLu.TIME_GO_HOME_MS);
            } catch (InterruptedException e) {
                // Bỏ qua nếu bị interrupt
            }
            try {
                // Thoát map hiện tại
                if (charDaoLu.zoneMap != null) {
                    charDaoLu.zoneMap.exit(charDaoLu, 0);
                }
                // Tìm zone ở map nhà (21=Trái Đất, 22=Namek, 23=Xayda)
                int homeMapId = master.cgender + 21;
                ZoneMap homeZone = findZoneInMap(homeMapId);
                if (homeZone != null) {
                    homeZone.join(charDaoLu, 0, 250, 336);
                }
            } catch (Exception e) {
                // Bỏ qua lỗi khi về nhà
            }
            this.status = ConstDaoLu.STATUS_GOHOME;
            goingHome = false;
        }, "DaoLu-GoHome-" + master.charID).start();
    }

    /**
     * Tìm 1 zone trống trong map (dùng khi về nhà).
     * Duyệt qua tất cả map trên server, tìm map có id phù hợp,
     * rồi tìm zone chưa đầy (players.size < maxPlayer).
     * @param mapId ID map cần tìm
     * @return ZoneMap đầu tiên có thể join, hoặc null
     */
    private ZoneMap findZoneInMap(int mapId) {
        for (Map map : Server.gI().maps) {
            if (map.template.mapTemplateId == mapId) {
                for (ZoneMap zone : map.zones) {
                    // Kiểm tra zone chưa đầy
                    if (zone.getCountPLayerNotAI() < zone.maxPlayer) {
                        return zone;
                    }
                }
                // Nếu tất cả zone đầy, trả về zone đầu tiên
                if (!map.zones.isEmpty()) {
                    return map.zones.get(0);
                }
            }
        }
        return null;
    }

    /**
     * Đạo Lữ đi theo chủ (trong FOLLOW mode).
     * Di chuyển đến gần chủ nếu khoảng cách > dis pixel.
     * @param dis Khoảng cách tối đa để bắt đầu di chuyển
     */
    private void followMaster(int dis) {
        if (charDaoLu.isDie || master.zoneMap != charDaoLu.zoneMap) {
            return;
        }
        int mX = master.cx;
        int mY = master.cy;
        int disX = charDaoLu.cx - mX;
        // Tính khoảng cách Euclid
        double distance = Math.sqrt(Math.pow(mX - charDaoLu.cx, 2) + Math.pow(mY - charDaoLu.cy, 2));
        if (distance >= dis) {
            // Di chuyển đến gần chủ (cùng phía)
            if (disX < 0) {
                charDaoLu.cx = mX - Util.gI().nextInt(0, dis);
            } else {
                charDaoLu.cx = mX + Util.gI().nextInt(0, dis);
            }
            charDaoLu.cy = mY;
            // Gửi vị trí mới lên map
            if (charDaoLu.zoneMap != null) {
                charDaoLu.zoneMap.playerMove(charDaoLu.charID, charDaoLu.cx, charDaoLu.cy);
            }
        }
    }

    /**
     * Di chuyển idle khi rảnh (không có quái, không GOHOME).
     * Di chuyển random quanh vị trí chủ mỗi 5-8 giây.
     */
    private void moveIdle() {
        if (status == ConstDaoLu.STATUS_GOHOME || !idle) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastTimeMoveIdle >= timeMoveIdle) {
            int dir = charDaoLu.cx - master.cx <= 0 ? -1 : 1;
            int newX;
            if (dir == -1) {
                newX = master.cx + Util.gI().nextInt(30, 50);
            } else {
                newX = master.cx - Util.gI().nextInt(30, 50);
            }
            charDaoLu.cx = newX;
            charDaoLu.cy = master.cy;
            if (charDaoLu.zoneMap != null) {
                charDaoLu.zoneMap.playerMove(charDaoLu.charID, charDaoLu.cx, charDaoLu.cy);
            }
            lastTimeMoveIdle = now;
            timeMoveIdle = Util.gI().nextInt(ConstDaoLu.TIME_IDLE_MOVE_MIN, ConstDaoLu.TIME_IDLE_MOVE_MAX);
        }
    }

    // ======================== VÒNG LẶP CHÍNH ========================

    /**
     * Hàm update chính - gọi mỗi game tick từ Char.update() của chủ.
     * Xử lý toàn bộ logic AI: hồi sinh, di chuyển, chiến đấu, tăng chỉ số.
     * @param delay Thời gian delay giữa các tick (ms)
     */
    public void update(int delay) {
        try {
            // ---- Auto tăng chỉ số nhẹ mỗi tick ----
            increasePoint();

            // ---- Xử lý khi Đạo Lữ đã chết ----
            if (charDaoLu.isDie) {
                long now = System.currentTimeMillis();
                if (now - lastTimeDie > ConstDaoLu.TIME_REVIVE_MS) {
                    // Hồi sinh sau 5 giây
                    charDaoLu.isDie = false;
                    charDaoLu.cHP = charDaoLu.cHPFull;
                    charDaoLu.cMP = charDaoLu.cMPFull;
                    if (charDaoLu.zoneMap != null) {
                        charDaoLu.zoneMap.playerLoadHP(charDaoLu, -1);
                    }
                    justRevived = true;
                }
                return; // Đang chết, không làm gì khác
            }

            // ---- Chat thông báo vừa hồi sinh (1 lần) ----
            if (justRevived && charDaoLu.zoneMap == master.zoneMap) {
                chatFromDaoLu(ConstDaoLu.CHAT_REVIVE);
                justRevived = false;
            }

            // ---- Kiểm tra cùng map với chủ ----
            if (charDaoLu.zoneMap == null || charDaoLu.zoneMap != master.zoneMap) {
                if (status != ConstDaoLu.STATUS_GOHOME) {
                    joinMapMaster();
                }
            }

            // ---- Không hành động nếu chủ chết hoặc mình đang bị hiệu ứng ----
            if (master.isDie || charDaoLu.isDie) {
                return;
            }

            // ---- Di chuyển idle khi rảnh ----
            moveIdle();

            // ---- Xử lý theo trạng thái ----
            switch (status) {
                case ConstDaoLu.STATUS_FOLLOW:
                    // Đi theo chủ trong bán kính 30px
                    followMaster(30);
                    break;

                case ConstDaoLu.STATUS_ATTACK:
                    // Tu luyện: tự tìm quái và đánh
                    updateAttackMode();
                    break;

                case ConstDaoLu.STATUS_GOHOME:
                    // Ở nhà: đi qua lại, nói chuyện
                    updateAtHome();
                    break;
            }
        } catch (Exception e) {
            // Log lỗi nhưng không crash server
            // Util.gI().log("DaoLu update error: " + e.getMessage());
        }
    }

    /**
     * Xử lý logic khi ở trạng thái ATTACK (tự đánh quái).
     * - Tìm mob gần nhất trong bán kính RANGE_FIND_MOB
     * - Dùng skill cận (<=100px) hoặc skill xa (>100px)
     * - 10% cơ hội trash talk khi đánh
     */
    private void updateAttackMode() {
        mobAttack = findNearestMob();
        if (mobAttack != null) {
            // Chat trash talk tối đa 1 lần mỗi 5 giây
            long now = System.currentTimeMillis();
            if (now - lastTimeChatAttack >= CHAT_ATTACK_COOLDOWN && Util.gI().nextInt(100) < 10) {
                lastTimeChatAttack = now;
                String chat = ConstDaoLu.CHAT_ATTACK[Util.gI().nextInt(ConstDaoLu.CHAT_ATTACK.length)];
                if (charDaoLu.zoneMap != null) {
                    charDaoLu.zoneMap.chat(charDaoLu, chat);
                }
            }

            // Tính khoảng cách đến mob
            int disToMob = getDistance(charDaoLu, mobAttack);

            if (disToMob <= ConstDaoLu.RANGE_MELEE) {
                // === ĐÁNH CẬN (skill 1) ===
                useSkillOnMob(0); // skill index 0 = đấm/cận chiến
            } else {
                // === ĐÁNH XA (skill 2) ===
                if (charDaoLu.skills.size() > 1 && charDaoLu.skills.get(1).skillId != -1) {
                    useSkillOnMob(1); // skill index 1 = chưởng/tầm xa
                } else {
                    // Không có skill xa → di chuyển lại gần + đấm
                    charDaoLu.cx = mobAttack.pointx + Util.gI().nextInt(-20, 20);
                    charDaoLu.cy = mobAttack.pointy;
                    if (charDaoLu.zoneMap != null) {
                        charDaoLu.zoneMap.playerMove(charDaoLu.charID, charDaoLu.cx, charDaoLu.cy);
                    }
                    useSkillOnMob(0);
                }
            }
            idle = false;
        } else {
            // Không có quái → idle
            idle = true;
        }
    }

    /**
     * Xử lý logic khi ở nhà (STATUS_GOHOME).
     * Di chuyển qua lại trong nhà, nói chuyện mỗi 5 giây.
     */
    private void updateAtHome() {
        if (charDaoLu.zoneMap == null) {
            return;
        }
        int mapId = charDaoLu.zoneMap.map.template.mapTemplateId;
        // Chỉ hoạt động ở map nhà (21, 22, 23)
        if (mapId != 21 && mapId != 22 && mapId != 23) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastTimeMoveAtHome <= ConstDaoLu.TIME_MOVE_AT_HOME_MS) {
            return;
        }
        // Di chuyển qua lại theo hướng
        switch (mapId) {
            case 21: // Trái Đất
            case 23: // Xayda
                if (directAtHome == -1) {
                    charDaoLu.cx = 250;
                    directAtHome = 1;
                } else {
                    charDaoLu.cx = 200;
                    directAtHome = -1;
                }
                charDaoLu.cy = 336;
                break;
            case 22: // Namek
                if (directAtHome == -1) {
                    charDaoLu.cx = 500;
                    directAtHome = 1;
                } else {
                    charDaoLu.cx = 452;
                    directAtHome = -1;
                }
                charDaoLu.cy = 336;
                break;
        }
        if (charDaoLu.zoneMap != null) {
            charDaoLu.zoneMap.playerMove(charDaoLu.charID, charDaoLu.cx, charDaoLu.cy);
        }
        // Chat khi ở nhà (chỉ chủ thấy)
        chatFromDaoLu(ConstDaoLu.CHAT_AT_HOME);
        lastTimeMoveAtHome = now;
    }

    // ======================== TU VI & CHIẾN ĐẤU ========================

    /**
     * Tìm mob gần nhất còn sống trong zone.
     * @return Mob gần nhất hoặc null nếu không có
     */
    private Mob findNearestMob() {
        if (charDaoLu.zoneMap == null) {
            return null;
        }
        int minDis = ConstDaoLu.RANGE_FIND_MOB;
        Mob closest = null;
        for (Mob mob : charDaoLu.zoneMap.mobs) {
            // [FIX] Lọc mob đã chết, hp <= 0, hoặc không thể đánh (status 0/1)
            if (mob.isDie || mob.hp <= 0 || mob.status == 0 || mob.status == 1) {
                continue;
            }
            int d = getDistance(charDaoLu, mob);
            if (d <= minDis) {
                minDis = d;
                closest = mob;
            }
        }
        return closest;
    }

    /**
     * Dùng skill lên mob đang target.
     * [FIX] Dùng Mob.AttackMob() thay vì trừ HP trực tiếp để:
     *   - Mob chết đúng (startDie, mobDie, drop item)
     *   - Có cooldown 2 giây giữa các đòn
     *   - Khi mob chết → chance drop đan dược
     *
     * @param skillIndex Index skill trong danh sách (0=cận, 1=xa, ...)
     */
    private void useSkillOnMob(int skillIndex) {
        if (charDaoLu.skills.isEmpty() || skillIndex >= charDaoLu.skills.size()) {
            return;
        }
        if (mobAttack == null || mobAttack.isDie || mobAttack.hp <= 0) {
            return;
        }
        // [FIX] Cooldown giữa các đòn tấn công
        long now = System.currentTimeMillis();
        if (now - lastTimeAttack < ATTACK_COOLDOWN) {
            return;
        }
        lastTimeAttack = now;

        // Di chuyển đến gần mob trước khi đánh (nếu skill cận)
        if (skillIndex == 0) {
            charDaoLu.cx = mobAttack.pointx + Util.gI().nextInt(-20, 20);
            charDaoLu.cy = mobAttack.pointy;
            if (charDaoLu.zoneMap != null) {
                charDaoLu.zoneMap.playerMove(charDaoLu.charID, charDaoLu.cx, charDaoLu.cy);
            }
        }
        // Tính damage dựa trên chỉ số Đạo Lữ
        int dam = Util.gI().nextInt(
                (int) ((long) charDaoLu.cDamFull * 9L / 10L),
                charDaoLu.cDamFull
        );
        if (dam <= 0) {
            dam = 1;
        }

        // [FIX] Gửi hiệu ứng tấn công (animation) cho tất cả player trong map
        // Phải gửi TRƯỚC khi trừ HP để client thấy skill effect trước, rồi mới thấy damage
        try {
            if (charDaoLu.zoneMap != null) {
                int skillId = charDaoLu.skills.get(skillIndex).skillId;
                java.util.ArrayList<Mob> hitMobs = new java.util.ArrayList<>();
                hitMobs.add(mobAttack);
                // Gửi animation cho tất cả player khác trong zone
                charDaoLu.zoneMap.playerAttackNPC(charDaoLu, skillId, hitMobs);
                // Gửi animation cho chính chủ nhân (vì playerAttackNPC skip charDaoLu.charID, 
                // nhưng master có charID khác nên master sẽ nhận được thông qua loop)
            }
        } catch (Exception eAnim) {
            // Bỏ qua nếu gửi animation lỗi - không ảnh hưởng damage
        }

        // [FIX] Dùng Mob.AttackMob() để xử lý đúng (death, drops, mobHP/mobDie)
        // master = chủ nhân → drops/exp tính cho chủ
        int hpTruoc = mobAttack.hp;
        try {
            mobAttack.AttackMob(master, dam, false, 1, -1);
        } catch (Exception e) {
            // Fallback: trừ HP trực tiếp nếu AttackMob lỗi
            if (mobAttack.hp > 0) {
                if (dam > mobAttack.hp) {
                    dam = mobAttack.hp;
                }
                mobAttack.hp -= dam;
                if (charDaoLu.zoneMap != null) {
                    charDaoLu.zoneMap.mobHP(mobAttack.mobId, mobAttack.hp, dam, false, -1);
                }
                if (mobAttack.hp <= 0) {
                    mobAttack.startDie();
                }
            }
        }

        // Cộng Tu Vi (chỉ khi ATTACK mode)
        if (status == ConstDaoLu.STATUS_ATTACK) {
            addPointTuVi();
        }

        // [NEW] Nếu mob vừa chết → chance drop đan dược cho chủ
        if (hpTruoc > 0 && (mobAttack.isDie || mobAttack.hp <= 0)) {
            dropDanDuoc();
        }
    }

    /**
     * Drop ngẫu nhiên đan dược khi Đạo Lữ giết mob (tu luyện).
     * 15% chance mỗi lần giết → nhận 1 đan dược random (2071-2076).
     */
    private void dropDanDuoc() {
        if (master == null || master.session == null) {
            return;
        }
        // 15% chance drop 1 đan dược random (chỉ 2071-2076)
        if (Util.gI().nextInt(100) < 15) {
            int itemId = 2071 + Util.gI().nextInt(6); // 2071, 2072, 2073, 2074, 2075, 2076
            try {
                Item danItem = new Item(itemId, false, 1,
                        ItemOption.getOption(itemId, 0, -1),
                        mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                boolean added = master.addItemBag(0, danItem);
                if (added) {
                    master.addInfo1("|2|Đạo Lữ thu luyện được: " + danItem.template.name);
                }
            } catch (Exception e) {
                // Bỏ qua nếu template không tồn tại
            }
        }
    }

    /**
     * Cộng Tu Vi khi Đạo Lữ tấn công mob (chỉ trong ATTACK mode).
     * Công thức: numToTuVi = capTinh * capCanhGioi + 1
     * Mỗi numToTuVi đòn đánh → +1 Tu Vi
     * Tu Vi càng khó tăng khi cảnh giới + cấp tinh cao
     */
    public void addPointTuVi() {
        numAttackMob++;
        // Công thức số đòn cần để +1 Tu Vi:
        // Level 0-5: capTinh * capCanhGioi + 1 (nhẹ)
        // Level 6+ (Đấu Hoàng trở đi): (capCanhGioi - 5) * 100 + capTinh * capCanhGioi
        //   Đấu Hoàng(6)=100+, Đấu Tông(7)=200+, Đấu Tôn(8)=300+, Đấu Thánh(9)=400+
        if (pointCapCanhGioi >= 6) {
            numToTuVi = (pointCapCanhGioi - 5) * 100 + pointCapTinh * pointCapCanhGioi;
        } else {
            numToTuVi = pointCapTinh * pointCapCanhGioi + 1;
        }
        if (numAttackMob >= numToTuVi) {
            numAttackMob -= numToTuVi;
            this.pointTuVi++;
            if (this.pointTuVi > ConstDaoLu.MAX_TU_VI) {
                this.pointTuVi = ConstDaoLu.MAX_TU_VI;
            }
        }
    }

    /**
     * Auto tăng chỉ số nhẹ mỗi giây (mô phỏng tu luyện).
     * 5% cơ hội tăng 1 điểm tiềm năng, 95% tăng random 1-100 chỉ số.
     */
    private void increasePoint() {
        long now = System.currentTimeMillis();
        if (now - lastTimeIncreasePoint < 1000) {
            return; // Chờ ít nhất 1 giây
        }
        if (Util.gI().nextInt(100) < 5) {
            // 5%: tăng 1 tiềm năng
            charDaoLu.cTiemNang += 1;
        } else {
            // 95%: tăng random chỉ số gốc
            int index = Util.gI().nextInt(0, 3); // 0=HP, 1=MP, 2=Dam
            int point = Util.gI().nextInt(1, 101);
            switch (index) {
                case 0: charDaoLu.cHPGoc += point; break;
                case 1: charDaoLu.cMPGoc += point; break;
                case 2: charDaoLu.cDamGoc += point; break;
            }
        }
        lastTimeIncreasePoint = now;
    }

    // ======================== BUFF CHỈ SỐ CHO CHỦ ========================

    /**
     * Tính tổng % buff HP/KI/SD mà Đạo Lữ cộng cho chủ.
     * Buff chỉ áp dụng khi Đạo Lữ KHÔNG ở nhà.
     *
     * Tổng buff = buff_phẩm + buff_cảnh_giới + buff_cấp_tinh + buff_đấu_đế(nếu có)
     *
     * Ví dụ: Tam Phẩm + Đấu Vương(5) + Ngũ Tinh(4)
     *   = 100000% + 5000% + 500% = 105,500% HP/KI/SD
     *
     * @return Tổng % buff (dùng hiển thị menu, = tổng HP+KI+SD / 3 ước lượng)
     */
    public int getTotalBuffPercent() {
        if (status == ConstDaoLu.STATUS_GOHOME) {
            return 0;
        }
        int[] pct = getBuffPercentDetail();
        return (pct[0] + pct[1] + pct[2]) / 3;
    }

    /**
     * Tính chi tiết % buff riêng cho HP, KI, SD.
     * Buff = Phẩm + Cộng dồn cấp bậc (tinh không tăng chỉ số).
     *
     * @return int[3] = {%HP, %KI, %SD}
     */
    public int[] getBuffPercentDetail() {
        int pctHP = 0, pctKI = 0, pctSD = 0;
        // 1. Buff theo phẩm (cả 3 chỉ số)
        if (typeDaoLu >= 1 && typeDaoLu <= 3) {
            pctHP += ConstDaoLu.BUFF_PHAM_HP[typeDaoLu];
            pctKI += ConstDaoLu.BUFF_PHAM_KI[typeDaoLu];
            pctSD += ConstDaoLu.BUFF_PHAM_SD[typeDaoLu];
        }
        // 2. Buff cộng dồn theo cấp bậc
        pctHP += ConstDaoLu.getTotalBuffHP(pointCapCanhGioi);
        pctKI += ConstDaoLu.getTotalBuffKI(pointCapCanhGioi);
        pctSD += ConstDaoLu.getTotalBuffSD(pointCapCanhGioi);
        return new int[]{pctHP, pctKI, pctSD};
    }

    /**
     * Áp dụng buff Đạo Lữ lên chỉ số chủ.
     * Gọi trong Char.updateAll() sau khi tính chỉ số cơ bản.
     * Mỗi chỉ số HP/KI/SD có % riêng biệt.
     *
     * @param hpFull  HP chủ trước buff
     * @param mpFull  MP chủ trước buff
     * @param damFull Dam chủ trước buff
     * @return int[3] = {hpBonus, mpBonus, damBonus}
     */
    public int[] calcBuff(int hpFull, int mpFull, int damFull) {
        if (status == ConstDaoLu.STATUS_GOHOME) {
            return new int[]{0, 0, 0};
        }
        int[] pct = getBuffPercentDetail();
        int hpBonus = (int) ((long) hpFull * pct[0] / 100L);
        int mpBonus = (int) ((long) mpFull * pct[1] / 100L);
        int damBonus = (int) ((long) damFull * pct[2] / 100L);
        return new int[]{hpBonus, mpBonus, damBonus};
    }

    // ======================== NGOẠI HÌNH ========================

    /**
     * Lấy sprite head của Đạo Lữ.
     * Ưu tiên: Type head → Trang bị slot 5 → Style theo power
     */
    public short getHead() {
        // Nếu có type đặc biệt và chưa biến hình → dùng head type
        if (!isTransform && typeDaoLu >= 1 && typeDaoLu <= 3) {
            return ConstDaoLu.HEAD_BY_TYPE[typeDaoLu];
        }
        // Nếu mặc đồ slot 5 (ngụy trang)
        if (isMacDo && charDaoLu.arrItemBody != null && charDaoLu.arrItemBody.length > 5
                && charDaoLu.arrItemBody[5] != null && charDaoLu.arrItemBody[5].template.part != -1) {
            return charDaoLu.arrItemBody[5].template.part;
        }
        // Style theo power
        if (charDaoLu.cPower < 1500000) {
            return ConstDaoLu.DAO_LU_STYLE[charDaoLu.cgender][0];
        } else {
            return ConstDaoLu.DAO_LU_STYLE[3][charDaoLu.cgender];
        }
    }

    /**
     * Lấy sprite body của Đạo Lữ.
     */
    public short getBody() {
        if (!isTransform && typeDaoLu >= 1 && typeDaoLu <= 3) {
            return ConstDaoLu.BODY_BY_TYPE[typeDaoLu];
        }
        if (isMacDo && charDaoLu.arrItemBody != null && charDaoLu.arrItemBody.length > 0
                && charDaoLu.arrItemBody[0] != null) {
            return charDaoLu.arrItemBody[0].template.part;
        }
        if (charDaoLu.cPower < 1500000) {
            return ConstDaoLu.DAO_LU_STYLE[charDaoLu.cgender][1];
        } else {
            return (short) (charDaoLu.cgender == 1 ? 59 : 57); // Namec/other
        }
    }

    /**
     * Lấy sprite leg của Đạo Lữ.
     */
    public short getLeg() {
        if (!isTransform && typeDaoLu >= 1 && typeDaoLu <= 3) {
            return ConstDaoLu.LEG_BY_TYPE[typeDaoLu];
        }
        if (isMacDo && charDaoLu.arrItemBody != null && charDaoLu.arrItemBody.length > 1
                && charDaoLu.arrItemBody[1] != null) {
            return charDaoLu.arrItemBody[1].template.part;
        }
        if (charDaoLu.cPower < 1500000) {
            return ConstDaoLu.DAO_LU_STYLE[charDaoLu.cgender][2];
        } else {
            return (short) (charDaoLu.cgender == 1 ? 60 : 58);
        }
    }

    /**
     * Toggle biến hình (hiển thị trang bị thay vì sprite mặc định).
     */
    public void transform() {
        this.isTransform = !this.isTransform;
        // Cập nhật ngoại hình trên map
        charDaoLu.head = getHead();
        charDaoLu.body = getBody();
        charDaoLu.leg = getLeg();
        // Gửi lại thông tin char cho tất cả player trong zone
        if (charDaoLu.zoneMap != null) {
            charDaoLu.zoneMap.playerLoadAll(charDaoLu);
        }
        String text = "Đạo Lữ " + ConstDaoLu.getTenPham(typeDaoLu) + " xuất trận!";
        if (charDaoLu.zoneMap != null) {
            charDaoLu.zoneMap.chat(charDaoLu, text);
        }
    }

    // ======================== THĂNG CẤP ========================

    /**
     * Thăng tinh (tăng cấp tinh trong cùng cảnh giới).
     * Yêu cầu: Tu Vi >= 600
     * Tiêu hao: TOÀN BỘ Tu Vi
     * Thành công: +1 cấp tinh
     * Thất bại: Rớt hết Tu Vi (không chết)
     *
     * Tỷ lệ:
     *   Level 0-6: 100%, 95%, 90%, 85%... (-5% mỗi tinh)
     *   Level 7-8: 100%, 94%, 88%... (-6% mỗi tinh)
     *   Level 9:   100%, 93%, 86%... (-7% mỗi tinh)
     *
     * @return true nếu thăng thành công, false nếu thất bại hoặc không đủ điều kiện
     */
    public boolean thangTinh() {
        int maxTinh = ConstDaoLu.getMaxTinh(pointCapCanhGioi);
        if (pointCapTinh >= maxTinh) {
            return false; // Đã max tinh
        }
        if (pointTuVi < ConstDaoLu.MIN_TU_VI_THANG_TINH) {
            return false; // Chưa đủ Tu Vi
        }
        // Tính tỷ lệ thành công (dựa trên cảnh giới + tinh hiện tại)
        int tiLe = ConstDaoLu.getTiLeThangTinh(pointCapCanhGioi, pointCapTinh);
        // Tiêu hao toàn bộ Tu Vi bất kể thành/bại
        pointTuVi = 0;
        // Roll random
        if (Util.gI().nextInt(100) < tiLe) {
            // THÀNH CÔNG → +1 tinh
            pointCapTinh++;
            timeThangCap = System.currentTimeMillis();
            return true;
        } else {
            // THẤT BẠI → chỉ rớt Tu Vi (đã reset ở trên), không chết
            return false;
        }
    }

    /**
     * Thăng cảnh giới (tăng cấp bậc: Đấu Khí → Đấu Giả → ... → Đấu Thánh).
     * Yêu cầu: Tu Vi = 1000 (max) + Cấp tinh = max
     * Tiêu hao: TOÀN BỘ Tu Vi
     * Thành công: +1 cảnh giới, reset cấp tinh về 0
     * Thất bại: Giảm tinh (1/2/3 tùy level) + Đạo Lữ chết
     *
     * Tỷ lệ: Đấu Khí=90%, Đấu Giả=80%, Đấu Sư=75%, Đại Đấu Sư=70%,
     *         Đấu Linh=65%, Đấu Vương=60%, Đấu Hoàng=55%,
     *         Đấu Tông=50%, Đấu Tôn=40%
     * Thất bại: Level 0-5 rớt 1 tinh, Level 6 rớt 2 tinh, Level 7-8 rớt 3 tinh
     *
     * @return true nếu thăng thành công
     */
    public boolean thangCanhGioi() {
        // Kiểm tra đã max cấp tinh chưa
        int maxTinh = ConstDaoLu.getMaxTinh(pointCapCanhGioi);
        if (pointCapTinh < maxTinh) {
            return false; // Chưa max tinh
        }
        if (pointCapCanhGioi >= ConstDaoLu.MAX_CAP_BAC - 1) {
            return false; // Đấu Thánh lên Đấu Đế phải qua thangDauDe()
        }
        if (pointTuVi < ConstDaoLu.MAX_TU_VI) {
            return false; // Chưa đủ Tu Vi
        }
        // Tiêu hao Tu Vi
        pointTuVi = 0;
        // Tính tỷ lệ
        int tiLe = ConstDaoLu.getTiLeThangCanhGioi(pointCapCanhGioi);
        if (Util.gI().nextInt(100) < tiLe) {
            // THÀNH CÔNG
            pointCapCanhGioi++;
            pointCapTinh = 0; // Reset tinh về 0
            timeThangCap = System.currentTimeMillis();
            return true;
        } else {
            // THẤT BẠI → giảm tinh theo cấp bậc (không chết)
            int soTinhMat = ConstDaoLu.getSoTinhMatKhiThatBai(pointCapCanhGioi);
            pointCapTinh -= soTinhMat;
            if (pointCapTinh < 0) {
                pointCapTinh = 0;
            }
            return false;
        }
    }

    /**
     * Thăng Đấu Đế (cấp cuối cùng, từ Đấu Thánh lên).
     * Yêu cầu: pointCapCanhGioi == 9 (Đấu Thánh) + max tinh + Tu Vi max
     *           + 10 loại đan × 1000 viên + 4 mảnh Đà Xá Cổ Đế
     * Thành công: 100% (nếu đủ nguyên liệu)
     * Hiệu ứng: Thông báo toàn server, trời tối, biến hình
     *
     * Lưu ý: Kiểm tra item phải thực hiện ở MenuBoard.java trước khi gọi hàm này.
     *
     * @return true luôn (100% thành công nếu đủ điều kiện)
     */
    public boolean thangDauDe() {
        pointCapCanhGioi = ConstDaoLu.MAX_CAP_BAC; // = 10
        pointCapTinh = 0;
        timeThangCap = System.currentTimeMillis();
        isThangDauDe = true;
        pointTuVi = 0;
        return true;
    }

    // ======================== HIỆU ỨNG ĐẤU ĐẾ ========================

    /**
     * Phát hiệu ứng Đấu Đế xuất hiện (trời tối, thông báo toàn server).
     * Chạy trên thread riêng để không block game loop.
     */
    public void effDauDeXuatHien() {
        new Thread(() -> {
            try {
                // Lấy thông tin map hiện tại
                int mapId = 0, bgId = 0, zoneId = 0;
                String mapName = "???";
                if (master.zoneMap != null && master.zoneMap.map != null) {
                    mapId = master.zoneMap.mapTemplate.mapTemplateId;
                    bgId = master.zoneMap.mapTemplate.bgID;
                    zoneId = master.zoneMap.zoneID;
                    mapName = master.zoneMap.map.template.mapName;
                }

                // Bước 1: Thông báo thế giới
                String tbao = "Đấu Đế xuất hiện!!! Đạo lữ của " + master.cName
                        + " đã đột phá đấu đế tại " + mapName + "!";
                for (Session_ME conn : Server.gI().connList) {
                    if (conn != null && conn.myCharz() != null) {
                        conn.service.chatTHEGIOI("", tbao, null, 0);
                    }
                }

                // Bước 2: TRỜI TỐI - gửi callDragon cho tất cả player (giống gọi rồng thần)
                // Hiệu ứng trời tối + ánh sáng rồng thần
                for (Session_ME conn : Server.gI().connList) {
                    if (conn != null && conn.myCharz() != null) {
                        conn.service.callDragon(mapId, bgId, zoneId, master.charID, "", master.cx, master.cy, 0);
                    }
                }

                Thread.sleep(2000);

                // Bước 3: Set aura Đấu Đế cho charDaoLu
                if (charDaoLu != null) {
                    charDaoLu.idAuraEff = 22;
                    if (charDaoLu.zoneMap != null) {
                        synchronized (charDaoLu.zoneMap.players) {
                            for (int i = 0; i < charDaoLu.zoneMap.players.size(); i++) {
                                Char pl = charDaoLu.zoneMap.players.get(i);
                                if (pl != null && pl.session != null) {
                                    pl.session.service.getAuraEff(charDaoLu.charID, charDaoLu.idAuraEff);
                                }
                            }
                        }
                        charDaoLu.zoneMap.playerLoadAll(charDaoLu);
                    }
                }

                Thread.sleep(3000);

                // Bước 4: Thông báo chi tiết cho chủ
                if (master.session != null) {
                    master.session.service.chatTHEGIOI("",
                            "Trời đất rung chuyển, đạo lữ đã đột phá đến "
                            + ConstDaoLu.getFullCapBac(pointCapCanhGioi, pointCapTinh),
                            null, 0);
                }

                // Bước 5: Player trong map chat chúc mừng
                if (charDaoLu != null && charDaoLu.zoneMap != null) {
                    String[] chatStr = {
                        "Đấu đế, là đấu đế!!",
                        "Mau, mau quỳ xuống!",
                        "Cường giả vô địch!"
                    };
                    synchronized (charDaoLu.zoneMap.players) {
                        for (int i = 0; i < charDaoLu.zoneMap.players.size(); i++) {
                            Char pl = charDaoLu.zoneMap.players.get(i);
                            if (pl != null && pl.charID != master.charID && pl.charID != charDaoLu.charID && pl.session != null) {
                                String s = chatStr[Util.gI().nextInt(chatStr.length)];
                                charDaoLu.zoneMap.chat(pl, s);
                                break;
                            }
                        }
                    }
                }

                Thread.sleep(3000);

                // Bước 6: TRỜI SÁNG LẠI - gửi hideDragon cho tất cả player
                for (Session_ME conn : Server.gI().connList) {
                    if (conn != null && conn.myCharz() != null) {
                        conn.service.hideDragon();
                    }
                }

            } catch (InterruptedException e) {
                // Nếu bị interrupt, đảm bảo trời sáng lại
                try {
                    for (Session_ME conn : Server.gI().connList) {
                        if (conn != null && conn.myCharz() != null) {
                            conn.service.hideDragon();
                        }
                    }
                } catch (Exception ex) {}
            } catch (Exception e) {
                // Đảm bảo trời sáng lại nếu lỗi
                try {
                    for (Session_ME conn : Server.gI().connList) {
                        if (conn != null && conn.myCharz() != null) {
                            conn.service.hideDragon();
                        }
                    }
                } catch (Exception ex) {}
            }
        }, "DaoLu-DauDe-" + master.charID).start();
    }

    // ======================== TIỆN ÍCH ========================

    /**
     * Đạo Lữ chat (chỉ chủ thấy).
     * @param text Nội dung chat
     */
    private void chatFromDaoLu(String text) {
        if (master.session != null) {
            master.session.service.chatTHEGIOI("", "[Đạo Lữ] " + text, null, 0);
        }
    }

    /**
     * Tính khoảng cách giữa Char và Mob.
     */
    private int getDistance(Char c, Mob m) {
        return (int) Math.sqrt(Math.pow(c.cx - m.pointx, 2) + Math.pow(c.cy - m.pointy, 2));
    }

    /**
     * Lấy text mô tả trạng thái hiện tại (dùng cho menu info).
     */
    public String getTextStatusInfo() {
        switch (status) {
            case ConstDaoLu.STATUS_GOHOME:
                return "Về Nhà\nVề Nhà, Rửa Chén, Nấu Cơm";
            case ConstDaoLu.STATUS_ATTACK:
                return "Tu Luyện\nTấn Công Quái Luyện Sức Mạnh và Tu Vi\n"
                        + "Đòn tấn công để tăng Tu Vi: " + numAttackMob + "/" + numToTuVi;
            case ConstDaoLu.STATUS_FOLLOW:
                return "Đi Theo\nTấn Công Mục Tiêu Cùng Chủ";
            default:
                return "";
        }
    }

    /**
     * Khi chủ dùng skill → Đạo Lữ dùng cùng skill (chỉ trong FOLLOW mode).
     * Gọi từ Char.java khi chủ tấn công.
     */
    public void masterUseSkill() {
        if (status != ConstDaoLu.STATUS_FOLLOW) {
            return;
        }
        if (charDaoLu.isDie || charDaoLu.zoneMap != master.zoneMap) {
            return;
        }
        // Nếu đang ở nhà → tự chuyển FOLLOW
        if (status == ConstDaoLu.STATUS_GOHOME) {
            changeStatus(ConstDaoLu.STATUS_FOLLOW);
        }
        // Dùng skill cận chiến lên mob gần nhất (đơn giản hóa)
        mobAttack = findNearestMob();
        if (mobAttack != null) {
            useSkillOnMob(0);
        }
    }

    /**
     * Giải phóng tài nguyên khi player disconnect.
     */
    public void dispose() {
        if (charDaoLu.zoneMap != null) {
            charDaoLu.zoneMap.exit(charDaoLu, 0);
        }
        this.master = null;
        this.mobAttack = null;
    }

    // ======================== AURA ========================

    /**
     * Lấy ID aura cho Đạo Lữ.
     * Đấu Đế có aura đặc biệt (22), còn lại dùng aura của chủ.
     */
    public byte getAura() {
        if (pointCapCanhGioi == ConstDaoLu.MAX_CAP_BAC) {
            return 22; // Aura Đấu Đế
        }
        return 0; // Mặc định
    }
}
