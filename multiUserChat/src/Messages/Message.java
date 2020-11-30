package Messages;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Message implements Serializable{
    private String message;
    private MessageType messageType;
    private Set<String>Users=new HashSet<>();

    public Message(){}
    public Message(MessageType messageType) {
        this.messageType = messageType;
    }
    public Message(MessageType messageType, Set<String> Users) {
        this.messageType = messageType;
        this.Users = Users;
    }
    public Message(String message, MessageType messageType){
        this.message=message;
        this.messageType=messageType;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Set<String> getUsers() {
        return Users;
    }
}
