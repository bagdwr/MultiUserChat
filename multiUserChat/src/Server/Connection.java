package Server;

import Messages.Message;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Connection(){}
    public Connection(Socket socket) throws Exception{
            this.socket=socket;
            this.outputStream=new ObjectOutputStream(socket.getOutputStream());
            this.inputStream=new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws Exception{
            outputStream.writeObject(message);
    }
    public Message receiveMessage() throws Exception{
        Message message=(Message) inputStream.readObject();
        return message;
    }
    @Override
    public void close() throws IOException {
        socket.close();
        inputStream.close();
        outputStream.close();
    }
}
