package it.unipi.dii.inginf.lsdb.coogether.bean;

import java.util.Date;

public class Comment {
    private String commentId;
    private String authorId;
    private String authorName;
    private int rating;
    private Date datePublished;
    private String text;

    public Comment(){
    }

    public Comment( String id, String text, String name){
        this.commentId = id;
        this.text = text;
        this.authorId = name;
    }

    public Comment(String id, String authorId, String authorName, int stars, Date pub, String text){
        this.commentId=id;
        this.authorId=authorId;
        this.authorName=authorName;
        this.rating=stars;
        this.datePublished=pub;
        this.text=text;
    }

    public String getCommentId(){
        return commentId;
    }

    public String getAuthorId(){
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

    public void setCommentId(String id){
        this.commentId=id;
    }

    public void setAuthorId(String id){
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
