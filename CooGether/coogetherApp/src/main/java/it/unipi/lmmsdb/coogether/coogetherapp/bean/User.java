package it.unipi.lmmsdb.coogether.coogetherapp.bean;

public class User {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private  int followers;
    private int following;
    private int role;

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

    public User(int id, String username, String fullName, String email){
        this.userId= id;
        this.username=username;
        this.fullName=fullName;
        this.email=email;
    }

    public User(int id, String username, String fullName, String password, String email){
        this.userId = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;

    }

    public User(int id, String username, String fullName, String password, String email, int role){
        this.userId = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
    public User(int id, String name, String full, String email, String pass, int followers, int following, int role){
        this.userId=id;
        this.username=name;
        this.fullName=full;
        this.email=email;
        this.password=pass;
        this.followers=followers;
        this.following=following;
        this.role=role;
    }

    public int getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getFullName(){ return fullName;}

    public String getEmail(){ return email;}

    public String getPassword(){ return password;}

    public int getFollowers(){
        return followers;
    }

    public int getFollowing(){
        return following;
    }

    public int getRole(){return role;}

    public void setUserId(int id){
        this.userId=id;
    }

    public void setUsername(String name){
        this.username=name;
    }

    public void setFullName(String name){ this.fullName=name;}

    public void setEmail(String email){this.email=email;}

    public void setPassword(String pass){this.password=pass;}

    public void setFollowers(int foll){
        this.followers=foll;
    }

    public void setFollowing(int foll){
        this.following=foll;
    }

    public void setRole(int role){this.role=role;}
}
