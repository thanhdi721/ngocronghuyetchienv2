package dragon.v;

import dragon.server.Session_ME;

/**
 *
 * @author TGDD
 */
public class NapThe {
    
    public static final String NETWORK[] = new String[]{"VIETTEL"};
    public static final int PRICE[] = new int[]{10000, 20000, 30000, 50000, 100000, 200000, 500000};
    public static final String API_KEY = "%s%d%s%s";//network, price, code, serial,
    
    public String netWork = null;
    public int price = 0;
    public String code = null;
    public String seri = null;
    public Session_ME session;
    
    public NapThe(Session_ME session) {
        this.session = session;
    }
    
    public void okNapThe() {
        this.session.service.startOKDlg("Đang cập nhật....");
    }
    
}
