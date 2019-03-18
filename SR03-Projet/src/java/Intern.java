package java;

public class Intern extends User{
    private String type = "Intern";
    
    public Intern(String email, String password, String name, String status,String company,String tel,String creatng_time) {
            super(email, password,name,status,company,tel,creatng_time);
    }
    
    @Override
    public String toString(){
        return super.toString()+"type= "+ type;
    }
}
