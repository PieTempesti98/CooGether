package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class FollowDTO {
    @JsonProperty("userId")
    private int userId;
    private ArrayList<Integer> follow;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getFollow() {
        return follow;
    }

    public void setFollow(ArrayList<Integer> follow) {
        this.follow = follow;
    }
}
