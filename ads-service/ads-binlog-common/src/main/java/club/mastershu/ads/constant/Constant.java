package club.mastershu.ads.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    private static final String DB_NAME = "ads_data";
    public static class PLAN_TABLE_INFO {
        public static final String TABLE_NAME = "plan";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
    }
    public static class CREATIVE_TABLE_INFO {
        public static final String TABLE_NAME = "creative";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MATERIAL_TYPE = "material_type";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_AUDIT_STATUS = "audit_status";
        public static final String COLUMN_URL = "url";
    }

    public static class UNIT_TABLE_INFO {
        public static final String TABLE_NAME = "unit";
        public static final String COLUMN_ID = "id";

        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_POSITION_TYPE = "position_type";
        public static final String COLUMN_PLAN_ID = "plan_id";
    }

    public static class CREATIVE_UNIT_TABLE_INFO {

        public static final String TABLE_NAME = "creative_unit";
        public static final String COLUMN_CREATIVE_ID = "creative_id";
        public static final String COLUMN_UNIT_ID = "unit_id";
    }

    public static class UNIT_DISTRICT_TABLE_INFO {
        public static final String TABLE_NAME = "unit_district";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_PROVINCE = "province";
        public static final String COLUMN_CITY = "city";
    }

    public static class UNIT_IT_TABLE_INFO {
        public static final String TABLE_NAME = "unit_it";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_TAG = "tag";
    }

    public static class UNIT_KEYWORD_TABLE_INFO {
        public static final String TABLE_NAME = "unit_keyword";

        public static final String COLUMN_UNIT_ID = "unit_id";
        public static final String COLUMN_KEYWORD = "keyword";
    }

    public static Map<String, String> tableToDb;
    static {
        tableToDb = new HashMap<>();
        tableToDb.put(PLAN_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(CREATIVE_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(CREATIVE_UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(UNIT_DISTRICT_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(UNIT_IT_TABLE_INFO.TABLE_NAME, DB_NAME);
        tableToDb.put(UNIT_KEYWORD_TABLE_INFO.TABLE_NAME, DB_NAME);
    }
}
