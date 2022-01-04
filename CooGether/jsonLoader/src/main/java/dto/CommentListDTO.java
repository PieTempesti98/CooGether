package dto;

import java.util.ArrayList;

public class CommentListDTO {
    private ArrayList<CommentDTO> data;

    public ArrayList<CommentDTO> getData() {
        return data;
    }

    public void setData(ArrayList<CommentDTO> data) {
        this.data = data;
    }
}
