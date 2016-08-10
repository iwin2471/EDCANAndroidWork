package net.iwin247.calendar.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.iwin247.calendar.R;
import net.iwin247.calendar.model.SendUser;
import net.iwin247.calendar.model.sendToken;
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

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])"); // 4자리 ~ 16자리까지 가능

    Call<SendUser> SendUserCall;
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

        Login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login:
                startActivity(new Intent(MainActivity.this, calender.class));
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
                    SendUserCall = service.SendUser(Email.getText()+"", passwd.getText()+"");
                    SendUserCall.enqueue(new Callback<SendUser>() {
                        @Override
                        public void onResponse(Call<SendUser> call, Response<SendUser> response) {
                            switch (response.code()) {
                                case 300:
                                    finish();
                                    Toast.makeText(MainActivity.this, "회원가입되었습니다", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, calender.class));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<SendUser> call, Throwable t) {
                            Log.wtf("wtf", "what the fuck");
                        }
                    });
                }
                //startActivity(new Intent(MainActivity.this, calender.class));
                break;

        }
    }
}