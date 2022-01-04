package it.unipi.dii.inginf.lsdb.coogether.bean;

public class User {
    private String userId;
    private String username;
    private String fullname;
    private String email;
    private String password;

    public User(){
    }

    public User(String id, String name ){
        this.userId= id;
        this.username= name;
    }

    public User(String id, String name, String full, String email, String pass){
        this.userId=id;
        this.username=name;
        this.fullname=full;
        this.email=email;
        this.password=pass;
    }

    public String getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getFullname(){ return fullname;}

    public String getEmail(){ return email;}

    public String getPassword(){ return password;}

    public void setUserId(String id){
        this.userId=id;
    }

    public void setUsername(String name){
        this.username=name;
    }

    public void setFullname(String name){ this.fullname=name;}

    public void setEmail(String email){this.email=email;}

    public void setPassword(String pass){this.password=pass;}
}
