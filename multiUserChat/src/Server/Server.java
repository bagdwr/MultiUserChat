package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server{
    private ServerSocket serverSocket;
    private static ServerGui gui;
    private static Boolean isServerWorks=false;
    private static UsersData usersData;

    protected void serverStart(){
        try {
            if (!isServerWorks)
            {
                serverSocket=new ServerSocket(gui.getPort());
                gui.refreshTextArea("Сервер запущен");
                isServerWorks=true;
            }else {
                gui.prompt("Сервер уже работает");
            }

        }
        catch (Exception ex){
            gui.refreshTextArea("Не удалось запустить сервер");
        }

    }
    protected void serverStop(){
        try {
            if (!serverSocket.isClosed() && serverSocket!=null){
//                for (Map.Entry<String,Connection>user:usersData.getUsers().entrySet()){
//                    user.getValue().CloseSocket();
//                }
                serverSocket.close();
               // usersData.getUsers().clear();
                gui.refreshTextArea("Сервер остановлен");
                isServerWorks=false;
            }else {
                gui.refreshTextArea("Сервер не запущен");
            }
        }catch (Exception ex){
                ex.printStackTrace();
        }
    }
    protected void AcceptServer(){
        try {
            while (true){
                Socket socket=serverSocket.accept();
                System.out.println("connected");
                ServerThread serverThread= new ServerThread(socket,gui,usersData);
                System.out.println("yep");
                serverThread.start();
            }
        }catch (Exception ex){
            gui.refreshTextArea("Не смог подключиться к серверу");
        }
    }
    public static void main(String[] args) {
        Server server=new Server();
        gui=new ServerGui(server);
        gui.drawGui();
        while (isServerWorks){
            server.AcceptServer();
            isServerWorks=false;
        }
    }

}
