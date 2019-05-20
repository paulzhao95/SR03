package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Intern extends User implements Serializable {

    public Intern(String email, String password, String name, Boolean status, String company, String tel, Timestamp creatng_time) {
            super(email, password,name,status,company,tel,creatng_time, UserType.Intern);
    }

    public Intern() {
        super();
        this.setType(UserType.Intern);

    }

    public Intern(User user) {
        super(user.getEmail(),user.getPassword(),user.getName(),user.getStatus(),user.getCompany(),user.getTel(), user.getCreatingTime(),user.getType());
        super.setType(UserType.Intern);
    }

    @Override
    public String toString(){
        return super.toString()+"type= "+ this.getType();
    }
}
