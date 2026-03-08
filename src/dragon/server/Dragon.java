package dragon.server;

import dragon.object.BgItem;
import dragon.u.GameData;
import dragon.u.Util;
import dragon.object.GameInfo;
import dragon.object.ImageSource;
import dragon.object.Map;
import dragon.object.SmallImage;
import dragon.template.MapTemplate;
import hethong.ServerActivity;
import io.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import dragon.u.NamekBall;
import dragon.u.SuperRank;
import dragon.v.LuckyRoundNew;
import dragon.v.LuyenTap;
import hethong.AutoSave;

/**
 *
 * @author Admin
 */
public class Dragon {
    
    protected static int vResource = 5714017;
    protected static byte vData = 47;
    protected static byte vMap = 47;
    protected static byte vSkill = 14;
    protected static byte vItem = 38;
    public static String LINK_IP_PORT_SERVER = "Nro Huyết Chiến :160.191.175.24:12345:0,0,0";
 // sự kiện>>
    public static int PORT = 12345;
    public static boolean isEvent_Noel = false;
    public static boolean isEvent_Mabu = false;
    public static boolean isEvent_NHS = false;
    public static boolean isEvent_NgayHe = false;
    public static boolean isEvent_TrungThu = false;
    public static boolean isEvent_Halloween = false;
    public static boolean isEvent_WorldCup2022 = false;
    public static boolean isEvent_TetNguyenDan = false;
    public static boolean isEvent_Girl = false;
    public static boolean isEvent_HungVuong = false;
    public static boolean isEvent_HE2023 = false;
    public static boolean isEvent_DIET_SAU_BO_2023 = false;
    public static boolean isEvent_VIP = true;

    
    public static void main(String[] args) {
        Util.gI().setDebug(false);
        System.out.println("DEBUG="+ Util.gI().debug);
        ServerActivity.gI().Activity();
        GameData.init();
        // [ĐẠO LỮ] Kiểm tra bảng daolus trong database nro_player
        try {
            MySQL checkDB = MySQL.createData3();
            try {
                checkDB.getConnection().prepareStatement("SELECT 1 FROM `daolus` LIMIT 1").executeQuery();
                System.out.println("[DaoLu] OK: Bảng 'daolus' tồn tại trong nro_player.");
            } catch (Exception e) {
                System.out.println("[DaoLu] CẢNH BÁO: Bảng 'daolus' CHƯA CÓ trong database nro_player!");
                System.out.println("[DaoLu] Chạy SQL: CREATE TABLE daolus ... từ file sql/nro_daolu.sql");
                System.out.println("[DaoLu] Lỗi: " + e.getMessage());
            } finally {
                checkDB.close();
            }
        } catch (Exception e2) {
            System.out.println("[DaoLu] Không thể kết nối nro_player: " + e2.getMessage());
        }
        Server.init();
        MapTemplate.initCollisionPixel();
        Map.initMapServer();
        Server.gI().openCace23();
        ImageSource.initImg();
        SmallImage.init();
        BgItem.init();
        GameInfo.init();
        Server.gI().initEvent();
 //     Server.gI().initAnTrom();
        Server.gI().initNpc();
        NamekBall.gI().initNamekBall();
        Server.gI().openPrize();
        SuperRank.init();
        LuckyRoundNew.init();
        LuyenTap.init();
        Server.gI().initBroly();
        Server.gI().initKuku();
        Server.gI().initMap_Dau_Dinh();
        Server.gI().initRambo();
        AutoSave.startAutoSave();
        Server.gI().initBotTop();
        Server.gI().initMabu();
        Server.start(PORT);
        
    }

    public static byte[] getFile(String url) {
        FileInputStream openFileInput;
        try {
            openFileInput = new FileInputStream(url);
            byte[] data = new byte[openFileInput.available()];
            openFileInput.read(data);
            openFileInput.close();
            return data;
        } catch (Exception e) {
            System.out.println("Path "+url);
//            e.printStackTrace();
        }
        return null;
    }

    public static void saveFile(String url, byte[] ab) {
        FileOutputStream fos;
        try {
            File f = new File(url);
            if(f.exists())
                f.delete();
            f.createNewFile();
            fos = new FileOutputStream(url);
            fos.write(ab);
            fos.flush();
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    protected static void writeByteArray(Message msg,byte[] ab) {
        try {
            msg.writer().writeInt(ab.length);
            msg.writer().write(ab);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
