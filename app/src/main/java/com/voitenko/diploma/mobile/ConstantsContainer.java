package com.voitenko.diploma.mobile;

import android.os.Environment;

public class ConstantsContainer {
    public static final String USER_ID = "userId";
    public static final String DUTY_ID = "dutyId";
    public static final String GROUP_ID = "groupId";
    public static final String ENDPOINT = "http://10.23.13.33:8080/";
    public static final String FILEPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "email.txt";
    public static final String FILEPATH_ID = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "id.txt";


}