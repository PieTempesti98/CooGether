package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class NewRecipeListDTO {
    @JsonProperty("root")
    private ArrayList<NewRecipeDTO> root;

    public ArrayList<NewRecipeDTO> getData() {
        return root;
    }

    public void setData(ArrayList<NewRecipeDTO> data) {
        this.root = data;
    }
}
