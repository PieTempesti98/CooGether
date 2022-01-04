package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private int commentId;
    private int authorId;
    private String authorName;
    private int recipeId;
    private Date dateSubmitted;
    private Date dateModified;
    private String review;

    public int getReviewId() {
        return commentId;
    }

    public void setCommentId(int reviewId) {
        this.commentId = reviewId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            this.dateSubmitted = sdf.parse(dateSubmitted);
        } catch (ParseException e) {
            e.printStackTrace();
            this.dateSubmitted = null;
        }
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public void setDateModified(String dateModified){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            this.dateModified = sdf.parse(dateModified);
        } catch (ParseException e) {
            e.printStackTrace();
            this.dateModified = null;
        }
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
