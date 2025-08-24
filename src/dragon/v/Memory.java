package dragon.v;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memory {

    public int userId = -1;
    public int lastlogout = 0;
    public int nFreeWish = 1;
    public long timeWaitRoadSnake = -1;

    public Memory(int userId) {
        this.userId = userId;
    }

    private static final Map<Integer, Memory> MEMORYS = new ConcurrentHashMap<>();

    public static Memory get(int userId) {
        return MEMORYS.computeIfAbsent(userId, Memory::new);
    }
}
