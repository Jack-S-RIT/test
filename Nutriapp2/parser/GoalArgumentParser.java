package parser;

import java.io.IOException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.PTUI;
import goal.Goal;
import persistence.goal.GoalDAO;
import persistence.goal.GoalFileDAO;
import user.User;

public class GoalArgumentParser extends ArgumentParser {

    private GoalDAO goalDAO;

    public GoalArgumentParser(User user, String operation, String data) {
        super(user, operation, data);
        try {
            goalDAO = new GoalFileDAO(String.format(PTUI.PATH, "goals"), new ObjectMapper());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object execute() {
        switch (operation) {
            case "get":
                System.out.println(getGoal());
                return null;
            case "create":
                if (isDataValid()) {
                    String[] tokens = data.split(",");
                    createGoal(tokens[0], Integer.parseInt(tokens[1]));
                }
                System.out.println("invalid data");
                return null;
            case "update":
                if (isDataValid()) {
                    String[] tokens = data.split(",");
                    updateGoal(tokens[0], Integer.parseInt(tokens[1]));
                }
                return null;
        }
        return null;
    }

    public boolean createGoal(String state, int targetWeight) {
        Goal goal = new Goal(1, state, targetWeight, loggedInUser.getId());

        try {
            goalDAO.createGoal(goal);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Goal getGoal() {
        try {
            return goalDAO.getGoalByUserId(loggedInUser.getId());
        } catch (IOException e) {
        }
        return null;
    }

    public boolean updateGoal(String state, int targetWeight) {
        Goal oldGoal = getGoal();
        Goal newGoal = new Goal(oldGoal.getId(), state, targetWeight, loggedInUser.getId());
        try {
            goalDAO.updateGoal(newGoal);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public boolean isDataValid() {
        return Pattern.matches("(?:gain-weight,\\d+)|(?:lose-weight,\\d+)|(?:maintain-weight,\\d+)", data);
    }

}
