package dragon.t;

/**
 * ConstDaoLu - Hằng số cho hệ thống Đạo Lữ (bạn đồng hành tu tiên)
 * Port từ gameplayopen ConstPet.java sang cbmeil architecture
 * 
 * Đạo Lữ là một companion NPC có hệ thống cảnh giới tu tiên:
 * - 11 cấp bậc: Đấu Khí(0) → Đấu Đế(10)
 * - Mỗi cấp bậc có nhiều cấp tinh (Nhất Tinh → Cửu Tinh)
 * - Tu Vi tích lũy bằng cách đánh quái
 * - 3 phẩm: Nhất Phẩm, Nhị Phẩm, Tam Phẩm (phẩm càng cao buff càng mạnh)
 */
public final class ConstDaoLu {

    // ======================== Không cho tạo instance ========================
    private ConstDaoLu() {
    }

    // ======================== Cấp bậc cảnh giới tối đa ========================
    /** Cấp bậc cảnh giới cao nhất (Đấu Đế = 10) */
    public static final int MAX_CAP_BAC = 10;

    /** Tu Vi tối đa có thể tích lũy */
    public static final int MAX_TU_VI = 1000;

    /** Tu Vi tối thiểu để thăng tinh */
    public static final int MIN_TU_VI_THANG_TINH = 600;

    // ======================== 3 loại phẩm Đạo Lữ ========================
    /** Nhất Phẩm - phẩm thấp nhất, tỷ lệ ra 65% */
    public static final byte TYPE_NHAT_PHAM = 1;

    /** Nhị Phẩm - phẩm trung bình, tỷ lệ ra 30% */
    public static final byte TYPE_NHI_PHAM = 2;

    /** Tam Phẩm - phẩm cao nhất, tỷ lệ ra 5% */
    public static final byte TYPE_TAM_PHAM = 3;

    // ======================== 3 trạng thái hoạt động ========================
    /** Đi theo chủ - copy skill chủ khi chủ đánh */
    public static final byte STATUS_FOLLOW = 0;

    /** Tu luyện - tự tìm quái và đánh, tích lũy Tu Vi */
    public static final byte STATUS_ATTACK = 2;

    /** Về nhà - bay về map nhà (21/22/23 tùy chủng tộc), không buff cho chủ */
    public static final byte STATUS_GOHOME = 3;

    // ======================== Phạm vi chiến đấu ========================
    /** Phạm vi tìm quái để tấn công (pixel) */
    public static final int RANGE_FIND_MOB = 300;

    /** Phạm vi dùng skill cận chiến (pixel) */
    public static final int RANGE_MELEE = 100;

    // ======================== Thời gian ========================
    /** Thời gian hồi sinh sau khi chết (ms) = 5 giây */
    public static final int TIME_REVIVE_MS = 5000;

    /** Thời gian chờ trước khi về nhà (ms) = 2 giây */
    public static final int TIME_GO_HOME_MS = 2000;

    /** Thời gian di chuyển idle khi rảnh - min (ms) */
    public static final int TIME_IDLE_MOVE_MIN = 5000;

    /** Thời gian di chuyển idle khi rảnh - max (ms) */
    public static final int TIME_IDLE_MOVE_MAX = 8000;

    /** Thời gian giữa mỗi lần xin đậu thần (ms) */
    public static final int TIME_ASK_PEA_MS = 10000;

    /** Thời gian Đạo Lữ đi qua lại ở nhà (ms) */
    public static final int TIME_MOVE_AT_HOME_MS = 5000;

    // ======================== Bonus % chỉ số cho chủ theo PHẨM ========================
    /**
     * % buff HP/KI/SD cho chủ theo phẩm Đạo Lữ (cộng cả 3 chỉ số).
     * Nhất Phẩm = +5%, Nhị Phẩm = +10%, Tam Phẩm = +20%
     */
    public static final int[] BUFF_PHAM_HP =  {0, 5, 10, 20};
    public static final int[] BUFF_PHAM_KI =  {0, 5, 10, 20};
    public static final int[] BUFF_PHAM_SD =  {0, 5, 10, 20};

    // ======================== Bonus % chỉ số cho chủ theo CẤP BẬC ========================
    /**
     * % buff HP/KI/SD riêng cho từng cấp bậc (cộng dồn khi thăng).
     * Đấu Khí(0): 0/0/0
     * Đấu Giả(1): +5%HP
     * Đấu Sư(2): +5%KI
     * Đại Đấu Sư(3): +3%SD
     * Đấu Linh(4): +5%HP
     * Đấu Vương(5): +5%KI
     * Đấu Hoàng(6): +5%SD
     * Đấu Tông(7): +5%HP
     * Đấu Tôn(8): +5%KI
     * Đấu Thánh(9): +5%SD
     * Đấu Đế(10): +10%HP +10%KI +10%SD
     */
    public static final int[] BUFF_CAPBAC_HP = {0, 5, 0, 0, 5, 0, 0, 5, 0, 0, 10};
    public static final int[] BUFF_CAPBAC_KI = {0, 0, 5, 0, 0, 5, 0, 0, 5, 0, 10};
    public static final int[] BUFF_CAPBAC_SD = {0, 0, 0, 3, 0, 0, 5, 0, 0, 5, 10};

    // ======================== Item ID cần thiết ========================
    // [SỬA] Đổi item ID từ 2070 trở đi (tránh trùng item có sẵn trong nro_data)
    /** ID item "Hồn Đạo Lữ" - dùng để tạo Đạo Lữ */
    public static final int ITEM_HON_DAO_LU = 2070;

    /** ID item đan dược (2071-2080) - nguyên liệu thăng Đấu Đế */
    public static final int[] ITEM_DAN_DUOC = {2071, 2072, 2073, 2074, 2075, 2076, 2077, 2078, 2079, 2080};

    /** ID item "Mảnh Đà Xá Cổ Đế" - nguyên liệu thăng Đấu Đế */
    public static final int ITEM_DA_XA_CO_DE = 2081;

    /** Số lượng mỗi loại đan dược cần để thăng Đấu Đế */
    public static final int SO_LUONG_DAN_CAN = 1000;

    /** Số lượng mảnh Đà Xá Cổ Đế cần để thăng Đấu Đế */
    public static final int SO_LUONG_DA_XA_CAN = 4;

    // ======================== Ngoại hình ========================
    /**
     * Sprite ID ngoại hình Đạo Lữ theo giới tính.
     * [genderIndex][0=head, 1=body, 2=leg]
     * genderIndex: 0=Trái Đất, 1=Namek, 2=Xayda, 3=Power cao
     */
    public static final short[][] DAO_LU_STYLE = {
        {285, 286, 287},  // Trái Đất
        {288, 289, 290},  // Namek
        {282, 283, 284},  // Xayda
        {304, 305, 303}   // Style khi power > 1.5M
    };

    /**
     * Avatar head theo type Đạo Lữ (dùng ngoại hình cải trang).
     * TYPE_1 = Nhất Phẩm (CT 1503 hầu gái xanh): head=1379
     * TYPE_2 = Nhị Phẩm (CT 1504 hầu gái hồng): head=1382
     * TYPE_3 = Tam Phẩm (CT 1512 Bikini): head=1385
     */
    public static final short[] HEAD_BY_TYPE = {-1, 1379, 1382, 1385};

    /**
     * Avatar body theo type Đạo Lữ (dùng ngoại hình cải trang).
     * TYPE_1 = Nhất Phẩm (CT 1503): body=1380
     * TYPE_2 = Nhị Phẩm (CT 1504): body=1383
     * TYPE_3 = Tam Phẩm (CT 1512): body=1386
     */
    public static final short[] BODY_BY_TYPE = {-1, 1380, 1383, 1386};

    /**
     * Avatar leg theo type Đạo Lữ (dùng ngoại hình cải trang).
     * TYPE_1 = Nhất Phẩm (CT 1503): leg=1381
     * TYPE_2 = Nhị Phẩm (CT 1504): leg=1384
     * TYPE_3 = Tam Phẩm (CT 1512): leg=1387
     */
    public static final short[] LEG_BY_TYPE = {-1, 1381, 1384, 1387};

    // ======================== Câu nói của Đạo Lữ ========================
    /** Câu nói khi Đạo Lữ đánh quái (random 1 trong mảng) */
    public static final String[] CHAT_ATTACK = {
        "T vả chết đi~",
        "Dám đụng vào phu quân t này",
        "Chụy cho m thăng thiên"
    };

    /** Câu nói khi chuyển trạng thái FOLLOW */
    public static final String CHAT_FOLLOW = "Phu quân, thiếp ở đây...";

    /** Câu nói khi chuyển trạng thái ATTACK */
    public static final String CHAT_ATTACK_MODE = "Ok phu quân, thiếp sẽ chăm chỉ luyện tập";

    /** Câu nói khi chuyển trạng thái GOHOME */
    public static final String CHAT_GOHOME = "Tạm biệt chàng, cần gì cứ gọi, ta sẽ đến ngay";

    /** Câu nói khi hồi sinh */
    public static final String CHAT_REVIVE = "Phu quân, ta đã trở lại!";

    /** Câu nói khi xin đậu thần (hết mana) */
    public static final String CHAT_ASK_PEA = "Chàng có thể cho ta 1 hạt đậu thần không..?";

    /** Câu nói khi ở nhà */
    public static final String CHAT_AT_HOME = "Cơm đã nấu xong, nhà đã quét sạch, ta còn quên gì nữa không...!";

    // ======================== Tên cảnh giới ========================
    /** Tên 11 cấp bậc cảnh giới */
    public static final String[] TEN_CANH_GIOI = {
        "Đấu Khí",      // 0
        "Đấu Giả",      // 1
        "Đấu Sư",       // 2
        "Đại Đấu Sư",   // 3
        "Đấu Linh",     // 4
        "Đấu Vương",    // 5
        "Đấu Hoàng",    // 6
        "Đấu Tông",     // 7
        "Đấu Tôn",      // 8
        "Đấu Thánh",    // 9
        "Đấu Đế"        // 10
    };

    /** Tên cấp tinh cho cảnh giới 0 (Đấu Khí dùng "Đoạn") */
    public static final String[] TEN_CAP_TINH_DOAN = {
        "Nhất Đoạn", "Nhị Đoạn", "Tam Đoạn", "Tứ Đoạn", "Ngũ Đoạn",
        "Lục Đoạn", "Thất Đoạn", "Bát Đoạn", "Cửu Đoạn", "Cửu Đoạn Đỉnh Phong"
    };

    /** Tên cấp tinh cho cảnh giới 1-9 (dùng "Tinh") */
    public static final String[] TEN_CAP_TINH_TINH = {
        "Nhất Tinh", "Nhị Tinh", "Tam Tinh", "Tứ Tinh", "Ngũ Tinh",
        "Lục Tinh", "Thất Tinh", "Bát Tinh", "Cửu Tinh", "Cửu Tinh Đỉnh Phong",
        "Nữa Bước Lên"  // index 10: dùng cho cảnh giới 8 (Đấu Tôn) max tinh = 11
    };

    // ======================== Utility methods ========================

    /**
     * Lấy số cấp tinh tối đa cho mỗi cấp bậc cảnh giới.
     * - Đấu Đế(10): 0 (đã max)
     * - Đấu Tôn(8): 11
     * - Đấu Thánh(9), Đấu Tông(7)...Đại Đấu Sư(3): 10
     * - Đấu Sư(2), Đấu Giả(1), Đấu Khí(0): 9
     */
    public static int getMaxTinh(int capBac) {
        switch (capBac) {
            case 10: return 0;   // Đấu Đế - đỉnh cao rồi
            case 8:  return 11;  // Đấu Tôn - có thêm "Nữa Bước Đấu Thánh"
            case 9: case 7: case 6: case 5: case 4: case 3:
                return 10;       // Cảnh giới trung-cao
            case 2: case 1: case 0:
                return 9;        // Cảnh giới thấp
            default: return 0;
        }
    }

    /**
     * Lấy tên cảnh giới theo cấp bậc.
     * @param capBac 0-10
     * @return Tên cảnh giới, vd: "Đấu Vương"
     */
    public static String getTenCanhGioi(int capBac) {
        if (capBac >= 0 && capBac < TEN_CANH_GIOI.length) {
            return TEN_CANH_GIOI[capBac];
        }
        return "Vô Danh";
    }

    /**
     * Lấy tên cấp tinh theo cảnh giới và cấp tinh.
     * @param capBac  Cấp bậc cảnh giới (0-10)
     * @param capTinh Cấp tinh hiện tại
     * @return Tên cấp tinh, vd: "Tam Tinh"
     */
    public static String getTenCapTinh(int capBac, int capTinh) {
        if (capBac == 10) {
            return "Chí Tôn";
        }
        if (capBac == 0) {
            // Đấu Khí dùng "Đoạn" thay "Tinh"
            if (capTinh >= 0 && capTinh < TEN_CAP_TINH_DOAN.length) {
                return TEN_CAP_TINH_DOAN[capTinh];
            }
        } else {
            // Cảnh giới 1-9 dùng "Tinh"
            if (capTinh >= 0 && capTinh < TEN_CAP_TINH_TINH.length) {
                return TEN_CAP_TINH_TINH[capTinh];
            }
        }
        return "Phế Vật";
    }

    /**
     * Lấy tên đầy đủ cảnh giới + cấp tinh.
     * Ví dụ: "Đấu Vương - Ngũ Tinh" hoặc "Đấu Thánh - Đỉnh Phong"
     * @param capBac  Cấp bậc cảnh giới (0-10)
     * @param capTinh Cấp tinh hiện tại
     */
    public static String getFullCapBac(int capBac, int capTinh) {
        // Các mốc đặc biệt khi max tinh → gần thăng bậc
        String key = capBac + "-" + capTinh;
        switch (key) {
            case "9-10": return "Đấu Thánh - Đỉnh Phong";
            case "8-11": return "Đấu Tôn - Nữa Bước Đấu Thánh";
            case "8-10": return "Đấu Tôn - Đỉnh Phong";
            case "7-10": return "Đấu Tông - Nữa Bước Đấu Tôn";
            case "6-10": return "Đấu Hoàng - Nữa Bước Đấu Tông";
            case "5-10": return "Đấu Vương - Nữa Bước Đấu Hoàng";
            case "4-10": return "Đấu Linh - Nữa Bước Đấu Vương";
            case "3-10": return "Đại Đấu Sư - Nữa Bước Đấu Linh";
            default:
                return getTenCanhGioi(capBac) + " - " + getTenCapTinh(capBac, capTinh);
        }
    }

    /**
     * Lấy tên phẩm Đạo Lữ.
     * @param type 1=Nhất Phẩm, 2=Nhị Phẩm, 3=Tam Phẩm
     */
    public static String getTenPham(int type) {
        switch (type) {
            case 3: return "Tam Phẩm";
            case 2: return "Nhị Phẩm";
            case 1: return "Nhất Phẩm";
            default: return "Vô Phẩm";
        }
    }

    /**
     * Lấy text thông báo khi tạo Đạo Lữ (kèm màu).
     * @param type 1=Nhất Phẩm, 2=Nhị Phẩm, 3=Tam Phẩm
     */
    public static String getTextTaoDaoLu(int type) {
        switch (type) {
            case 3:
                return "|2|Nhân Phẩm Ngút Trời!!! Bạn Đã Chiêu Mộ Được Đạo Lữ \n|7|Tam Phẩm";
            case 2:
                return "|8|Nhân Phẩm Hơn Người!!! Bạn Đã Chiêu Mộ Được Đạo Lữ \n|5|Nhị Phẩm";
            case 1:
                return "Bạn Đã Chiêu Mộ Được Đạo Lữ\n|0|Nhất Phẩm";
            default:
                return "Vô Định";
        }
    }

    /**
     * Tính tỷ lệ thăng tinh (%).
     * - Đấu Khí → Đấu Hoàng (level 0-6): Nhất tinh 100%, mỗi tinh giảm 5%
     * - Đấu Tông, Đấu Tôn (level 7-8): Nhất tinh 100%, mỗi tinh giảm 6%
     * - Đấu Thánh (level 9): Nhất tinh 100%, mỗi tinh giảm 7%
     * Thất bại: rớt hết Tu Vi
     *
     * @param capCanhGioi Cấp bậc cảnh giới hiện tại (0-9)
     * @param capTinh     Cấp tinh hiện tại (đang thăng từ capTinh lên capTinh+1)
     * @return Tỷ lệ thành công (0-100)%
     */
    public static int getTiLeThangTinh(int capCanhGioi, int capTinh) {
        int giamMoiTinh;
        if (capCanhGioi <= 6) {
            giamMoiTinh = 5;   // Đấu Khí → Đấu Hoàng: -5% mỗi tinh
        } else if (capCanhGioi <= 8) {
            giamMoiTinh = 6;   // Đấu Tông, Đấu Tôn: -6% mỗi tinh
        } else {
            giamMoiTinh = 7;   // Đấu Thánh: -7% mỗi tinh
        }
        int tiLe = 100 - capTinh * giamMoiTinh;
        if (tiLe > 100) return 100;
        if (tiLe < 1) return 1; // Tối thiểu 1%
        return tiLe;
    }

    /**
     * Tính tỷ lệ thăng cảnh giới (%).
     * Đấu Khí(0)=90%, Đấu Giả(1)=80%, Đấu Sư(2)=75%, Đại Đấu Sư(3)=70%,
     * Đấu Linh(4)=65%, Đấu Vương(5)=60%, Đấu Hoàng(6)=55%,
     * Đấu Tông(7)=50%, Đấu Tôn(8)=40%
     * Đấu Thánh(9) lên Đấu Đế = 100% nếu đủ nguyên liệu (qua thangDauDe)
     *
     * @param capBac Cấp bậc cảnh giới hiện tại
     * @return Tỷ lệ thành công (%)
     */
    public static int getTiLeThangCanhGioi(int capBac) {
        switch (capBac) {
            case 0: return 90;  // Đấu Khí → Đấu Giả
            case 1: return 80;  // Đấu Giả → Đấu Sư
            case 2: return 75;  // Đấu Sư → Đại Đấu Sư
            case 3: return 70;  // Đại Đấu Sư → Đấu Linh
            case 4: return 65;  // Đấu Linh → Đấu Vương
            case 5: return 60;  // Đấu Vương → Đấu Hoàng
            case 6: return 55;  // Đấu Hoàng → Đấu Tông
            case 7: return 50;  // Đấu Tông → Đấu Tôn
            case 8: return 40;  // Đấu Tôn → Đấu Thánh
            default: return 0;
        }
    }

    /**
     * Số tinh bị mất khi thăng cảnh giới thất bại.
     * Đấu Khí → Đấu Vương (0-5): -1 tinh
     * Đấu Hoàng (6): -2 tinh
     * Đấu Tông, Đấu Tôn (7-8): -3 tinh
     *
     * @param capBac Cấp bậc cảnh giới hiện tại
     * @return Số tinh bị trừ khi thất bại
     */
    public static int getSoTinhMatKhiThatBai(int capBac) {
        if (capBac <= 5) return 1;   // Đấu Khí → Đấu Vương
        if (capBac == 6) return 2;   // Đấu Hoàng
        return 3;                     // Đấu Tông, Đấu Tôn
    }

    /**
     * Tính tổng % buff HP từ cấp bậc (cộng dồn từ 0 đến capBac hiện tại).
     */
    public static int getTotalBuffHP(int capBac) {
        int total = 0;
        for (int i = 0; i <= capBac && i < BUFF_CAPBAC_HP.length; i++) {
            total += BUFF_CAPBAC_HP[i];
        }
        return total;
    }

    /**
     * Tính tổng % buff KI từ cấp bậc (cộng dồn từ 0 đến capBac hiện tại).
     */
    public static int getTotalBuffKI(int capBac) {
        int total = 0;
        for (int i = 0; i <= capBac && i < BUFF_CAPBAC_KI.length; i++) {
            total += BUFF_CAPBAC_KI[i];
        }
        return total;
    }

    /**
     * Tính tổng % buff SD từ cấp bậc (cộng dồn từ 0 đến capBac hiện tại).
     */
    public static int getTotalBuffSD(int capBac) {
        int total = 0;
        for (int i = 0; i <= capBac && i < BUFF_CAPBAC_SD.length; i++) {
            total += BUFF_CAPBAC_SD[i];
        }
        return total;
    }
}
