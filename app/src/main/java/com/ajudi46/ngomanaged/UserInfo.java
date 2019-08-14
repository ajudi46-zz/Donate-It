package com.ajudi46.ngomanaged;

public class UserInfo {
    String emaildid;
    String flarecount;


    UserInfo(){

    }

    public UserInfo(String emaildid,String flarecount) {
        this.emaildid = emaildid;
        this.flarecount = flarecount;
    }

    public String getEmaildid() {
        return emaildid;
    }


    public String getFlarecount() { return flarecount; }
};

