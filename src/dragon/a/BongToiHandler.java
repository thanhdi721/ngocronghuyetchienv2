package dragon.a;

import dragon.object.Char;
import dragon.object.Item;
import dragon.object.ItemOption;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BongToiHandler {
    /*
    - Bộ 3: Tổng chỉ số buff từ trang bị Bóng Tối được tăng nhẹ 
    - Bộ 6: Kích hoạt Bóng Đêm. Tổng chỉ số buff từ trang bị Án Sáng tăng mạnh. Giảm sát thương từ Boss và Quái Bóng Tối - Thánh Quang:
    - Trong khoảng thời gian ban ngày (từ 5h sáng đến 16h chiều), trang bị Ánh Sáng sẽ được tăng cường sát thương gây ra cho boss, quái Bóng Tối, tăng 
       HP và KI, giảm nhẹ SĐ 
    - Bóng Đêm: Khoảng thời gian về tối (từ 17h tối đến 3h sáng), trang bị Bóng Tối sẽ được tăng cường sát thương gây ra cho boss, quái Ánh Sáng, tăng SĐ,
      giảm nhẹ HP và KI - Trong khoảng thời gian ban ngày (5h sáng đến 15h chiều) 
       + //buff, tăng 30% cho tổng chỉ số được buff thêm (VD: tổng buff hp là 20% thì 30% của 20 là 6 => buff thêm thành 26% 
       + //buff bộ 3: tăng 40% tổng chỉ số được buff thêm. Cách tính tương tự 
       + //buff bộ 6: Tăng cường sát thương (30% gây ra cho boss và 50% gây ra cho quái thuộc ánh sáng). Tăng 30% sức đánh, giảm 20% HP và KI. 
    - Trong khoảng thời gian về tối (17h chiều đến 3h sáng) 
       + //debuff, giảm 50% cho tổng chỉ số được buff thêm, cách tính tương tự như buff thêm 
       + //buff bộ 3: xoá bỏ debuff và tăng 5% cho tổng chỉ số được buff thêm, cách tính tương tự như buff thêm
       + //buff bộ 6: Tăng cường sát thương (15% gây ra cho boss và 25% gây ra cho quái thuộc ánh sáng). Tăng 10% sức đánh, giảm 20% HP và KI.
     */
    private static final List<Integer> option_item = Arrays.asList(247, 248, 249);
    private static final int id_option_level = 250;
    private static final int id_option_set_bo_3 = 230;
    private static final int id_option_set_bo_6 = 231;
    private static final int id_option_noi_tai = 235;

    private static final int id_map_quai_anh_sang = 0;
    private static final int id_boss_anh_sang = 0;

    public static int PARAM_HP;
    public static int PARAM_KI;
    public static int PARAM_SD;

    public static int countBongToiItems(Char charz) {
        if (charz == null) {
            return 0;
        }
        if (charz.arrItemBody == null) {
            return 0;
        }
        int count = 0;
        for (Item it : charz.arrItemBody) {
            if (it == null || it.options == null) {
                continue;
            }
            for (ItemOption op : it.options) {
                if (option_item.contains(op.optionTemplate.id)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static boolean isSet3BongToi(Char charz) {
        if (charz == null) {
            return false;
        }
        return countBongToiItems(charz) >= 3;
    }

    public static boolean isSet6BongToi(Char charz) {
        if (charz == null) {
            return false;
        }
        return countBongToiItems(charz) >= 6;
    }

    public static void updateOptionNoiTaiByTime(Char charz, int hoursNow) {
        for (Item trangBi : charz.arrItemBody) {
            if (trangBi == null || trangBi.options == null) {
                continue;
            }
            boolean hasOption = trangBi.options.stream()
                    .anyMatch(op -> op.optionTemplate.id == id_option_noi_tai);
            boolean hasBongToi = trangBi.options.stream()
                    .anyMatch(op -> option_item.contains(op.optionTemplate.id));
            if (!hasBongToi) {
                continue;
            }
            if (!hasOption && (hoursNow >= 17 || hoursNow <= 3)) {
                trangBi.options.add(new ItemOption(id_option_noi_tai, 0));
            } else if (hasOption && !(hoursNow >= 17 || hoursNow <= 3)) {
                trangBi.options.removeIf(op -> op.optionTemplate.id == id_option_noi_tai);
            }
        }
    }

    private static void updateSetOption(boolean isSetActive, int optionId, Char charz) {
        if (charz == null) {
            return;
        }
        for (Item trangBi : charz.arrItemBody) {
            if (trangBi == null || trangBi.options == null) {
                continue;
            }

            boolean hasSet = trangBi.options.stream()
                    .anyMatch(op -> op.optionTemplate.id == optionId);
            boolean hasBongToi = trangBi.options.stream()
                    .anyMatch(op -> option_item.contains(op.optionTemplate.id));

            if (isSetActive && hasBongToi && !hasSet) {
                trangBi.options.add(new ItemOption(optionId, 0));
            } else if (!isSetActive && hasSet) {
                trangBi.options.removeIf(op -> op.optionTemplate.id == optionId);
            }
        }
    }

    private static void updateSetOptionByItem(boolean isSetActive, int optionId, Char charz, Item trangBi) {
        if (charz == null) {
            return;
        }
        if (trangBi == null || trangBi.options == null) {
            return;
        }

        boolean hasSet = trangBi.options.stream()
                .anyMatch(op -> op.optionTemplate.id == optionId);
        boolean hasBongToi = trangBi.options.stream()
                .anyMatch(op -> option_item.contains(op.optionTemplate.id));

        if (isSetActive && hasBongToi && !hasSet) {
            trangBi.options.add(new ItemOption(optionId, 0));
        } else if (!isSetActive && hasSet) {
            trangBi.options.removeIf(op -> op.optionTemplate.id == optionId);
        }
    }

    public static void updateSetOptions(Char charz) {
        if (charz == null) {
            return;
        }
        updateSetOption(isSet3BongToi(charz), id_option_set_bo_3, charz);
        updateSetOption(isSet6BongToi(charz), id_option_set_bo_6, charz);
    }

    public static void updateSetOptionsByItem(Char charz, Item trangBi) {
        if (charz == null) {
            return;
        }
        updateSetOptionByItem(isSet3BongToi(charz), id_option_set_bo_3, charz, trangBi);
        updateSetOptionByItem(isSet6BongToi(charz), id_option_set_bo_6, charz, trangBi);
    }

    private static void calculateBaseParams(Char charz) {
        PARAM_HP = 0;
        PARAM_KI = 0;
        PARAM_SD = 0;

        for (Item trangBi : charz.arrItemBody) {
            if (trangBi == null || trangBi.options == null) {
                continue;
            }

            for (ItemOption op : trangBi.options) {
                switch (op.optionTemplate.id) {
                    case 247:
                        PARAM_HP += op.param;
                        break;
                    case 248:
                        PARAM_KI += op.param;
                        break;
                    case 249:
                        PARAM_SD += op.param;
                        break;
                }
            }
            for (ItemOption op : trangBi.options) {
                System.out.println("trang bị bóng tối " + op.optionTemplate.id + "param" + op.param);
            }
        }
    }

    public static void update(Char charz) {
        if (charz == null) {
            return;
        }
        calculateBaseParams(charz);
    }
}
