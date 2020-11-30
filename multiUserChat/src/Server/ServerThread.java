package Server;

import Messages.Message;
import Messages.MessageType;

import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerThread extends Thread{
    private Socket socket;
    private ServerGui gui;
    private UsersData usersData;

    public ServerThread(){}
    public ServerThread(Socket socket, ServerGui gui, UsersData usersData){
        this.socket=socket;
        this.gui=gui;
        this.usersData=usersData;
    }

    protected void MessagingBetweenUsers(Connection connection, String username){

    }
    protected void sendMessageAllUsers(Message message){
        try {
            for (Map.Entry<String,Connection>user:usersData.getUsers().entrySet()){
                user.getValue().sendMessage(message);
            }
        }catch (Exception ex){
            gui.refreshTextArea("Cannot send message to users");
        }

    }
    protected String RequestUsername(Connection connection){
        String username;
        while (true){
            try {
                connection.sendMessage(new Message(MessageType.REQUEST_USERNAME));
                Message messageUsername=connection.receiveMessage();
                username=messageUsername.getMessage();
                if (messageUsername.getMessageType().equals(MessageType.CHECK_USER) && !username.isEmpty() && !usersData.getUsers().containsKey(username)){
                    usersData.AddUser(username,connection);
                    Set<String>newUsers=new HashSet<>();
                    for(Map.Entry<String,Connection>user: usersData.getUsers().entrySet()){
                        newUsers.add(user.getKey());
                    }
                    connection.sendMessage(new Message(MessageType.ACCEPTED_USER,newUsers));
                    sendMessageAllUsers(new Message(username,MessageType.ADDING_USER));
                    break;
                }else {
                    connection.sendMessage(new Message(MessageType.USER_USED));
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
          return username;
    }
    @Override
    public void run() {
        gui.refreshTextArea("Подключился новый пользователь с удаленным сервером "+socket.getRemoteSocketAddress()+"\n");
         try {
             Connection connection=new Connection(socket);
             String username=RequestUsername(connection);
             MessagingBetweenUsers(connection,username);
         }catch (Exception ex){
             gui.refreshTextArea("Произошла ошибка\n");
         }
    }
}
