package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CommentDTO;
import dto.CommentListDTO;
import entities.Comment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CommentHandler {

    private final static String path = "/comments.json";

    public static CommentListDTO parseComments(){
        InputStream input = CommentListDTO.class.getResourceAsStream(path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(input, CommentListDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Comment> convertToComments(ArrayList<CommentDTO> comments){
        ArrayList<Comment> list = new ArrayList<>();

        for(CommentDTO com: comments){
            Comment c = new Comment();
            c.setCommentId(com.getReviewId());
            c.setRecipeId(com.getRecipeId());
            c.setAuthorId(com.getAuthorId());
            c.setAuthorName(com.getAuthorName());
            c.setDateSubmitted(com.getDateSubmitted());
            c.setDateModified(com.getDateModified());
            c.setReview(com.getReview());

            list.add(c);
        }

        return list;
    }

    public static void toJson(ArrayList<Comment> comments){
        ObjectMapper om = new ObjectMapper();
        CommentContainer c = new CommentContainer();
        c.setData(comments);
        try {
            om.writeValue(new File("comments_ok.json"), c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
