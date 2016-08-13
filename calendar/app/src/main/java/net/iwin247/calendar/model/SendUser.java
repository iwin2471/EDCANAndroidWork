package net.iwin247.calendar.model;

/**
 * Created by user on 2016-08-10.
 */
public class SendUser {
    private String Email;
    private String passwd;
    private String Token;

    public SendUser(String Email, String passwd, String Token){
        this.Email = Email;
        this.passwd = passwd;
        this.Token = Token;
    }
}


