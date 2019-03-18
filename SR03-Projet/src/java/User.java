
package java;

public class User {
    private String email;
    private String password;
    private String name;
    private String status;
    private String company;
    private String tel;
    private String creatng_time;
    
    
    public User(){
    }
    
    public User(String email, String password, String name, String status,String company,String tel,String creatng_time){
        this.email=email;
        this.password=password;
        this.name=name;
        this.status=status;
        this.company=company;
        this.tel=tel;
        this.creatng_time=creatng_time;
    }
    
    public void setLogin(String login){
        this.email = login;
    }
    
    public String getLogin(){
        return this.email;
    }
    
    public void setPwd(String pwd){
        this.password = pwd;
    }
    
    public String getPwd(){
        return this.password;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public String getCompany(){
        return this.company;
    }
    
    public void setTel(String tel){
        this.tel = tel;
    }
    
    public String getTel(){
        return this.tel;
    }
    
    public void setCreateDate(String date){
        this.creatng_time = date;
    }
    
    public String getCreateDate(){
        return this.creatng_time;
    }
    
    public String toString(){
        return "User{" + "Name= "+ name+ ", login= "+email+", key= "+password;
    }
}
