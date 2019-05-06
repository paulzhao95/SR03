
package model;

import java.io.Serializable;
import java.sql.Timestamp;


public class User implements Serializable {
    private String email;
    private String password;
    private String name;
    private Boolean status;
    private String company;
    private String tel;
    private Timestamp creatingTime;
    private UserType type;

    public enum UserType {
        Administrator,
        Intern

    }



    public User(String email, String password, String name, Boolean status,String company,String tel,Timestamp creatingTime, UserType userType){
        this.email=email;
        this.password=password;
        this.name=name;
        this.status=status;
        this.company=company;
        this.tel=tel;
        this.creatingTime = creatingTime;
        this.type = userType;
    }

    public User() {
        this.email="";
        this.password="";
        this.name="";
        this.status=false;
        this.company="";
        this.tel="";
        this.creatingTime = null;
        this.type = UserType.Intern;
    }

    public void setEmail(String login){
        this.email = login;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setPassword(String pwd){
        this.password = pwd;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setStatus(Boolean status){
        this.status = status;
    }
    
    public Boolean getStatus(){
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
    
    public void setCreatingTime(Timestamp date){
        this.creatingTime = date;
    }

    public Timestamp getCreatingTime(){
        return this.creatingTime;
    }

    public void setType(UserType userType) { this.type = userType;}

    public UserType getType() {
        return type;
    }

    public String toString(){
        return "User{" + "Name= "+ name+ ", login= "+email+", key= "+password + ", type = "+ (this.type==UserType.Intern ? "Intern":"Administrator");
    }
}
