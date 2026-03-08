package dragon.u;

import dragon.object.Char;
import dragon.object.Item;
import dragon.object.ItemOption;
import dragon.server.Server;
import dragon.server.Session_ME;
import dragon.server.mResources;
import dragon.t.Player;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import dragon.t.Money;

/**
 *
 * @author Admin
 */
public class Admin {

    public static Admin instance;

    public static Admin gI() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public void openAdmin(Char charz) {
        charz.clientInput.typeInput = -1;
        charz.resetMenu();

        // Lấy thông tin CPU và RAM
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuLoad = osBean.getSystemCpuLoad() * 100; // % sử dụng CPU
        long ramUsed = (osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize()) / (1024 * 1024); // RAM đang dùng (MB)
        long ramTotal = osBean.getTotalPhysicalMemorySize() / (1024 * 1024); // Tổng RAM (MB)

        charz.menuBoard.chat = "Chức năng dành cho Admin\n"
                + "|5|Số người online: " + Server.gI().sizeByUId() + "\n"
                + "|7|CPU: " + String.format("%.2f", cpuLoad) + "% / 100%\n"
                + "|7|RAM: " + ramUsed + "MB / " + ramTotal + "MB\n"
                + "|7|Time Start Server: " + Util.gI().getFormatTime3(System.currentTimeMillis() - Server.gI().lastStart);

        charz.menuBoard.arrMenu.add(new MenuInfo("Hệ thống", 1, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("Chat vip", 2, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("Người chơi", 3, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("Vật phẩm", 4, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("Boss", 5, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("Log", 6, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("OpenSay", 7, -2003));
        charz.menuBoard.arrMenu.add(new MenuInfo("OpenSay2", 19, -2003));

        charz.menuBoard.openUIConfirm(5, null, null, -1);
    }

    public void select(Char charz, MenuInfo info, int select) {
        Session_ME session = charz.session;
        switch (info.type) {
            case 1: {
                charz.resetMenu();
                charz.menuBoard.chat = "Một số chức năng hệ thống";
                charz.menuBoard.arrMenu.add(new MenuInfo("Thống kê", 8, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Bảo trì", 9, -2003));
                charz.menuBoard.openUIConfirm(5, null, null, -1);
                break;
            }
            case 2: {
                charz.session.service.openClientInput("Nhập chat vip", new String[]{""}, new int[]{1});
                break;
            }
            case 3: {
                charz.resetMenu();
                charz.menuBoard.chat = "Bạn có thể thực hiên một số chức năng với người chơi tại đây";
                charz.menuBoard.arrMenu.add(new MenuInfo("Khóa vĩnh viễn", 10, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Khóa Chat", 11, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Tìm kiếm", 12, -2003));
                charz.menuBoard.openUIConfirm(5, null, null, -1);
                break;
            }
            case 4: {
                charz.resetMenu();
                charz.menuBoard.chat = "Hãy lựa chọn kiểu gọi vật phẩm";
                charz.menuBoard.arrMenu.add(new MenuInfo("Id vật phẩm\nsố lượng 1", 13, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Id vật phẩm\nNhập số lượng", 14, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Id vật phẩm\nkhác", 15, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Buff Player", 20, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Buff Money", 21, -2003));
                charz.menuBoard.openUIConfirm(5, null, null, -1);
                break;
            }
            case 5: {
                charz.resetMenu();
                charz.menuBoard.chat = "Boss có thể xuất hiện như nào";
                charz.menuBoard.arrMenu.add(new MenuInfo("Gọi BotId", 16, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Gọi Đầy Đủ", 17, -2003));
                charz.menuBoard.arrMenu.add(new MenuInfo("Gọi Boss Tùy Ý", 18, -2003));
                charz.menuBoard.openUIConfirm(5, null, null, -1);
                break;
            }
            case 6: {
                charz.session.service.openClientInput("Nhập log", new String[]{""}, new int[]{1});
                break;
            }
            case 7: {
                charz.session.service.openClientInput("Thông báo", new String[]{"NpcId (Có thể là 4)", "Nhập thông báo", "Avatar (Tùy ý)"}, new int[]{0, 1, 0});
                break;
            }
            case 8: {
                charz.session.service.openUISay(5, "LEVEL " + charz.clevel + " Tổng số kết nối:" + Server.gI().sizeConn() + " tổng online:" + Server.gI().sizeByUId() + " MapId=" + charz.zoneMap.mapTemplate.mapTemplateId + " cx=" + charz.cx + " cy=" + charz.cy + " sizeMob=" + charz.zoneMap.mobs.size() + " indexTile=" + (charz.cy / 24 * charz.zoneMap.mapTemplate.tmw + charz.cx / 24) + " sizeType=" + charz.zoneMap.mapTemplate.types.length, 1363);
                break;
            }
            case 9: {
                 if (Server.gI().timeBaoTri != -1) {
                    Server.gI().timeBaoTri = -1;
                } else {
                    Server.gI().timeBaoTri = 15000;
                }
                break;
//                Server.start = false;
//                break;
            }
            case 10: {
                charz.session.service.openClientInput("Tên nhân vật", new String[]{"Khóa vĩnh viễn"}, new int[]{1});
                break;
            }
            case 11: {
                charz.session.service.openClientInput("Tên nhân vật", new String[]{"Khóa chat thế giới"}, new int[]{1});
                break;
            }
            case 12: {
                charz.session.service.openClientInput("Tên nhân vật", new String[]{"Sẽ đưa bạn tới nhân vật này"}, new int[]{1});
                break;
            }
            case 13: {
                charz.session.service.openClientInput("Nhập thông tin vật phẩm", new String[]{"Id vật phẩm có số lượng là 1"}, new int[]{0});
                break;
            }
            case 14: {
                charz.session.service.openClientInput("Nhập thông tin vật phẩm", new String[]{"Id vật phẩm", "Số lượng"}, new int[]{0, 0});
                break;
            }
            case 15: {
                charz.session.service.openClientInput("Nhập thông tin vật phẩm", new String[]{"Id vật phẩm", "Số lượng", "trạng thái", "hành tinh"}, new int[]{0, 0, 0, 0});
                break;
            }
            case 16: {
                charz.session.service.openClientInput("Nhập Thông Tin Boss", new String[]{"botId", "Có báo KTG = 1"}, new int[]{0, 0});
                break;
            }
            case 17: {
                charz.session.service.openClientInput("Nhập Thông Tin Boss", new String[]{"botId", "Trạng thái PK", "HP", "Sát thương", "Tấn công = 1", "Có báo KTG = 1"}, new int[]{0, 0, 0, 0, 0, 0});
                break;
            }
            case 18: {
                charz.session.service.openClientInput("Nhập Thông Tin Boss", new String[]{"BotId", "Trạng thái PK", "Name HP ST", "Báo KTG = 1", "Head Body Leg"}, new int[]{0, 0, 1, 0, 1});
                break;
            }
            case 19: {
                charz.session.service.openClientInput("Thông báo", new String[]{"NpcId (Có thể là 4)", "Nhập thông báo", "Avatar (Tùy ý)", "P", "Caption"}, new int[]{0, 1, 0, 1, 1});
                break;
            }
            case 20: {
                charz.session.service.openClientInput("Nhập thông tin vật phẩm",
                        new String[]{"Tên nhân vật", "ID vật phẩm", "Số lượng"},
                        new int[]{1, 0, 0});
                break;
            }
            case 21: {
                charz.session.service.openClientInput("Nhập thông tin buff tiền",
                        new String[]{"Tên nhân vật", "Số tiền"},
                        new int[]{1, 0});
                break;
            }
        }
    }

    public void input(Char charz, String[] texts) {
        switch (charz.menuBoard.typeInfo) {
            case 2: {
                Server.gI().chatVip(texts[0]);
                break;
            }
            case 6: {
                Server.gI().log(texts[0]);
                break;
            }
            case 7: {
                Server.gI().openSay(Byte.parseByte(texts[0]), texts[1], Short.parseShort(texts[2]));
                break;
            }
            case 10: {
                Session_ME player = Server.gI().getByCName(texts[0]);
                if (player != null) {
                    player.isLock = 1;
                    player.disconnect();
                    charz.session.service.startOKDlg("Đã khóa thành công tài khoản nhân vật: " + texts[0]);
                } else {
                    charz.session.service.startOKDlg("Nhân vật không tồn tại hoặc chưa online.");
                }
                break;
            }
            case 11: {
                Session_ME player = Server.gI().getByCName(texts[0]);
                if (player != null) {
                    player.isLock = 2;
                    charz.session.service.startOKDlg("Đã khóa thành công tài khoản nhân vật: " + texts[0]);
                } else {
                    charz.session.service.startOKDlg("Nhân vật không tồn tại hoặc chưa online.");
                }
                break;
            }
            case 12: {
                Session_ME player = Server.gI().getByCName(texts[0]);
                if (player != null && player.myCharz().zoneMap != null) {
                    charz.zoneMap.exit(charz, 0);
                    player.myCharz().zoneMap.join(charz, 0, player.myCharz().cx, player.myCharz().cy);
                } else {
                    charz.session.service.startOKDlg("Nhân vật không tồn tại hoặc chưa online.");
                }
                break;
            }
            case 13:
            case 14:
            case 15: {
                int num = 1;
                int status = 0;
                int gender = charz.cgender;
                try {
                    num = Integer.parseInt(texts[1]);
                } catch (Exception e) {
                }
                try {
                    status = Byte.parseByte(texts[2]);
                } catch (Exception e) {
                }
                try {
                    gender = Byte.parseByte(texts[3]);
                } catch (Exception e) {
                }
                String[] numbers = texts[0].trim().split(" ");
                for (int i = 0; i < numbers.length; i++) {
                    Item it = new Item(Integer.parseInt(numbers[i]), false, num, ItemOption.getOption(Integer.parseInt(numbers[i]), status, gender), "", "", "");
                    if (it.isItemSLL()) {
                        it.quantity = 1;
                        it.getOption(31).param = num;
                    }
                    charz.addItemBag(0, it);
                }
                break;
            }
            case 16: {
                Player bot2 = Player.addBoss(Short.parseShort(texts[0]), 5, -1, -1, true, charz.cx, charz.cy, charz.zoneMap, -1, -1);
                if (Byte.parseByte(texts[1]) == 1) {
                    Server.gI().chatVip(String.format(mResources.BOSS_HAVE, bot2.cName, charz.zoneMap.mapTemplate.mapName));
                }
                break;
            }
            case 17: {
                Player bot2 = Player.addBoss(Short.parseShort(texts[0]), Byte.parseByte(texts[1]), Integer.parseInt(texts[2]), Integer.parseInt(texts[3]), Integer.parseInt(texts[4]) != 0, charz.cx, charz.cy, charz.zoneMap, -1, -1);
                if (Byte.parseByte(texts[5]) == 1) {
                    Server.gI().chatVip(String.format(mResources.BOSS_HAVE, bot2.cName, charz.zoneMap.mapTemplate.mapName));
                }
                break;
            }
            case 18: {
                String[] arrStr = texts[2].split(" ");
                String[] arrStr2 = texts[4].split(" ");
                Char c = Player.addBoss(Short.parseShort(texts[0]), Byte.parseByte(texts[1]), Integer.parseInt(arrStr[arrStr.length - 2]), Integer.parseInt(arrStr[arrStr.length - 1]), true, charz.cx, charz.cy, null, -1, -1);
                c.cName = texts[2].replace(" " + arrStr[arrStr.length - 2] + " " + arrStr[arrStr.length - 1], "");
                c.head = c.headDefault = Short.parseShort(arrStr2[0]);
                c.body = c.bodyDefault = Short.parseShort(arrStr2[1]);
                c.leg = c.legDefault = Short.parseShort(arrStr2[2]);
                charz.zoneMap.join(c, 0, -1, -1);
                if (Byte.parseByte(texts[3]) == 1) {
                    Server.gI().chatVip(String.format(mResources.BOSS_HAVE, c.cName, charz.zoneMap.mapTemplate.mapName));
                }
                break;
            }
            case 19: {
                Server.gI().bigMessage2(Byte.parseByte(texts[0]), texts[1], texts[3], Short.parseShort(texts[2]), texts[4]);
                break;
            }
            case 20: {
                String playerName = texts[0];
                int itemId = Integer.parseInt(texts[1]);
                int quantity = Integer.parseInt(texts[2]);

                Session_ME targetPlayer = Server.gI().getByCName(playerName);
                if (targetPlayer != null) {
                    Item item = new Item(itemId, false, quantity,
                            ItemOption.getOption(itemId, 0, targetPlayer.myCharz().cgender), "", "", "");
                    targetPlayer.myCharz().addItemBag(0, item);
                    charz.session.service.startOKDlg("Đã gửi vật phẩm thành công!");
                } else {
                    charz.session.service.startOKDlg("Người chơi không tồn tại hoặc không online.");
                }
                break;
            }
            case 21: {
                String playerName = texts[0];
                int amount = Integer.parseInt(texts[1]);
                Session_ME player = Server.gI().getByCName(playerName);

                if (player != null && player.myCharz() != null) {
                    long playerMoney = Money.gI().getMoney(player.myCharz());
                    Money.gI().updateMoeny(player.myCharz(), amount);
                    charz.session.service.startOKDlg("Đã cộng " + amount + " tới nhân vật " + playerName);
                } else {
                    charz.session.service.startOKDlg("Người chơi không tồn tại hoặc không online.");
                }
                break;
            }

        }
    }
}
