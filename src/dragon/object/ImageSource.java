package dragon.object;

import dragon.server.Dragon;
import dragon.server.mResources;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author TGDD
 */
public class ImageSource {
    
    public static HashMap<Byte, String[]> imageOriginals = new HashMap();
    public static HashMap<String, byte[]> imgData = new HashMap<>();
    
    public static void initImg() {
        System.out.println("Load ImageSource");
        Iterator<Byte> itr = imageOriginals.keySet().iterator();
        while (itr.hasNext()) {
            byte zoom = itr.next();
            String[] arrOriginal = ImageSource.imageOriginals.get(zoom);
            if (arrOriginal != null) {
                for (int i = 0; i < arrOriginal.length; i++) {
                    String original = arrOriginal[i];
                    byte[] data = Dragon.getFile(String.format(mResources.PATH_IMG, zoom, original));
                    imgData.put(String.format(mResources.PATH_IMG, zoom, original), data);
                }
            }
        }
    }
    
}
