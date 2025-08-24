package hethong;

import dragon.server.Server;
import dragon.t.Clan;
import dragon.t.Rank;
import dragon.u.ItemKyGui;
import dragon.u.SuperRank;
import dragon.v.LuckyRoundNew;
import dragon.v.LuyenTap;

/**
 *
 * @author ◆𝕮𝖍𝖎𝖕-𝕯𝖊𝖛♚
 */
public class AutoSave implements Runnable {

    private int timeSave = 5; // Thời gian chờ trước khi lưu (30 giây)
    private int setSave = 5;  // Thời gian lưu lại (30 giây)

    @Override
    public void run() {
        while (true) {
            try {
                // Đợi 30 giây trước khi kiểm tra và lưu dữ liệu
                Thread.sleep(5000);

                if (timeSave <= 0) {
                    // Reset lại thời gian lưu
                    timeSave = setSave;
                    // Lưu dữ liệu
                    saveData();
                } else {
                    // Giảm thời gian đếm ngược
                    timeSave -= 5;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveData() {
       Server.gI().saveData();
    }

    public static void startAutoSave() {
        AutoSave autoSave = new AutoSave();
        new Thread(autoSave).start(); // Khởi động thread auto save
        System.out.println("____[AutoSave OPEN.]____");
    }
}
