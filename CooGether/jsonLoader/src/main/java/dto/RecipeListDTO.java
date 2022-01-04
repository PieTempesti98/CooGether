package dto;

import java.util.ArrayList;

public class RecipeListDTO {
    private ArrayList<RecipeDTO> data;

    public ArrayList<RecipeDTO> getData() {
        return data;
    }

    public void setData(ArrayList<RecipeDTO> data) {
        this.data = data;
    }
}
