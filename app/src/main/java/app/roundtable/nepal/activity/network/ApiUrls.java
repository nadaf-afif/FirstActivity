package app.roundtable.nepal.activity.network;

/**
 * Created by afif on 20/6/15.
 */
public class ApiUrls {

    public static final String BASE_URL_PATH = "http://roundtablenepal.esy.es";


    // login api
    public static final String LOGIN_API_PATH = BASE_URL_PATH + "/api/login";

    // request otp api
    public static final String REQUEST_API_PATH = BASE_URL_PATH + "/api/request_otp";

    // request access api path
    public static final String REQUEST_ACCESS_API_PATH = BASE_URL_PATH + "/api/access_request";

    // get all RTN tables api path
    public static final String RTN_TABLES_API_PATH = BASE_URL_PATH + "/api/table/get_all";


    // get events api path
    public static final String ALL_EVENTS_API_PATH = BASE_URL_PATH + "/api/event/get_all";

    // get meetings api path
    public static final String ALL_MEETINGS_API_PATH = BASE_URL_PATH + "/api/meeting/get_all";

    // get news api path
    public static final String ALL_NEWS_API_PATH = BASE_URL_PATH + "/api/news/get_all";


    public static final String MEMBERS_BY_TABLE_API_PATH = BASE_URL_PATH + "/api/table/";

    // add new event API path
    public static final String ADD_NEW_EVENT_API_PATH = BASE_URL_PATH + "/api/event/create";

    // add new meeting API path
    public static final String ADD_NEW_MEETING_API_PATH = BASE_URL_PATH + "/api/meeting/create";

    // add new news API path
    public static final String ADD_NEWS_API_PATH = BASE_URL_PATH + "/api/news/create";

    // get favorites list APi PAth
    public static final String FAVORITES_LIST_API_PATH = BASE_URL_PATH + "/api/favorites/get_all";

}
