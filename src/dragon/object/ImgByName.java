package dragon.object;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TGDD
 */
public class ImgByName {
    
    public String nameImg;
    public int nFrame;
    
    public ImgByName(ResultSet res) {
        try {
        this.nameImg = res.getString(1);
        this.nFrame = res.getByte(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ImgByName[] arrImgByName;
    
    public static ImgByName get(String nameImg) {
        int i;
        for (i = 0; i < arrImgByName.length; i++) {
            if (arrImgByName[i].nameImg.equals(nameImg)) {
                return arrImgByName[i];
            }
        }
        return null;
    }
    
}
