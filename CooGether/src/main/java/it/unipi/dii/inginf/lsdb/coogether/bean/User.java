package it.unipi.dii.inginf.lsdb.coogether.bean;

public class User {
    private String userId;
    private String username;

    public User(){
    }

    public User(String id, String name ){
        this.userId= id;
        this.username= name;
    }

    public String getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public void setUserId(String id){
        this.userId=id;
    }

    public void setUsername(String name){
        this.username=name;
    }
}
