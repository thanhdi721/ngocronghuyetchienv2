package dragon.u;

/**
 *
 * @author TGDD
 */
public class MenuInfo {
    
    public String strMenu;
    public int type;
    public int index;
    public Object p;
    public String[] arrayStr0;
    public String stm;
    
    public MenuInfo(String str, int type) {
        this.strMenu = str;
        this.type = type;
    }
    
    public MenuInfo(String str, int type, Object p) {
        this.strMenu = str;
        this.type = type;
        this.p = p;
    }
    
    public MenuInfo(String str, int type, int index) {
        this.strMenu = str;
        this.type = type;
        this.index = index;
    }
    
    public boolean excute() {
        return true;
    }
    
    public void clean() {
        
    }
    
}
