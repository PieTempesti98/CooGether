package it.unipi.lmmsdb.coogether.coogetherapp.pojo;

import java.util.Date;

public class CommentPojo {
    private int reviewId;
    private int authorId;
    private int rating;
    private DatePojo dateSubmitted;
    private String authorName;
    private String comment;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public DatePojo getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(DatePojo dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
