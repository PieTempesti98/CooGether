package it.unipi.dii.inginf.lsdb.coogether.bean;

public class User {
    private int userId;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private  int followers;
    private int following;

    public User(){
    }

    public User(int id, String name ){
        this.userId= id;
        this.username= name;
    }

    public User(int id, String name, int followers ){
        this.userId= id;
        this.username= name;
        this.followers=followers;
    }

    public User(int id, String name, String full, String email, String pass, int followers, int following){
        this.userId=id;
        this.username=name;
        this.fullname=full;
        this.email=email;
        this.password=pass;
        this.followers=followers;
        this.following=following;
    }

    public int getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getFullname(){ return fullname;}

    public String getEmail(){ return email;}

    public String getPassword(){ return password;}

    public int getFollowers(){
        return followers;
    }

    public int getFollowing(){
        return following;
    }

    public void setUserId(int id){
        this.userId=id;
    }

    public void setUsername(String name){
        this.username=name;
    }

    public void setFullname(String name){ this.fullname=name;}

    public void setEmail(String email){this.email=email;}

    public void setPassword(String pass){this.password=pass;}

    public void setFollowers(int foll){
        this.followers=foll;
    }

    public void setFollowing(int foll){
        this.following=foll;
    }
}
