package Сlient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class ClientGui extends JFrame {
    private Client client;
    private JTextArea dialog=new JTextArea(5,30);
    private JTextArea users=new JTextArea(5,20);
    private JTextField messageField=new JTextField(32);
    private JButton connect=new JButton("подлючиться");
    private JButton disconnect=new JButton("отключиться");
    private JPanel panel=new JPanel();

    public ClientGui(Client client){
        this.client=client;
    }
    public void drawGui(){
        setTitle("Чат");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                   if (client.isConnected){
                       client.disconnectFromServer();
                   }
                   System.exit(0);
            }
        });
        dialog.setLineWrap(true);
        dialog.setEditable(false);
        users.setLineWrap(true);
        users.setEditable(false);
        add(new JScrollPane(dialog), BorderLayout.CENTER);
        add(new JScrollPane(users),BorderLayout.EAST);
        setSize(700,500);
        //pack();
        panel.add(messageField);
        panel.add(connect);
        panel.add(disconnect);
        add(panel,BorderLayout.SOUTH);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.connectToServer();
            }
        });
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 client.disconnectFromServer();
            }
        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(client.isConnected){
                    String mess=messageField.getText().trim();
                    if (!mess.isEmpty()){
                        client.sendMessageToServer(mess);
                    }
                    messageField.setText("");
                }else {
                    JOptionPane.showMessageDialog(null,"Вы не подключены","Could not send message",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
    public void RefreshDialogWindow(String message){
        dialog.append(message+"\n");
    }
    public String getIP(){
        String ip;
        while (true){
            ip=JOptionPane.showInputDialog(null,"Введите IP адрес сервера","IP",JOptionPane.QUESTION_MESSAGE);
            if (ip.isEmpty()){
                JOptionPane.showMessageDialog(null,"Ошибка данных","Ошибка",JOptionPane.ERROR_MESSAGE);
            }else {
                break;
            }
        }
        return ip.trim();
    }
    public int getPort(){
        int port;
        while (true){
            String p=JOptionPane.showInputDialog(null,"Введите порт","порт",JOptionPane.QUESTION_MESSAGE);
            if (!p.isEmpty() && p.length()<6){
                 port=Integer.valueOf(p.trim());
                 break;
            }
            else {
                JOptionPane.showMessageDialog(null,"Ошибка данных","ошибка",JOptionPane.ERROR_MESSAGE);
            }
        }
        return port;
    }
    public String requestUsername(){
        String username;
        while (true){
            username=JOptionPane.showInputDialog(null,"Введите имя пользователя,","username",JOptionPane.QUESTION_MESSAGE);
            if (username.isEmpty()){
                JOptionPane.showMessageDialog(null,"Ошибка данных","Ошибка",JOptionPane.ERROR_MESSAGE);
            }else {
                break;
            }
        }
        return username.trim();
    }
    public void errorMessage(String text){
        JOptionPane.showMessageDialog(null,text,"Ошибка",JOptionPane.ERROR_MESSAGE);
    }
    public void refreshUsersList(Set<String> listUsers){
        users.setText("");
        if (client.isConnected){
            StringBuilder sb=new StringBuilder("Список пользователей:\n");
            for (String u:listUsers){
                sb.append(u+"\n");
            }
            users.setText(sb.toString());
        }
    }

}
