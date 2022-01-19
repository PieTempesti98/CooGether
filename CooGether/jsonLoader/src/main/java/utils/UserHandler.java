package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import entities.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserHandler {
    public static ArrayList<UserDTO> parseUsers(String path, ArrayList<UserDTO> list){
        InputStream input = UserListDTO.class.getResourceAsStream(path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserListDTO container =  objectMapper.readValue(input, UserListDTO.class);
            list.addAll(container.getResults());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<FollowDTO> parseFollows(String path){
        InputStream input = FollowListDTO.class.getResourceAsStream(path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FollowListDTO container =  objectMapper.readValue(input, FollowListDTO.class);
            return container.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<User> convertToUsers(ArrayList<UserDTO> dto){
        ArrayList<User> list = new ArrayList<>();
        for(UserDTO elem: dto){
            User u = new User();
            u.setFirstName(elem.getName().getFirst());
            u.setLastName(elem.getName().getLast());
            u.setPassword(elem.getLogin().getPassword());
            u.setNat(elem.getNat());
            u.setEmail(elem.getEmail());
            u.setUsername(elem.getLogin().getUsername());

            list.add(u);
        }
        return list;
    }

    public static ArrayList<User> fixUserId(ArrayList<User> list, ArrayList<Integer> userIDs, ArrayList<String> usernames){
        for(User elem: list){
            int i = list.indexOf(elem);
            elem.setUserID(userIDs.get(i));
            if(i < usernames.size())
                elem.setUsername(usernames.get(i));
        }
        return list;
    }

    public static void toJson(ArrayList<User> users){
        ObjectMapper om = new ObjectMapper();
        UserContainer c = new UserContainer();
        c.setData(users);
        try {
            om.writeValue(new File("users_ok.json"), c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
