package com.ccos.contract.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer userId;
    private String uname;
    private String upwd;
    private String nick;
    private String head;
    private String mood;

    public void setUserId(Integer userId){
        this.userId=userId;
    }
}
