package com.voitenko.diploma.mobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.api.SightseeingAPI;
import com.voitenko.diploma.mobile.api.SightseeingContentAPI;
import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.SightseeingContent;
import com.voitenko.diploma.mobile.service.DataConverter;
import com.voitenko.diploma.mobile.service.ServiceGenerator;
import com.voitenko.diploma.mobile.service.UserManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RPLoginActivity extends Activity {

    public static final String SCAN_RESULT = "SCAN_RESULT";
    public static final String SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";

    private CallbackManager mCallbackManager;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            if (accessToken != null) {
                Intent main = new Intent(RPLoginActivity.this, CountriesActivity.class);
                main.putExtra("login", "noapp");
                startActivity(main);
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);


        LoginButton mFbLoginButton = (LoginButton) findViewById(R.id.login_button);

        if (UserManager.isProfileValid(this)) {
            //mSearchCountriesButton.setVisibility(View.GONE);
        } else {
            //mSearchCountriesButton.setVisibility(View.VISIBLE);
        }

        mFbLoginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString("email");
                                    File file = new File(ConstantsContainer.FILEPATH);
                                    try {
                                        DataConverter.writeFile(email, file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mCallbackManager.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra(SCAN_RESULT);
                final int contentId = Integer.parseInt(contents.split("/")[6]);
                final SightseeingContentAPI sightseeingContentAPI = ServiceGenerator.createService(SightseeingContentAPI.class, ConstantsContainer.ENDPOINT);
                sightseeingContentAPI.getAll(
                        new Callback<ArrayList<SightseeingContent>>() {
                            @Override
                            public void success(ArrayList<SightseeingContent> result, Response response) {
                                Integer sightSeeingId;
                                for (SightseeingContent sightseeingContent : result) {
                                    if (sightseeingContent.getContent().getId() == contentId) {
                                        sightSeeingId = sightseeingContent.getSightseeing().getId();
                                        Intent intent = new Intent(RPLoginActivity.this, SightseeingDetailsActivity.class);
                                        intent.putExtra(ConstantsContainer.SIGHTSEEING_ID, sightSeeingId.toString());
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("!!!RETROFIT_ERROR!!!!!", error.getMessage());
                            }
                        }

                );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getHashInfo() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.voitenko.dutyhelper",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void searchCountries(View v) {
        Intent intent = new Intent(this, CountriesActivity.class);
        startActivity(intent);
    }

    private static AlertDialog showDialog(final Activity activity, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

}
