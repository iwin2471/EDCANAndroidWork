package net.iwin247.calendar.utils;

import net.iwin247.calendar.model.Login;
import net.iwin247.calendar.model.SendUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by KOHA_DESKTOP on 2016. 6. 29..
 */
public interface NetworkInterface {
    @POST("/users/reg")
    @FormUrlEncoded
    Call<SendUser> SendUser(@Field("Email") String Email, @Field("pw") String passwd, @Field("Token") String Token);

    @POST("/users/login")
    @FormUrlEncoded
    Call<Login> Login(@Field("Email") String Email, @Field("pw") String passwd, @Field("Token") String Token);
}



