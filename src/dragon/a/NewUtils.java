package dragon.a;

import java.util.Calendar;

/**
 *
 * @author Admin
 */
public class NewUtils {

    public static int hoursNow() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
