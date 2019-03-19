package model;

public class Intern extends User{

    public Intern(String email, String password, String name, String status,String company,String tel,String creatng_time) {
            super(email, password,name,status,company,tel,creatng_time);
            this.setType("Intern");
    }
    
    @Override
    public String toString(){
        return super.toString()+"type= "+ this.getType();
    }
}
