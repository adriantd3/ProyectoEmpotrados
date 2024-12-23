package ssedm.lcc.example.newdictionarywithddbb;

import android.provider.BaseColumns;

public final class DictContract {
    private DictContract() {}

    public static abstract class DictEntry implements BaseColumns {
        public static final String TABLE_NAME = "Diccionario";
        public static final String COLUMN_NAME_KEY = "clave";
        public static final String COLUMN_NAME_VAL = "valor";
    }
}
