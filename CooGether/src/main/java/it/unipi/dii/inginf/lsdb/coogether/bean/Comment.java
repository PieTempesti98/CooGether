package it.unipi.dii.inginf.lsdb.coogether.bean;

import java.util.Date;

public class Comment {
    private int commentId;
    private int authorId;
    private String authorName;
    private int rating;
    private Date datePublished;
    private String text;

    public Comment(){
    }

    public Comment(int id, String text, String name){
        this.commentId = id;
        this.text = text;
        this.authorName = name;
    }

    public Comment(int id, int authorId, String authorName, int stars, Date pub, String text){
        this.commentId=id;
        this.authorId=authorId;
        this.authorName=authorName;
        this.rating=stars;
        this.datePublished=pub;
        this.text=text;
    }

    public int getCommentId(){
        return commentId;
    }

    public int getAuthorId(){
        return authorId;
    }

    public String getAuthorName(){
        return authorName;
    }

    public String getText(){
        return text;
    }

    public int getRating(){
        return rating;
    }

    public Date getDatePublished(){
        return datePublished;
    }

    public void setCommentId(int id){
        this.commentId=id;
    }

    public void setAuthorId(int id){
        this.authorId= id;
    }

    public void setAuthorName(String name){
        this.authorName=name;
    }

    public void setDatePublished(Date pub){
        this.datePublished=pub;
    }

    public void setRating(int stars){
        this.rating=stars;
    }

    public void setText(String text){
        this.text=text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "authorUsername='" + authorName + '\'' +
                ", rating='" + rating + '\'' +
                ", text='" + text + '\'' +
                ", creationTime=" + datePublished +
                '}';
    }
}
