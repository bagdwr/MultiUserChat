package Server;

import java.util.HashMap;
import java.util.Map;

public class UsersData {
    private HashMap<String,Connection>users=new HashMap<>();

    public UsersData(){}
    public UsersData(HashMap<String,Connection> users) {
        this.users = users;
    }

    public HashMap<String,Connection>getUsers(){
        return users;
    }

    public void AddUser(String name,Connection connection){
        users.put(name,connection);
    }

    public void DeleteUser(String name){
        users.remove(name);
    }
}
