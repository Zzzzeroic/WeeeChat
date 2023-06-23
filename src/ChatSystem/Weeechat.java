package ChatSystem;
import java.util.Scanner;

public class Weeechat {
    private Boolean State=false;
    public static void main(String[] args) {
        ChatSystem chatSystem = new ChatSystem();
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;
        while (true) {
            System.out.println("1. Register user");
            System.out.println("2. Login");
            System.out.println("3. Add friend");
            System.out.println("4. Send message");
            System.out.println("5. Display chat records");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String registerPassword = scanner.nextLine();
                    chatSystem.registerUser(registerUsername, registerPassword);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    loggedInUser = chatSystem.login(loginUsername, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("User logged in successfully.");
                    }
                    break;
                case 3:
                    System.out.print("Enter friend's username: ");
                    String friendUsername = scanner.nextLine();
                    break;
                case 4:
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                        break;
                    }
                    System.out.print("Enter friend's username: ");
                    String messageReceiver = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String messageContent = scanner.nextLine();
                    chatSystem.sendMessage(loggedInUser.getUsername(), messageReceiver, messageContent);
                    break;
                case 5:
                    chatSystem.displayChatRecords(true);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
    public void setLoginState(){
        State=true;
    }
}