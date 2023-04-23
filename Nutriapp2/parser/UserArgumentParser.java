package parser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.PTUI;
import persistence.user.UserDAO;
import persistence.user.UserFileDAO;
import user.User;

public class UserArgumentParser extends ArgumentParser {

    private UserDAO userDAO;

    public UserArgumentParser(User user, String operation, String data) {
        super(user, operation, data);
        try {
            userDAO = new UserFileDAO(String.format(PTUI.PATH, "users"), new ObjectMapper());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isLoginDataValid(String data) {
        String[] tokens = data.split(",");
        return tokens.length == 2;
    }

    @Override
    public Object execute() {
        Object response = null;
        switch (operation) {
            case "get":
                System.out.println(getLoggedInUser());
                return response;
            case "update":
                if (isDataValid()) {
                    String[] tokens = data.split(",");
                    updateLoggedInUser(tokens[0], tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]),
                            tokens[4]);
                }
                System.out.println("invalid data");
                return response;
            case "login":
                if (isLoginDataValid(data)) {
                    String[] tokens = data.split(",");
                    User loggedUser = login(tokens[0], tokens[1]);
                    if (loggedUser != null) {
                        response = loggedUser;
                    }
                }
                break;

            case "register":
                if (isLoginDataValid(data)) {
                    String[] tokens = data.split(",");
                    register(tokens[0], tokens[1]);
                }
                return response;

        }
        return response;
    }

    public User login(String username, String password) {
        User tempUser = new User(0, username, password);
        try {
            User checkedUser = userDAO.loginUser(tempUser);
            return checkedUser;
        } catch (IOException e) {
            System.out.println("user not found");
            return null;
        }
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

    @Override
    public boolean isDataValid() {
        return Pattern.matches("(?:[*]|\\D+),(?:[*]|\\D+)(?:[*]|\\d+),(?:[*]|\\d+),(?:[*]|\\d{4}-\\d{2}-\\d{2})", data);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * UGLY AND INEFFICIENT BUT GETS THE JOB DONE
     */
    public boolean updateLoggedInUser(String password, String name, Integer height, Integer weight, String birthDate) {
        if (password.equals("*")) {
            password = loggedInUser.getPassword();
        }
        if (name.equals("*")) {
            name = loggedInUser.getName(); /// all these ifs allow users to call this function changing only one value
                                           /// for a user and if a value is null it will use the users old value
        }
        if (height.equals("*")) {
            height = loggedInUser.getHeight();
        }
        if (weight.equals("*")) {
            weight = loggedInUser.getWeight();
        }
        if (birthDate.equals("*")) {
            birthDate = loggedInUser.getBirthDate();
        } else {
            User newUser = new User(loggedInUser.getId(), loggedInUser.getName(), password, name, height, weight,
                    birthDate);
            try {
                LocalDate.parse(birthDate);

                try {
                    userDAO.updateUser(newUser);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("invalid date entered");
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }

}
