package ChatSystem;
import javax.swing.SwingUtilities;
import java.util.Scanner;
public class Main {
    private String username;
    private boolean loggedIn;
    ChatSystem chatSystem;
    ChatWindow chatWindow;
    User loggedInUser = null;

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
        //main.performChatting();
    }

    public Main() {
        username = "";
        loggedIn = false;
    }

    public void sendMessage(String messageReceiver, String messageContent) {
        chatSystem.sendMessage(loggedInUser.getUsername(), messageReceiver, messageContent);
    }

    public void performChatting() {
        Scanner scanner = new Scanner(System.in);
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        System.out.print("Enter friend's username: ");
        String messageReceiver = scanner.nextLine();

        System.out.print("Enter message: ");
        String messageContent = scanner.nextLine();

        sendMessage(messageReceiver, messageContent);
    }

    public void start() {
        chatSystem = new ChatSystem();
        // Create and display the login window
        loginGUI loginGUI = new loginGUI(this);
        loginGUI.show();

        // Wait for the login process to complete
        while (loggedInUser == null) {
            try {
                Thread.sleep(100); // Sleep for a short duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // The login process has completed, continue with other operations
        if (loggedInUser != null) {
            username=loggedInUser.getUsername();
            loggedIn=true;

            chatSystem.setValue(username);

            chatWindow=new ChatWindow(this);
        } else {
            System.out.println("Login failed. Exiting the application.");
        }
    }
}
