package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Intern extends User implements Serializable {

    public Intern(String email, String password, String name, Boolean status, String company, String tel, Timestamp creatng_time) {
            super(email, password,name,status,company,tel,creatng_time);
            this.setType("Intern");
    }

    public Intern() {
        super();
        this.setType("Intern");

    }

    @Override
    public String toString(){
        return super.toString()+"type= "+ this.getType();
    }
}
