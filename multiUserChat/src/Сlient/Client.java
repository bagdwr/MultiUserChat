package Сlient;

import Messages.Message;
import Messages.MessageType;
import Server.Connection;

import java.net.Socket;

public class Client {
    private static ClientGui gui;
    private Connection connection;
    private static UserClientData userClientData;
    public  Boolean isConnected=false;

    public void disconnectFromServer(){
        try {
            if (isConnected){
                connection.sendMessage(new Message(MessageType.DISABLE_USER));
                userClientData.getUsers().clear();
                gui.refreshUsersList(userClientData.getUsers());
                isConnected=false;
            }else{
                gui.errorMessage("Вы не подключены");
            }
        }catch (Exception ex){
            gui.errorMessage( "Произошла ошибка при отключении");
        }
    }
    public void connectToServer(){
        if (!isConnected){
        while (true){
            try {
                String ip=gui.getIP();
                int port=gui.getPort();
                Socket socket=new Socket(ip,port);
                System.out.println("connected");
                // I don't know how to fix here... . connection(socket);
                connection=new Connection(socket);
                isConnected=true;
                gui.RefreshDialogWindow("Сервер: вы подключились");
                break;
            }catch (Exception ex){
                gui.errorMessage("Произошла ошибка! Возможно Вы ввели не верный адрес сервера или порт. Попробуйте еще раз");
                break;
            }}}
        else{
               gui.errorMessage("Вы уже подключены");
            }
        }
    public void sendMessageToServer(String message){
        try{
            connection.sendMessage(new Message(message,MessageType.TEXT_MESSAGE));
        }catch (Exception ex){
            gui.errorMessage("Ошибка отправки сообщения");
        }

    }
    public void getMessageFromServer(){
        while (isConnected){
            try {
                Message message=(Message)connection.receiveMessage();
                if (message.getMessageType().equals(MessageType.ADDING_USER)){

                }
                if (message.getMessageType().equals(MessageType.DISABLE_USER)){

                }
            }catch (Exception ex){
                gui.errorMessage("Ошибка при получении данных с сервера");
                isConnected=false;
            }
        }
    }
    public void UsernameRegistration(){
        while (true){
            try {
               Message message=connection.receiveMessage();
                if (message.getMessageType().equals(MessageType.REQUEST_USERNAME)){
                    String username=gui.requestUsername();
                    connection.sendMessage(new Message(username,MessageType.CHECK_USER));
                }
                if (message.getMessageType().equals(MessageType.ACCEPTED_USER)){
                    gui.RefreshDialogWindow("Server: Your username was accepted");
                    gui.refreshUsersList(message.getUsers());
                    break;
                }
            }catch (Exception ex){
                gui.errorMessage("Произошла ошибка при регистрации имени");
                try {
                    connection.close();
                    isConnected=false;
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        Client client=new Client();
        userClientData= new UserClientData();
        gui=new ClientGui(client);
        gui.drawGui();
        while (true){
            if (client.isConnected){
                client.UsernameRegistration();
                client.getMessageFromServer();
                client.isConnected=false;
                break;
            }
        }
    }
}
