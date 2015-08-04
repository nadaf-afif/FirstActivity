package app.roundtable.nepal.activity.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.sql.Statement;

import app.roundtable.nepal.R;

/**
 * Created by afif on 20/6/15.
 */
public class ApplicationPreferences {

    private static final String NAVIGATION_INDEX = "index_tab";
    private static final String LAST_NAME = "last_name";
    private static final String GENDER = "gender";
    private static final String MOBILE = "mobile";
    private static final String EMAIL = "email";
    private static final String BLOOD_GROUP = "blood_group";
    private static final String SPOUSE_NAME = "spouse_name";
    private static final String DATE_OF_BIRTH = "dob";
    private static final String SPOUSE_DOB = "spouse_dob";
    private static final String RESIDENCE_PHONE = "res_phone";
    private static final String OFFICE_PHONE = "office_phone";
    private static final String DESIGNATION = "designation";
    private static final String RESIDENCE_CITY = "res_city";
    private static final String OFFICE_CITY = "office_city";
    private static final String STATE = "state";
    private static final String ANNIVERSARY_DATE =  "anniversary_date";
    private static final String TABLE_ID = "table_id";
    private static final String TABLE_CODE = "table_code";
    private static final String TABLE_NAME = "table_name";
    private static final String COMPANY = "company";
    private static final String ADDRESS = "address";

    private Context mContext;
    private SharedPreferences mSharedPreferences;


    public static final String GCM_REGISTRATION_ID = "gcm_id";
    public static final String IS_LOGGED_IN = "is_login";
    public static final String IS_REGISTERED = "device_registered";
    public static final String USER_NAME = "user_name";
    public static final String USER_PROFILE_IMAGE = "image_profile";
    public static final String USER_ID = "user_id";
    private String spouseDOB;
    private String userResidencePhone;
    private String userDesignation;

    public ApplicationPreferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.preference_name),Context.MODE_PRIVATE);
    }


    public String getGcmRegistrationId(){

        //default id to be removed
        return mSharedPreferences.getString(GCM_REGISTRATION_ID,"");

    }


    public void setGcmRegistrationId(String gcmRegistrationId){

        mSharedPreferences.edit().putString(GCM_REGISTRATION_ID, gcmRegistrationId).commit();
    }



    public void setIsLoggedIn(boolean isLoggedIn){

        mSharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).commit();
    }

    public boolean IsLoggedIn(){ return mSharedPreferences.getBoolean(IS_LOGGED_IN, false); };

    public boolean isDeviceRegistered() {
        return mSharedPreferences.getBoolean(IS_REGISTERED, false);
    }
    
    public void setIsRegistered(boolean value){

        mSharedPreferences.edit().putBoolean(IS_REGISTERED,value).commit();
    }


    public int getNavigationTabIndex(){

        return mSharedPreferences.getInt(NAVIGATION_INDEX,0);
    }

    public void setNavigationIndex( int value){

        mSharedPreferences.edit().putInt(NAVIGATION_INDEX,value).commit();
    }

    public String getUserName() {
        return mSharedPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String name){

        mSharedPreferences.edit().putString(USER_NAME, name).commit();
    }

    public String getUserProfileImage() {

        return mSharedPreferences.getString(USER_PROFILE_IMAGE, "");
    }

    public void setUserProfileImage(String imagePath){

        mSharedPreferences.edit().putString(USER_PROFILE_IMAGE, imagePath).commit();
    }

    public void setUserId(String member_id) {
        mSharedPreferences.edit().putString(USER_ID, member_id).commit();
    }

    public String getUserId(){
        return mSharedPreferences.getString(USER_ID,"");
    }

    public void setUserLastName(String lname) {
        mSharedPreferences.edit().putString(LAST_NAME, lname).commit();
    }

    public String getUserLastName(){
        return mSharedPreferences.getString(LAST_NAME,"");
    }

    public void setUserGender(String gender) {
        mSharedPreferences.edit().putString(GENDER, gender).commit();
    }

    public String getUserGender(){
        return mSharedPreferences.getString(GENDER,"");
    }

    public void setUserMobile(String mobile) {
        mSharedPreferences.edit().putString(MOBILE, mobile).commit();
    }

    public String getMobile(){

        return mSharedPreferences.getString(MOBILE,"");
    }

    public void setUserEmail(String email) {
        mSharedPreferences.edit().putString(EMAIL,email).commit();
    }

    public String getUserEmail(){
        return mSharedPreferences.getString(EMAIL,"");
    }

    public void setUserBloodGroup(String blood_group) {
        mSharedPreferences.edit().putString(BLOOD_GROUP,blood_group).commit();
    }

    public String getUserBloodGroup(){
        return mSharedPreferences.getString(BLOOD_GROUP,"");
    }

    public void setSpouseName(String spouse_name) {
        mSharedPreferences.edit().putString(SPOUSE_NAME, spouse_name).commit();
    }

    public String getSpouseName(){
        return mSharedPreferences.getString(SPOUSE_NAME,"");
    }

    public void setUserDOB(String dob) {
        mSharedPreferences.edit().putString(DATE_OF_BIRTH, dob).commit();
    }

    public String getUserDOB(){
        return mSharedPreferences.getString(DATE_OF_BIRTH,"");
    }

    public void setSpouseDOB(String spouseDOB) {
        mSharedPreferences.edit().putString(SPOUSE_DOB,spouseDOB).commit();
    }

    public String getSpouseDOB(){
        return mSharedPreferences.getString(SPOUSE_DOB,"");
    }

    public void setUserResidencePhone(String userResidencePhone) {
        mSharedPreferences.edit().putString(RESIDENCE_PHONE, userResidencePhone).commit();
    }

    public String getUserResidencePhone(){
        return mSharedPreferences.getString(RESIDENCE_PHONE,"");
    }

    public void setOfficePhone(String office_phone) {
        mSharedPreferences.edit().putString(OFFICE_PHONE,office_phone).commit();
    }

    public String getOfficePhone(){
        return mSharedPreferences.getString(OFFICE_PHONE,"");
    }

    public void setUserDesignation(String userDesignation) {
        mSharedPreferences.edit().putString(DESIGNATION, userDesignation).commit();
    }

    public String getUserDesignation(){
        return mSharedPreferences.getString(DESIGNATION,"");
    }

    public void setResidenceCity(String res_city) {
        mSharedPreferences.edit().putString(RESIDENCE_CITY,res_city).commit();
    }

    public String getResidenceCity(){
        return mSharedPreferences.getString(RESIDENCE_CITY,"");
    }

    public void setOfficeCity(String office_city) {
        mSharedPreferences.edit().putString(OFFICE_CITY,office_city).commit();
    }

    public String getOfficeCity(){
        return mSharedPreferences.getString(OFFICE_CITY,"");
    }

    public void setUserState(String state) {
        mSharedPreferences.edit().putString(STATE,state).commit();
    }

    public String getUserState(){
        return mSharedPreferences.getString(STATE,"");
    }

    public void setAnniversaryDate(String anniversary_date) {
        mSharedPreferences.edit().putString(ANNIVERSARY_DATE, anniversary_date).commit();
    }

    public String getAnniversaryDate(){
        return mSharedPreferences.getString(ANNIVERSARY_DATE,"");
    }

    public void setTableId(String table_id) {
        mSharedPreferences.edit().putString(TABLE_ID, table_id).commit();
    }

    public String getTableId(){
        return mSharedPreferences.getString(TABLE_ID,"");
    }

    public void setProfileUpdated(boolean value) {

        mSharedPreferences.edit().putBoolean("is_updated", value).commit();
    }

    public boolean getProfileUpdated() {

        return  mSharedPreferences.getBoolean("is_updated", false);
    }

    public void setTableCode(String table_code) {
        mSharedPreferences.edit().putString(TABLE_CODE,table_code).commit();
    }

    public String getTableCode(){
        return mSharedPreferences.getString(TABLE_CODE,"");
    }

    public void setTableName(String table_name){
        mSharedPreferences.edit().putString(TABLE_NAME, table_name).commit();
    }

    public String getTableName(){
        return  mSharedPreferences.getString(TABLE_NAME,"");
    }

    public void setCompany(String company){
        mSharedPreferences.edit().putString(COMPANY,company).commit();
    }

    public String getCompany (){
        return mSharedPreferences.getString(COMPANY,"");
    }

    public void setAddress (String address){
        mSharedPreferences.edit().putString(ADDRESS,address).commit();
    }

    public String getAddress (){
        return mSharedPreferences.getString(ADDRESS,"");
    }
}
