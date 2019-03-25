package model;

import java.sql.Timestamp;

public class Administrator extends User{

    public Administrator(String email, String password, String name, Boolean status, String company, String tel, Timestamp creatng_time) {
            super(email, password,name,status,company,tel,creatng_time);
            this.setType("administrator");
    }

    public Administrator() {
        super();
        this.setType("administrator");
    }

    @Override
    public String toString(){
        return super.toString()+"type= "+ this.getType();
    }
}
