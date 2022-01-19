package dbConnections;

import dto.FollowDTO;
import entities.Recipe;
import entities.User;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.neo4j.driver.Values.parameters;

public class GraphConn implements AutoCloseable{
    private final String connectionString = "neo4j://172.16.4.53:7687";
    private final String user = "neo4j";
    private final String password = "root";

    private final Driver driver;

    public GraphConn(){
        driver = GraphDatabase.driver(connectionString, AuthTokens.basic(user, password));

    }

    public void addUsers(ArrayList<User> list){

        try (Session session = driver.session()) {
            for(User u: list) {
                session.run("CREATE (u:User {id: $id, email: $email, password: $password, firstName: $firstName, lastName: $lastName, username: $username})",
                        parameters(
                            "id", u.getUserID(),
                            "email", u.getEmail(),
                            "password", u.getPassword(),
                            "firstName", u.getFirstName(),
                            "lastName", u.getLastName(),
                            "username", u.getUsername()
                        )
                );
            }
        }
    }

    public void addRecipes(ArrayList<Recipe> list){
        for(Recipe r: list) {
            try (Session session = driver.session()) {
                session.run("MERGE (r: Recipe {id: $id, name: $name, category: $category, datePublished: $date})",
                        parameters(
                                "id", r.getRecipeId(),
                                "name", r.getName(),
                                "category", r.getRecipeCategory(),
                                "date", ((r.getDatePublished() != null) ? r.getDatePublished().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                        );
                session.run(
                        "MATCH (u:User) WHERE u.id = $uid " +
                        "MATCH (r:Recipe) WHERE r.id = $rid " +
                        "CREATE (u)-[:ADDS]->(r)",
                        parameters("uid", r.getAuthorId(), "rid", r.getRecipeId())
                );
            }
        }
    }


    public void connectUsers(ArrayList<FollowDTO> list){
        for(FollowDTO f: list){
            try (Session session = driver.session()){
                for(int i: f.getFollow()){
                    session.run(
                            "MATCH (u1:User) WHERE u1.id = $user1 " +
                                    "MATCH (u2:User) WHERE u2.id = $user2 " +
                                    "CREATE (u1)-[:FOLLOWS]->(u2)",
                            parameters("user1", f.getUserId(), "user2", i)
                    );
                }
            }
        }
    }
    @Override
    public void close() throws Exception {

    }
}
