package app.roundtable.nepal.activity.database;

/**
 * Created by afif on 23/6/15.
 */
public class Tables {



    public interface RTNTables{

        public static final String RTN_TABLES_TABLE = "rtn_tables";

        public static final String TABLE_ID = "table_id";
        public static final String TABLE_NAME = "table_name";
        public static final String TABLE_CODE = "table_code";
        public static final String TABLE_DESCRIPTION = "table_description";
        public static final String TABLE_BIG_URL = "table_big_url";
        public static final String TABLE_THUMB_URL = "table_thumb_url";
        public static final String TABLE_MEMBERS_COUNT = "table_members";

        public static final String COLUMNS = TABLE_ID + " text not null, "+
                                             TABLE_NAME + " text not null, "+
                                            TABLE_CODE + " text not null, "+
                                            TABLE_DESCRIPTION + " text not null, "+
                                            TABLE_BIG_URL + " text not null, "+
                                            TABLE_THUMB_URL + " text not null, "+
                                            TABLE_MEMBERS_COUNT + " text not null";

        public static final String RTN_TABLE_SCHEMA = "CREATE TABLE "+RTN_TABLES_TABLE +"("+COLUMNS+")";


    }


    public interface Events{

        public static final String EVENTS_TABLE = "events_table";

        public static final String EVENT_ID = "event_id";
        public static final String EVENT_TYPE = "event_type";
        public static final String EVENT_NAME = "event_name";
        public static final String EVENT_DATE = "event_date";
        public static final String EVENT_TIME = "event_time";
        public static final String EVENT_VENUE = "event_venue";
        public static final String IS_SPOUSE = "is_spouse";
        public static final String IS_CHILDREN = "is_children";
        public static final String TABLE_COUNT = "tables_count";
        public static final String EVENT_BIG_IMAGE = "event_big_url";
        public static final String EVENT_THUMB_IMAGE = "event_thumb_url";
        public static final String CREATED_AT = "created_on";
        public static final String MEMBER_CREATED = "member_created";

        public static final String COLUMNS = EVENT_ID + " text not null, "+
                                            EVENT_TYPE + " text not null, "+
                                            EVENT_NAME + " text not null, "+
                                            EVENT_DATE + " text not null, "+
                                            EVENT_TIME + " text not null, "+
                                            EVENT_VENUE+ " text not null, "+
                                            IS_SPOUSE + " text not null, "+
                                            IS_CHILDREN + " text not null, "+
                                            TABLE_COUNT + " text not null, "+
                                            EVENT_BIG_IMAGE + " text, " + EVENT_THUMB_IMAGE + " text, "+
                                            CREATED_AT + " text not null, "+
                                            MEMBER_CREATED + " text";

        public static final String EVENTS_SCHEMA = "CREATE TABLE "+EVENTS_TABLE + "("+COLUMNS+")";
    }



    public interface News{

        public static final String NEWS_TABLE = "news_table";

        public static final String NEWS_ID = "news_id";
        public static final String NEWS_HEADLINE = "news_headline";
        public static final String NEWS_DESCRIPTION = "news_description";
        public static final String NEWS_BIG_URL = "big_url";
        public static final String NEWS_THUMB_URL = "thumb_url";
        public static final String NEWS_MEMBER_ID = "member_id";
        public static final String NEWS_DATE = "news_date";

        public static final String COLUMN = NEWS_ID + " text not null, "+
                                            NEWS_DESCRIPTION + " text not null, "+
                                            NEWS_HEADLINE + " text not null, "+
                                            NEWS_BIG_URL + " text not null, "+
                                            NEWS_THUMB_URL + " text not null, "+
                                            NEWS_MEMBER_ID + " text not null, "+
                                            NEWS_DATE + " text not null";

        public static final String NEWS_TABLE_SCHEMA = "CREATE TABLE "+NEWS_TABLE + "("+COLUMN+")";

    }


    public interface Members{

        public static final String MEMBERS_TABLE = "members_table";

        public static final String MEMBER_ID = "member_id";
        public static final String MEMBER_TABLE_ID = "table_id";
        public static final String MEMBER_FIRST_NAME = "fname";
        public static final String MEMBER_LAST_NAME = "lname";
        public static final String GENDER = "gender";
        public static final String MOBILE = "mobile";
        public static final String EMAIL = "email";
        public static final String BLOOD_GROUP = "blood_group";
        public static final String SPOUSE_NAME = "spouse_name";
        public static final String DATE_OF_BIRTH = "dob";
        public static final String SPOUSE_DATE_OF_BIRTH = "spouse_dob";
        public static final String ANNIVERSARY_DATE = "anniversary_date";
        public static final String IMAGE_THUMB_URL = "image_thumb_url";
        public static final String IMAGE_BIG_URL = "image_big_url";
        public static final String RESIDENCE_PHONE = "res_phone";
        public static final String OFFICE_PHONE = "office_phone";
        public static final String DESIGNATION = "designation";
        public static final String RESIDENCE_CITY = "res_city";
        public static final String OFFICE_CITY = "office_city";
        public static final String STATE = "state";


        public static final String COLUMN = MEMBER_ID + " text not null, "+
                                            MEMBER_TABLE_ID + " text not null, "+
                                            MEMBER_FIRST_NAME + " text not null, "+
                                            MEMBER_LAST_NAME + " text not null, "+
                                            GENDER+ " text not null, "+
                                            MOBILE + " text, "+
                                            EMAIL + " text, "+
                                            BLOOD_GROUP + " text , "+
                                            SPOUSE_NAME + " text, "+
                                            DATE_OF_BIRTH + " text, "+
                                            SPOUSE_DATE_OF_BIRTH + " text, "+
                                            ANNIVERSARY_DATE + " text, "+
                                            IMAGE_BIG_URL + " text, "+
                                            IMAGE_THUMB_URL + " text, "+
                                            RESIDENCE_PHONE + " text, "+
                                            OFFICE_PHONE + " text, "+
                                            DESIGNATION + " text, "+
                                            RESIDENCE_CITY + " text, "+
                                            OFFICE_CITY + " text, "+
                                            STATE + " text";

        public static final String MEMBERS_TABLE_SCHEMA = "CREATE TABLE "+MEMBERS_TABLE +"(" + COLUMN + ")";

    }


    public interface Favorites{

        public static final String FAVORITE_TABLE = "favorites";

        public static final String BRAND_ID = "brand_id";
        public static final String BRAND_NAME = "brand_name";
        public static final String BRAND_LOGO_URL = "image_url";
        public static final String BRAND_WEBSITE_URL = "website_url";


        public static final String COLUMN = BRAND_ID + " text not null, "+
                                            BRAND_NAME + " text not null, "+
                                            BRAND_LOGO_URL + " text not null, "+
                                            BRAND_WEBSITE_URL + " text not null";

        public static final String FAVORITE_TABLE_SCHEME = "CREATE TABLE "+FAVORITE_TABLE + "("+COLUMN+")";


    }



}
