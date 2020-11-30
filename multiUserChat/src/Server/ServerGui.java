package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGui extends JFrame {
    private Server server;
    private JTextArea dialogArea=new JTextArea(15,35);
    private JButton startButton=new JButton("Запустить сервер");
    private JButton stopButton=new JButton("Отключить сервер");
    private JPanel panelButtons=new JPanel();

    public ServerGui(){}
    public ServerGui(Server server){
        this.server=server;
    }

    public void drawGui(){
        setTitle("Server");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogArea.setEditable(false);
        dialogArea.setLineWrap(true);
        add(new JScrollPane(dialogArea),BorderLayout.CENTER);
        setSize(380,250);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.serverStop();
                System.exit(0);
            }
        });
        panelButtons.add(startButton);
        panelButtons.add(stopButton);
        add(panelButtons, BorderLayout.SOUTH);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.serverStart();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.serverStop();
            }
        });
    }
    public void refreshTextArea(String message){
        dialogArea.append(message+"\n");
    }

    public int getPort(){
        while (true){
            String Port=JOptionPane.showInputDialog(null,"Введите порт серевера 0-65535",
                    "порт",JOptionPane.QUESTION_MESSAGE);
            if (Port.trim().matches("\\b[0-9]{1,5}\\b")){
                int port=Integer.parseInt(Port.trim());
                if (port>=0 && port<=65535){
                    return port;
                }
                JOptionPane.showMessageDialog(null, "Не верный порт, попробуйте еще раз",
                        "ошибка",JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "Не верный порт, попробуйте еще раз",
                        "ошибка",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void prompt(String s){
        JOptionPane.showMessageDialog(null,s,"Сервер",JOptionPane.ERROR_MESSAGE);
    }
}
