package dragon.t;

import dragon.object.Char;
import dragon.object.Item;
import dragon.object.ItemOption;
import dragon.server.MySQL;
import dragon.server.mResources;
import dragon.u.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Battle Pass / Hành trình mùa. Mùa = 1 tháng, reset ngày 1.
 * Free max 20, VIP (Pro/Master) max 50. Mỗi cấp 10 điểm.
 */
public class SeasonPass {

    public static final int PASS_FREE = 0;
    public static final int PASS_PRO = 1;
    public static final int PASS_MASTER = 2;

    public static final int EXP_PER_LEVEL = 10;
    public static final int FREE_MAX_LEVEL = 20;
    public static final int FREE_MAX_EXP = 200;
    public static final int VIP_MAX_LEVEL = 50;
    public static final int VIP_MAX_EXP = 500;

    public static final int PRICE_PRO_VND = 150000;
    public static final int PRICE_MASTER_VND = 200000;

    /** Cap nhiệm vụ: Fide Free 10, VIP 20; Tiểu đội trưởng 30; Super Bojack 30; King Kong Free 25/VIP 52; Online Free 4000/VIP 5000 phút; Quái Free 20đ/VIP 50đ 1M; PvP Free 50/VIP 200 kill */
    public static final int FIDE_CAP_FREE = 10;
    public static final int FIDE_CAP_VIP = 20;
    public static final int TIEU_DOI_TRUONG_CAP = 30;
    public static final int SUPER_BOJACK_CAP = 30;
    public static final int KING_KONG_CAP_FREE = 25;
    public static final int KING_KONG_CAP_VIP = 52;
    public static final int ONLINE_CAP_FREE = 4000;
    public static final int ONLINE_CAP_VIP = 5000;
    public static final int MONSTER_MILESTONE_FREE = 200000;  // Free: 20đ khi đạt 200k (1 lần/mùa)
    public static final int MONSTER_MILESTONE_VIP = 200000;
    public static final int PVP_CAP_FREE = 50;
    public static final int PVP_CAP_VIP = 200;

    /** Boss template id (chartemplate) – Fide Đại Ca 3 = 65, Tiểu đội trưởng = 114, Super Bojack 52, King Kong 73 */
    public static final int BOSS_FIDE_DAI_CA_3 = 65;
    public static final int BOSS_TIEU_DOI_TRUONG = 114;
    public static final int BOSS_SUPER_BOJACK = 52;
    public static final int BOSS_KING_KONG = 73;

    /** Mua điểm: 100 thỏi vàng (id 457) = 10 điểm */
    public static final int BUY_POINTS_GOLD_ITEM_ID = 457;
    public static final int BUY_POINTS_GOLD_COST = 100;
    public static final int BUY_POINTS_REWARD = 10;

    private static SeasonPass instance;

    public static SeasonPass gI() {
        if (instance == null) {
            instance = new SeasonPass();
        }
        return instance;
    }

    /** Bảng quà theo cấp (level 1–50): [itemId, quantity]. Index 0 không dùng. */
    private static final int[][] REWARDS = new int[51][2];
    static {
        int[][] r = new int[][]{
            {0, 0}, {1901, 1}, {1902, 1}, {1903, 2}, {220, 10}, {221, 10}, {222, 10}, {223, 10}, {224, 10},
            {380, 99}, {457, 50}, {381, 20}, {382, 20}, {383, 20}, {384, 20}, {381, 30}, {382, 30}, {383, 30}, {384, 30},
            {380, 150}, {457, 100},
            {1903, 3}, {1903, 5}, {220, 20}, {221, 20}, {222, 20}, {223, 20}, {224, 20}, {380, 200}, {457, 150}, {457, 100},
            {1903, 10}, {381, 50}, {382, 50}, {383, 50}, {384, 50}, {380, 300}, {457, 300}, {457, 300}, {457, 300}, {457, 500},
            {1903, 15}, {380, 500}, {224, 100}, {381, 100}, {223, 100}, {457, 300}, {457, 1000}, {457, 300}, {380, 2000}, {457, 300}
        };
        for (int i = 0; i < r.length && i <= 50; i++) {
            REWARDS[i] = r[i];
        }
    }

    public static String getCurrentSeasonId() {
        Calendar c = Calendar.getInstance();
        return String.format("%04d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    public int[] getRewardForLevel(int level) {
        if (level < 1 || level > 50) return null;
        return REWARDS[level];
    }

    /** Trả về level, exp, passType, lastClaimLevel; task counts. Nếu chưa có bản ghi thì tạo mới (0,0,FREE,0). */
    public SeasonPassData getOrCreate(int playerId, String seasonId) {
        SeasonPassData data = new SeasonPassData();
        data.seasonId = seasonId;
        data.playerId = playerId;
        try {
            MySQL mySQL = MySQL.createData2();
            try {
                ResultSet rs = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SEASON_PASS, playerId, seasonId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                if (rs.first()) {
                    data.level = rs.getInt(1);
                    data.exp = rs.getInt(2);
                    data.passType = rs.getInt(3);
                    data.lastClaimLevel = rs.getInt(4);
                }
                rs.close();
                rs = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SEASON_PASS_TASK, playerId, seasonId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                if (rs.first()) {
                    data.fideKillCount = rs.getInt(1);
                    data.tieuDoiTruongKillCount = rs.getInt(2);
                    data.superBojackKillCount = rs.getInt(3);
                    data.kingKongKillCount = rs.getInt(4);
                    data.onlineMinutes = rs.getInt(5);
                    data.monsterKillCount = rs.getInt(6);
                    data.monsterMilestoneGiven = rs.getInt(7);
                    data.pvpKillCount = rs.getInt(8);
                }
                rs.close();
                save(data);
            } finally {
                mySQL.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void save(SeasonPassData data) {
        try {
            MySQL mySQL = MySQL.createData2();
            try {
                mySQL.getConnection().prepareStatement(String.format(mResources.UPSERT_SEASON_PASS,
                    data.playerId, data.seasonId, data.level, data.exp, data.passType, data.lastClaimLevel)).executeUpdate();
                mySQL.getConnection().prepareStatement(String.format(mResources.UPSERT_SEASON_PASS_TASK,
                    data.playerId, data.seasonId, data.fideKillCount, data.tieuDoiTruongKillCount, data.superBojackKillCount,
                    data.kingKongKillCount, data.onlineMinutes, data.monsterKillCount, data.monsterMilestoneGiven, data.pvpKillCount)).executeUpdate();
            } finally {
                mySQL.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxLevel(int passType) {
        return (passType == PASS_FREE) ? FREE_MAX_LEVEL : VIP_MAX_LEVEL;
    }

    public int getMaxExp(int passType) {
        return (passType == PASS_FREE) ? FREE_MAX_EXP : VIP_MAX_EXP;
    }

    /** Cộng exp và xử lý lên cấp (trừ exp mỗi 10), trả về số exp đã cộng (có thể 0 nếu cap). */
    public int addExp(Char player, SeasonPassData data, int expToAdd) {
        if (player == null || data == null || expToAdd <= 0) return 0;
        int maxLevel = getMaxLevel(data.passType);
        int maxExp = getMaxExp(data.passType);
        int totalNow = data.level * EXP_PER_LEVEL + data.exp;
        if (totalNow >= maxExp) return 0;
        int cap = maxExp - totalNow;
        int add = Math.min(expToAdd, cap);
        data.exp += add;
        while (data.exp >= EXP_PER_LEVEL && data.level < maxLevel) {
            data.exp -= EXP_PER_LEVEL;
            data.level++;
        }
        save(data);
        return add;
    }

    /** Gửi quà từ cấp fromLevel đến toLevel (bao gồm) cho nhân vật; item khóa giao dịch. */
    public boolean giveRewards(Char player, SeasonPassData data, int fromLevel, int toLevel) {
        if (player == null || data == null) return false;
        boolean isVip = (data.passType == PASS_PRO || data.passType == PASS_MASTER);
        for (int lv = fromLevel; lv <= toLevel && lv <= 50; lv++) {
            if (lv > 20 && !isVip) continue;
            int[] r = getRewardForLevel(lv);
            if (r == null || r[0] == 0) continue;
            Item item = new Item(r[0], false, r[1], ItemOption.getOption(r[0], 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            item.options.add(new ItemOption(30, 1));
            if (!player.addItemBag(0, item)) {
                return false;
            }
        }
        return true;
    }

    /** Nhận quà từ lastClaimLevel+1 đến level hiện tại; cập nhật lastClaimLevel. */
    public boolean claimRewards(Char player, SeasonPassData data) {
        if (player == null || data == null) return false;
        int from = data.lastClaimLevel + 1;
        int to = data.level;
        if (from > to) return true;
        if (!giveRewards(player, data, from, to)) return false;
        data.lastClaimLevel = to;
        save(data);
        return true;
    }

    /** Mua Pro: kiểm tra VND >= 150000, trừ tiền, set passType = PRO. */
    public String buyPro(Char player) {
        if (player == null) return "Lỗi.";
        long vnd = Money.gI().getMoney(player);
        if (vnd < PRICE_PRO_VND) return "Số dư VND không đủ. Cần 150.000 VND.";
        String seasonId = getCurrentSeasonId();
        SeasonPassData data = getOrCreate(player.playerId, seasonId);
        if (data.passType != PASS_FREE) return "Bạn đã kích hoạt gói trong mùa này rồi.";
        Money.gI().updateMoeny(player, -PRICE_PRO_VND);
        data.passType = PASS_PRO;
        save(data);
        return "|2|Kích hoạt Battle Pass Pro thành công!";
    }

    /** Mua điểm bằng thỏi vàng: 100 thỏi vàng (id 457) = 10 điểm. Trả về thông báo. */
    public String buyPointsWithGold(Char player) {
        if (player == null) return "Lỗi.";
        if (player.getQuantityITem(BUY_POINTS_GOLD_ITEM_ID) < BUY_POINTS_GOLD_COST) {
            return "Cần " + BUY_POINTS_GOLD_COST + " thỏi vàng để đổi " + BUY_POINTS_REWARD + " điểm. Bạn đang có: " + player.getQuantityITem(BUY_POINTS_GOLD_ITEM_ID) + " thỏi vàng.";
        }
        String seasonId = getCurrentSeasonId();
        SeasonPassData d = getOrCreate(player.playerId, seasonId);
        int maxExp = getMaxExp(d.passType);
        int totalNow = d.level * EXP_PER_LEVEL + d.exp;
        if (totalNow >= maxExp) return "Đã đạt tối đa điểm mùa này (cấp " + getMaxLevel(d.passType) + ").";
        player.useItemBagById(BUY_POINTS_GOLD_ITEM_ID, BUY_POINTS_GOLD_COST);
        addExp(player, d, BUY_POINTS_REWARD);
        return "|2|Đổi " + BUY_POINTS_GOLD_COST + " thỏi vàng thành " + BUY_POINTS_REWARD + " điểm Hành trình mùa thành công!";
    }

    /** Mua Master: kiểm tra VND >= 200000, trừ tiền, set passType = MASTER; nếu level < 20 thì set level = 20 và gửi quà cấp 1–20. */
    public String buyMaster(Char player) {
        if (player == null) return "Lỗi.";
        long vnd = Money.gI().getMoney(player);
        if (vnd < PRICE_MASTER_VND) return "Số dư VND không đủ. Cần 200.000 VND.";
        String seasonId = getCurrentSeasonId();
        SeasonPassData data = getOrCreate(player.playerId, seasonId);
        if (data.passType != PASS_FREE) return "Bạn đã kích hoạt gói trong mùa này rồi.";
        Money.gI().updateMoeny(player, -PRICE_MASTER_VND);
        data.passType = PASS_MASTER;
        if (data.level < 20) {
            data.level = 20;
            data.exp = 0;
            data.lastClaimLevel = 0;
            giveRewards(player, data, 1, 20);
            data.lastClaimLevel = 20;
        }
        save(data);
        return "|2|Kích hoạt Battle Pass Master thành công! Đã nhận quà cấp 1–20.";
    }

    /** Cộng điểm khi kill boss Fide Đại Ca 3: +2/còn, Free cap 10, VIP cap 20. */
    public void onKillFide(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        int cap = (d.passType == PASS_FREE) ? FIDE_CAP_FREE : FIDE_CAP_VIP;
        if (d.fideKillCount >= cap) return;
        d.fideKillCount++;
        int added = addExp(player, d, 2);
        if (added > 0) save(d);
    }

    /** Kill Tiểu đội trưởng: +1, cap 30. */
    public void onKillTieuDoiTruong(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        if (d.tieuDoiTruongKillCount >= TIEU_DOI_TRUONG_CAP) return;
        d.tieuDoiTruongKillCount++;
        addExp(player, d, 1);
    }

    /** Kill Super Bojack: +1, cap 30. */
    public void onKillSuperBojack(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        if (d.superBojackKillCount >= SUPER_BOJACK_CAP) return;
        d.superBojackKillCount++;
        addExp(player, d, 1);
    }

    /** Kill King Kong: Free +2 (cap 25), VIP +5 (cap 52). */
    public void onKillKingKong(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        boolean isVip = (d.passType == PASS_PRO || d.passType == PASS_MASTER);
        int cap = isVip ? KING_KONG_CAP_VIP : KING_KONG_CAP_FREE;
        int pt = isVip ? 5 : 2;
        if (d.kingKongKillCount >= cap) return;
        d.kingKongKillCount++;
        addExp(player, d, pt);
    }

    /** Online: cộng từng phút, mỗi 100 phút +1 điểm; cap Free 4000 phút (40đ), VIP 5000 (50đ). */
    public void addOnlineMinutes(Char player, int minutes) {
        if (player == null || minutes <= 0) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        int capMin = (d.passType == PASS_FREE) ? ONLINE_CAP_FREE : ONLINE_CAP_VIP;
        if (d.onlineMinutes >= capMin) return;
        int addMin = Math.min(minutes, capMin - d.onlineMinutes);
        int oldHundreds = d.onlineMinutes / 100;
        d.onlineMinutes += addMin;
        int newHundreds = d.onlineMinutes / 100;
        int points = newHundreds - oldHundreds;
        if (points > 0) addExp(player, d, points);
        save(d);
    }

    /** Mỗi lần player kill 1 quái: tăng đếm; Free 200k → +20đ (1 lần), VIP 200k → +50đ (1 lần). */
    public void addMonsterKill(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        d.monsterKillCount++;
        if (d.passType == PASS_FREE && d.monsterMilestoneGiven < 1 && d.monsterKillCount >= MONSTER_MILESTONE_FREE) {
            d.monsterMilestoneGiven = 1;
            addExp(player, d, 20);
        } else if ((d.passType == PASS_PRO || d.passType == PASS_MASTER) && d.monsterMilestoneGiven < 2 && d.monsterKillCount >= MONSTER_MILESTONE_VIP) {
            d.monsterMilestoneGiven = 2;
            addExp(player, d, 50);
        }
        save(d);
    }

    /** PvP: +1 điểm mỗi 5 kill; cap Free 50 kill (10đ), VIP 200 kill (40đ). */
    public void onPvpKill(Char player) {
        if (player == null) return;
        SeasonPassData d = getOrCreate(player.playerId, getCurrentSeasonId());
        int cap = (d.passType == PASS_FREE) ? PVP_CAP_FREE : PVP_CAP_VIP;
        if (d.pvpKillCount >= cap) return;
        d.pvpKillCount++;
        if (d.pvpKillCount % 5 == 0) addExp(player, d, 1);
        save(d);
    }

    public static class SeasonPassData {
        public int playerId;
        public String seasonId;
        public int level;
        public int exp;
        public int passType;
        public int lastClaimLevel;
        public int fideKillCount;
        public int tieuDoiTruongKillCount;
        public int superBojackKillCount;
        public int kingKongKillCount;
        public int onlineMinutes;
        public int monsterKillCount;
        public int monsterMilestoneGiven;
        public int pvpKillCount;
    }
}
