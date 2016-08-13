package net.iwin247.calendar.model;

/**
 * Created by user on 2016-08-11.
 */
public class Login {
    private String Email;
    private String passwd;
    private String Token;

    public Login(String Email, String passwd, String Token){
        this.Email = Email;
        this.passwd = passwd;
        this.Token = Token;
    }
}
