package net.iwin247.calendar.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import net.iwin247.calendar.R;
import net.iwin247.calendar.model.SendUser;
import net.iwin247.calendar.model.Login;
import net.iwin247.calendar.utils.GCMRegistrationIntentService;
import net.iwin247.calendar.utils.NetworkInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button Login, reg;
    EditText Email, passwd;
    TextView error;
    String Token;
    String email, pw;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])"); // 4자리 ~ 16자리까지 가능

    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    Call<SendUser> SendUserCall;
    Call<Login> LoginCall;

    public static final String API_URL = "http://iwin247.net";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Service setup
    NetworkInterface service = retrofit.create(NetworkInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = (Button) findViewById(R.id.Login);
        reg = (Button) findViewById(R.id.Reg);
        Email = (EditText) findViewById(R.id.Email);
        passwd = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.error);

        SharedPreferences prefs = getSharedPreferences("test" , 0);
        String isLogin = prefs.getString("isLogin","");
        Toast.makeText(MainActivity.this, isLogin, Toast.LENGTH_SHORT).show();
        if(isLogin.equals("yes")){
            finish();
            startActivity(new Intent(MainActivity.this, calender.class));
        }

        Login.setOnClickListener(this);
        reg.setOnClickListener(this);

        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                     Token = intent.getStringExtra("token");

                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
        email = Email.getText().toString();
        pw = passwd.getText().toString();

        switch (view.getId()) {
            case R.id.Login:
                LoginCall = service.Login(email, pw, Token);
                LoginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        switch (response.code()) {
                            case 300:
                                error.setText("");
                                finish();
                                SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("email", email);
                                editor.commit();

                                Toast.makeText(MainActivity.this, "성공적으로 로그인되었습니다", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainActivity.this, calender.class));
                                break;
                            case 302:
                                error.setText("아이디나 비번을 정확히 입력해주세요!");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.wtf("wtf", t.toString());
                    }
                });
                break;
            case R.id.Reg:
                Matcher Emailmatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(Email.getText());
                Matcher  passwdMatcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(passwd.getText());

                if (!Emailmatcher.find()){
                    error.setText("이메일 형식을 맞춰주세요");
                }else if(!passwdMatcher.find()){
                    error.setText("비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요");
                } else {
                    error.setText("");
                    SendUserCall = service.SendUser(Email.getText()+"", passwd.getText()+"", Token);
                    SendUserCall.enqueue(new Callback<SendUser>() {
                        @Override
                        public void onResponse(Call<SendUser> call, Response<SendUser> response) {
                            switch (response.code()) {
                                case 300:
                                    finish();
                                    startActivity(new Intent(MainActivity.this, calender.class));
                                    break;
                                case 302:
                                    error.setText("아이디나 비번을 정확히 입력해주세요!");
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<SendUser> call, Throwable t) {
                            Log.wtf("wtf", t.toString());
                        }
                    });
                }
                //startActivity(new Intent(MainActivity.this, calender.class));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://net.iwin247.calendar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://net.iwin247.calendar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}