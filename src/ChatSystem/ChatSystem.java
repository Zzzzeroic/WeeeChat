package ChatSystem;

import ChatSystem.writeFile.writeLogin;
import java.util.Spliterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
class ChatSystem {
    private List<User> userList;
    private List<Friend> friendList;
    private List<ChatRecord> chatRecordList;

    public ChatSystem() {
        userList = new ArrayList<>();
        friendList = new ArrayList<>();
        chatRecordList = new ArrayList<>();
    }

    public void registerUser(String username, String password) {
        setUserList();
        if (userList != null) {
            for(User tmpUser:userList) {
                if(username.equals(tmpUser.getUsername())) {
                    System.out.println("User exists.");
                    return;
                }
            }
        }
        User user = new User(username, password);
        userList.add(user);
        writeLogin.writeLogin("UserStorage/loginRecord.txt", username + ',');
        writeLogin.writeLogin("UserStorage/loginRecord.txt", password + '\n');
        System.out.println("User registered successfully.");

        String dir="UserStorage/User/"+username;
        File dire=new File(dir);
        try{
            if(!dire.exists()){
                dire.mkdirs();
            }
            String userFile = "UserStorage/User/"+username+'/';
            File fF=new File(userFile+"friend.txt");
            File fC=new File(userFile+"record.txt");
            if(!fF.exists()) fF.createNewFile();
            if(!fC.exists()) fC.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        setUserList();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                if (user.authenticate(password)) {
                    user.setOnline(true);
                    return user;
                } else {
                    System.out.println("Invalid password.");
                    return null;
                }
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public void addFriend(String friendname, String user_name) {
        String userFilePath = "UserStorage/User/" + user_name + "/";
        File userFile = new File(userFilePath);
        List<Friend> friendList = new ArrayList<>();

        if (!userFile.exists()) {
            try {
                boolean created = userFile.mkdirs();
                if (created) {
                    System.out.println("文件夹已创建：" + userFile.getAbsolutePath());
                } else {
                    System.out.println("文件夹创建失败：" + userFile.getAbsolutePath());
                    return;
                }
            } catch (SecurityException e) {
                System.out.println("创建文件夹失败：" + e.getMessage());
                return;
            }
        }

        String friendFilePath = userFilePath + "friend.txt";
        File friendFile = new File(friendFilePath);

        if (friendFile.exists()) {
            try (FileInputStream fis = new FileInputStream(friendFile);
                 BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Friend tempFriend = new Friend(line);
                    friendList.add(tempFriend);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Friend iFriend : friendList) {
            if (iFriend.getUsername().equals(friendname)) {
                System.out.print("Friend exists.");
                return;
            }
        }

        setUserList();

        for (User iUser:userList){
            if(iUser.getUsername().equals(friendname)){
                Friend friend = new Friend(friendname);
                friendList.add(friend);
                try (FileWriter fw = new FileWriter(friendFile, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter pw = new PrintWriter(bw)) {
                    pw.println(friendname);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Friend added successfully.");
                return;
            }
        }
        System.out.print("Friend not found");
        return;
    }


    public void changeFriendStatus(String username, boolean online) {
        for (Friend friend : friendList) {
            if (friend.getUsername().equals(username)) {
                friend.setOnline(online);
                return;
            }
        }
        System.out.println("Friend not found.");
    }

    public void sendMessage(String sender, String receiver, String content) {
        ChatRecord chatRecord = new ChatRecord(sender, receiver, getCurrentTime(), content, true);
        chatRecordList.add(chatRecord);
        System.out.println("Message sent successfully.");
    }

    public void storeChatRecord(ChatRecord chatRecord) {
        chatRecordList.add(chatRecord);
    }

    public void displayChatRecords(boolean display) {
        //读取消息记录
        try {
            FileInputStream fis = new FileInputStream("UserStorage/record.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] recordLine = line.split(" ");
                if(recordLine.length<5) continue;
                else {
                    String sender = recordLine[0];
                    String receiver = recordLine[1];
                    String record_time = recordLine[2]+' '+recordLine[3];
                    String content="";
                    //输入内容中也会有空格，recordLine中的内容不一定只有5个
                    for(int i=4;i<recordLine.length;i++){
                        content+=recordLine[i];
                        if((i+1)<recordLine.length) content+=' ';
                    }

                    ChatRecord chatRecord = new ChatRecord(sender, receiver, record_time, content, false);
                    chatRecordList.add(chatRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(display) {
            for (ChatRecord chatRecord : chatRecordList) {
                System.out.println("Sender: " + chatRecord.getSender());
                System.out.println("Receiver: " + chatRecord.getReceiver());
                System.out.println("Time: " + chatRecord.getTime());
                System.out.println("Content: " + chatRecord.getContent());
                System.out.println();
            }
        }
    }

    public void displayChatRecords(String username, boolean display) {
        //读取消息记录
        try {
            FileInputStream fis = new FileInputStream("UserStorage/User/"+username+'/'+"record.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] recordLine = line.split(" ");
                if(recordLine.length<5) continue;
                else {
                    String sender = recordLine[0];
                    String receiver = recordLine[1];
                    String record_time = recordLine[2]+' '+recordLine[3];
                    String content="";
                    //输入内容中也会有空格，recordLine中的内容不一定只有5个
                    for(int i=4;i<recordLine.length;i++){
                        content+=recordLine[i];
                        if((i+1)<recordLine.length) content+=' ';
                    }

                    ChatRecord chatRecord = new ChatRecord(sender, receiver, record_time, content, false);
                    chatRecordList.add(chatRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(display) {
            for (ChatRecord chatRecord : chatRecordList) {
                System.out.println("Sender: " + chatRecord.getSender());
                System.out.println("Receiver: " + chatRecord.getReceiver());
                System.out.println("Time: " + chatRecord.getTime());
                System.out.println("Content: " + chatRecord.getContent());
                System.out.println();
            }
        }
    }

    private String getCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    private void setUserList(){
        userList.clear();
        //将已记录好的用户存入userList
        try {
            FileInputStream fis = new FileInputStream("UserStorage/loginRecord.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String temName = "";
                String temPass = "";
                Boolean tem = false;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != ',' & !tem)
                        temName += line.charAt(i);
                    else if (line.charAt(i) != ',' & tem)
                        temPass += line.charAt(i);
                    else tem = true;
                }
                User temUser = new User(temName, temPass);
                //System.out.println(temName+' '+temPass);
                userList.add(temUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValue(String update_name){
        String userFilePath = "UserStorage/User/" + update_name + "/";
        File userFile = new File(userFilePath);

        if (!userFile.exists()) {
            try {
                boolean created = userFile.mkdirs();
                if (created) {
                    System.out.println("文件夹已创建：" + userFile.getAbsolutePath());
                } else {
                    System.out.println("文件夹创建失败：" + userFile.getAbsolutePath());
                    return;
                }
            } catch (SecurityException e) {
                System.out.println("创建文件夹失败：" + e.getMessage());
                return;
            }
        }

        // 读取好友列表
        String friendFilePath = userFilePath + "friend.txt";
        File friendFile = new File(friendFilePath);

        if (friendFile.exists()) {
            try (FileInputStream fis = new FileInputStream(friendFile);
                 BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Friend tempFriend = new Friend(line);
                    friendList.add(tempFriend);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 读取聊天记录,利用之前写好的展示聊天记录功能对chatRecord进行赋值
        displayChatRecords(update_name, true);

    }

    public List<Friend> retFriend(){
        if(friendList.isEmpty()){
            return null;
        }
        return friendList;
    }
    public List<ChatRecord> retRecord(){
        if(chatRecordList.isEmpty()){
            return null;
        }
        return chatRecordList;
    }
}