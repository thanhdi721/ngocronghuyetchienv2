package dragon.u;

import dragon.object.Char;
import dragon.object.Item;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author TGDD
 */
public class Obj {

    public final static HashMap<String, Obj> HOBJ = new HashMap<>();
    public final ArrayList<Item> blackBalls = new ArrayList<>();
    public int nTrangsach = 0;
    public int nManhAo = 0;
    public int nManhQuan = 0;
    public int nManhGang = 0;
    public int nManhGiay = 0;
    public int nManhNhan = 0;
    public boolean petition;
    public Char myChar;
    public ArrayList<NauBanh> arrBanh = new ArrayList<>();
    public int pointEvent;
    public boolean isPotage;
    public long lastNB;
    public boolean isWinClone;
    public int nTangBongHoaXanh;
    public int nTangChauHoaXanh;
    public int nPointTang1;
    public int nRuaCon;
    public int nFreeTicket = 2;
    public int nBiKipTuyetKi;
    public ArrayList<Item> reBuyItem = new ArrayList<>();
    public int nJoinDH23 = 0;
    public int nWinHD23 = 0;
    public boolean isHideFusion;
    public int pointEventVIP = 0;
    public int vip_1 = 0;
    public boolean isHaveItemBlackBall(int id) {
        synchronized (this.blackBalls) {
            for (int i = 0; i < this.blackBalls.size(); i++) {
                if (this.blackBalls.get(i).template.id == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addItemBlackBall(Item item) {
        synchronized (this.blackBalls) {
            this.blackBalls.add(item);
        }
    }

    public void addNauBanh(int sc, int id, String text, Item item) {
        this.arrBanh.add(new NauBanh(sc, id, text, item));
    }

    public static Obj gI(String cName) {
        Obj obj;
        if (dragon.u.Obj.HOBJ.containsKey(cName)) {
            obj = dragon.u.Obj.HOBJ.get(cName);
        } else {
            obj = new Obj();
            dragon.u.Obj.HOBJ.put(cName, obj);
        }
        return obj;
    }

}
