import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.FullMode;
import application.GuestMode;
import persistence.user.UserDAO;
import persistence.user.UserFileDAO;
import user.User;

public class Main {

    private GuestMode guestMode;
    private FullMode fullMode;
    private Scanner scanner;
    private UserDAO userDAO;

    public Main() {
        guestMode = new GuestMode(); // starts in guest mode
        fullMode = null;
        scanner = new Scanner(System.in);

        try {
            userDAO = new UserFileDAO("Nutriapp2/data/users.json", new ObjectMapper());
        } catch (IOException e) {
        }
    }

    public void setFullMode(FullMode fullMode) {
        this.fullMode = fullMode;
    }

    public void register(String username, String password) {
        User user = new User(0, username, password);
        try {
            userDAO.createUser(user);
            System.out.println("user created");
        } catch (IOException e) {
            System.out.println("user not created");
        }
    }

    public boolean login(String username, String password) {
        User tempUser = new User(0, username, password);
        try {
            User checkedUser = userDAO.loginUser(tempUser);
            return checkedUser != null;
        } catch (IOException e) {
            System.out.println("user not found");
        }
        return false;
    }

    public void run() {
        System.out.println("Welcome to NutritionTracker!");
        System.out
                .println(
                        "Resources: goal|user|ingredient|recipe|meal|team|history|notification|workout|");
        System.out.println("Operations: get|create|update|login|logout|");
        System.out.println("Data: a,b,c,d... \t data separated by commas with no space");
        System.out.println("Commands formatted as such <Resource> <Operation> <Data>");
        while (true) {
            System.out.println(guestMode);
            String argument = scanner.nextLine();
            Object response = guestMode.takeArgument(argument);
            if (response instanceof User) {
                setFullMode(new FullMode((User) response, userDAO));
            }
            if (fullMode != null) {
                while (true) {
                    System.out.println(fullMode);
                    argument = scanner.nextLine();
                    fullMode.takeArgument(argument + " a");
                }
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}