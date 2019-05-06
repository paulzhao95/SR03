package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Administrator extends User implements Serializable {

    public Administrator(String email, String password, String name, Boolean status, String company, String tel, Timestamp creatng_time) {
            super(email, password,name,status,company,tel,creatng_time, UserType.Administrator);
    }

    public Administrator() {
        super();
        super.setType(UserType.Administrator);
    }

    @Override
    public String toString(){
        return super.toString()+"type= Administrator";
    }
}
