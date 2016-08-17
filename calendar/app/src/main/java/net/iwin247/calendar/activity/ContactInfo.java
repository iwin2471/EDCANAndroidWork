package net.iwin247.calendar.activity;

/**
 * Created by user on 2016-08-14.
 */
public class ContactInfo {
    private String msummary;
    private String mStime;
    private String mEtime;

    ContactInfo (String stime, String etime, String summary){
        msummary = summary;
        mStime = stime;
        mEtime = etime;
    }

    public String getMsummary() {
        return msummary;
    }

    public String getmStime() {
        return mStime;
    }

    public String getmEtime() {
        return mEtime;
    }

    public void setMsummary(String msummary) {
        this.msummary = msummary;
    }

    public void setmStime(String mStime) {
        this.mStime = mStime;
    }

    public void setmEtime(String mEtime) {
        this.mEtime = mEtime;
    }
}
