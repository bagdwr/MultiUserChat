package Сlient;

import java.util.HashSet;
import java.util.Set;

public class UserClientData {
    private Set<String>users=new HashSet<>();

    public UserClientData(){}
    public UserClientData(Set<String> users) {
        this.users = users;
    }

    public Set<String> getUsers(){
        return  users;
    }
    public void setUser(Set<String>users){
        this.users=users;
    }
    public void addNewUser(String name){
        users.add(name);
    }
    public void removeUser(String name){
        users.remove(name);
    }
}
