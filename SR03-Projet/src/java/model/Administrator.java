package model;

public class Administrator extends User{

    public Administrator(String email, String password, String name, String status,String company,String tel,String creatng_time) {
            super(email, password,name,status,company,tel,creatng_time);
            this.setType("Administrator");
    }
    
    @Override
    public String toString(){
        return super.toString()+"type= "+ this.getType();
    }
}
