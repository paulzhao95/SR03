
package model;

import java.sql.Timestamp;

public class User {
    private String email;
    private String password;
    private String name;
    private Boolean status;
    private String company;
    private String tel;
    private Timestamp creatngTime;
    private String type;
    

    public User(String email, String password, String name, Boolean status,String company,String tel,Timestamp creatngTime){
        this.email=email;
        this.password=password;
        this.name=name;
        this.status=status;
        this.company=company;
        this.tel=tel;
        this.creatngTime = creatngTime;
    }

    public User() {
        this.email="";
        this.password="";
        this.name="";
        this.status=false;
        this.company="";
        this.tel="";
        this.creatngTime = null;
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
        this.creatngTime = date;
    }

    public Timestamp getCreatingTime(){
        return this.creatngTime;
    }

    public void setType(String type) { this.type = type;}

    public String getType() {
        return type;
    }


    public String toString(){
        return "User{" + "Name= "+ name+ ", login= "+email+", key= "+password;
    }
}
