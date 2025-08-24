package dragon.object;

import java.util.ArrayList;

/**
 *
 * @author TGDD
 */
public class GameInfo {
    
    public String main;
    public String content;
    public short id;
    public boolean hasRead;
    
    public GameInfo(int id, String main, String content) {
        this.id = (short) id;
        this.main = main;
        this.content = content;
    }
    
    public static ArrayList<GameInfo> infos = new ArrayList<>();
    
    public static void init() {
        infos.add(new GameInfo(100, "Tân thủ", "Làm nhiệm vụ hàng ngày để nhận ngọc, vàng miễn phí tại Bò Mộng\n\n-Thân BTH."));
//        infos.add(new GameInfo(5, "Hướng Dẫn Nạp Tiền", "Truy cập trang chủ nrovn.com để nạp tiền\n\n-Tại NPC Ông Nội bạn có thể quy đổi ngọc, vàng"));
//        infos.add(new GameInfo(6, "Mã Quà Tặng", "Mã quà tặng: tanthu\nNhập tại NPC Ông Nội"));
    }
    
}
