package ssedm.lcc.example.newdictionarywithddbb;

import java.util.HashMap;

/**
 * Created by ruben on 24/11/17.
 */

public class SingletonMap extends HashMap<String, Object> {

    private static class SingletonHolder {
        private static final SingletonMap ourInstance = new SingletonMap();
    }

    public static SingletonMap getInstance() {
        return SingletonHolder.ourInstance;
    }

    private SingletonMap() {

    }
}
