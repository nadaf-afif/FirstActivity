package app.roundtable.nepal.activity.network;

/**
 * Created by afif on 20/6/15.
 */
public class ApiUrls {

     //public static final String BASE_URL_PATH = "http://roundtablenepal.esy.es";
     public static final String BASE_URL_PATH = "http://roundtablenepal.org/app";

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

    // get conveners list API PATH
    public static final String CONVENERS_LIST_API_PATH = BASE_URL_PATH + "/api/conveners/get_all";

    // search member API PATH
    public static final String SEARCH_MEMBER_API_PATH = BASE_URL_PATH + "/api/member/search";

    // edit profile api path
    public static final String EDIT_PROFILE_API_PATH = BASE_URL_PATH + "/api/member/edit_profile";

    // update gallery upload to server
    public static final String GALLERY_UPDATE_API_PATH = BASE_URL_PATH + "/api/gallery/post_update";

    // update RSVP status from event
    public static final String RSVP_UPDATE_API_PATH = BASE_URL_PATH + "/api/event/rsvp";

    // get RSVP responses of members
    public static final String RSVP_RESPONSE_API_PATH = BASE_URL_PATH + "/api/event/get_rsvp";

    // get profile info
    public static final String PROFILE_INFO_API = BASE_URL_PATH + "/api/member/get_profile";

}
