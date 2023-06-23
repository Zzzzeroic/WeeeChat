package ChatSystem;
import ChatSystem.writeFile.writeFile;
class ChatRecord {
    private String sender;
    private String receiver;
    private String time;
    private String content;

    public ChatRecord(String sender, String receiver, String time, String content, Boolean canRecord) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
        if(canRecord) {
            //总聊天记录
            writeFile.writeFile("UserStorage/record.txt", sender + ' ');
            writeFile.writeFile("UserStorage/record.txt", receiver + ' ');
            writeFile.writeFile("UserStorage/record.txt", time + ' ');
            writeFile.writeFile("UserStorage/record.txt", content + ' ');
            writeFile.writeFile("UserStorage/record.txt");

            //用户个人聊天记录
            String UserFilePath="UserStorage/User/";
            String FilePath_sender=UserFilePath+sender+'/'+"record.txt";
            String FilePath_receiver=UserFilePath+receiver+'/'+"record.txt";

            writeFile.writeFile(FilePath_sender, sender + ' ');
            writeFile.writeFile(FilePath_sender, receiver + ' ');
            writeFile.writeFile(FilePath_sender, time + ' ');
            writeFile.writeFile(FilePath_sender, content + ' ');
            writeFile.writeFile(FilePath_sender);

            writeFile.writeFile(FilePath_receiver, sender + ' ');
            writeFile.writeFile(FilePath_receiver, receiver + ' ');
            writeFile.writeFile(FilePath_receiver, time + ' ');
            writeFile.writeFile(FilePath_receiver, content + ' ');
            writeFile.writeFile(FilePath_receiver);
        }
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
