package ChatSystem;

import javafx.geometry.HorizontalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends JFrame {
    private JFrame jFrame = new JFrame();
    private JTextArea chatArea, messageArea, searchArea;
    private JScrollPane scrollPane_Text, scrollPane_Friend, scrollPane;
    private JButton sendButton;
    private User loginUser;
    private String Receiver;
    private int windowHeight, windowWidth;
    private JPanel jPanel, jPanel_F;
    private JButton addButton;
    private List<Friend> friendList;
    private List<ChatRecord> chatRecordList;
    private Main main;
    public ChatWindow(Main main) {
        super();
        this.main=main;
        loginUser=main.loggedInUser;

        chatRecordList=main.chatSystem.retRecord();

        windowHeight=32;
        windowWidth=64;

        Receiver="WeeeChat";
        String welcome = "欢迎来到WeeeChat，左侧为好友列表，下方为输入框。";

        setTitle(Receiver);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        friendList=main.chatSystem.retFriend();
        chatRecordList=main.chatSystem.retRecord();

        // 创建好友列表
        jPanel_F=new JPanel();
        BoxLayout layout = new BoxLayout(jPanel_F, BoxLayout.Y_AXIS);
        jPanel_F.setLayout(layout);

        addButton =new JButton("+");
        jPanel_F.add(addButton);
        if (friendList != null) {
            initButton();
        }
        scrollPane_Friend = new JScrollPane(jPanel_F);
        add(scrollPane_Friend, BorderLayout.WEST);

        // 创建对话区域
        chatArea = new JTextArea(welcome);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setRows(windowHeight);
        chatArea.setColumns(windowWidth);
        scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // 发送消息
        messageArea = new JTextArea();
        messageArea.setRows(2);
        messageArea.setColumns(windowWidth-8);
        messageArea.setLineWrap(true);
        scrollPane_Text = new JScrollPane(messageArea);

        // 发送按钮
        sendButton = new JButton("Send");

        jPanel = new JPanel();
        jPanel.add(scrollPane_Text);
        jPanel.add(sendButton);

        add(jPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);

        // 按下发送按钮
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 按下添加好友按钮
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame frame = new JFrame("Add Friends");
                searchArea = new JTextArea();
                frame.add(searchArea, BorderLayout.CENTER);

                JButton button1 = new JButton("确定");
                JButton button2 = new JButton("取消");

                JPanel jp1 = new JPanel();
                jp1.add(button1);
                jp1.add(button2);

                frame.add(jp1, BorderLayout.SOUTH);

                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);

                // 确认按钮
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main.chatSystem.addFriend(searchArea.getText(), loginUser.getUsername());

                        jPanel_F=new JPanel();
                        friendList.add(new Friend(searchArea.getText()));
                        addButton =new JButton("+");
                        jPanel_F.add(addButton);
                        initButton();
                        scrollPane_Friend = new JScrollPane(jPanel_F);
                        add(scrollPane_Friend, BorderLayout.WEST);
                    }
                });

                // 取消按钮
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
        });
    }

    private void sendMessage() {
        String message = messageArea.getText();
        main.sendMessage(Receiver, message);
        if (!message.isEmpty()) {
            chatArea.append("Me: " + message + "\n");
            messageArea.setText("");
        }
    }

    private void initButton(){
        if(friendList.size()==0) return;

        for(int i=0;i<friendList.size();i++){
            final String name = friendList.get(i).getUsername();
            JButton jButton=new JButton(name);
            jButton.setPreferredSize(new Dimension(200,100));
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chatArea.setText("");
                    Receiver=name;
                    // System.out.print("\npress "+name);
                    setTitle(Receiver);
                    updateChat();
                }
            });
            jPanel_F.add(jButton);
        }
    }
    private void updateChat(){
        chatRecordList = main.chatSystem.retRecord();
        if(chatRecordList==null) return;
        else {
            for (ChatRecord iRecord : chatRecordList) {
                if (iRecord.getReceiver().equals(loginUser.getUsername()) &
                        iRecord.getSender().equals(loginUser.getUsername()) &
                        loginUser.getUsername().equals(Receiver)) {
                    chatArea.append("Me: " + iRecord.getContent() + '\n');
                } else if (iRecord.getSender().equals(Receiver) &
                        iRecord.getReceiver().equals(loginUser.getUsername())) {
                    chatArea.append(Receiver + ": " + iRecord.getContent() + '\n');
                } else if (iRecord.getSender().equals(loginUser.getUsername()) & iRecord.getReceiver().equals(Receiver)) {
                    chatArea.append("Me: " + iRecord.getContent() + '\n');
                }
            }
        }
    }
}
