package application;

import java.io.IOException;

import food.Meal;
import food.Recipe;
import parser.GoalArgumentParser;
import parser.UserArgumentParser;
import persistence.user.UserDAO;
import user.User;

public class FullMode extends PTUI {

    private UserDAO userDAO;

    private User loggedInUser;

    public FullMode(User user, UserDAO userDAO) {
        super();
        loggedInUser = user;
        this.userDAO = userDAO;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public boolean createRecipe(String name, String instructions) {
        Recipe recipe = new Recipe(0, name, instructions);

        try {
            recipeDAO.createRecipe(recipe);
            System.out.println("recipe created");
            return true;
        } catch (IOException e) {
            System.out.println("recipe not created");
            return false;
        }
    }

    public boolean updateRecipe(int id, String name, String instructions, int ingredientId, int quantity) {
        Recipe recipe = new Recipe(id, name, instructions);
        recipe.addIngredient(ingredientId, quantity);
        try {
            recipeDAO.updateRecipe(recipe);
            System.out.println("recipe updated");
            return true;
        } catch (IOException e) {
            System.out.println("recipe not updated");
            return false;
        }

    }

    public boolean createMeal(String name) {
        Meal meal = new Meal(0, name);

        try {
            mealDAO.createMeal(meal);
            System.out.println("meal created");
            return true;
        } catch (IOException e) {
            System.out.println("meal not created");
            return false;
        }
    }

    public boolean updateMeal(int id, String name, int recipeId) {
        Meal meal = new Meal(id, name);
        meal.addRecipe(recipeId);

        try {
            mealDAO.updateMeal(meal);
            System.out.println("meal updated");
            return true;
        } catch (IOException e) {
            System.out.println("meal not updated");
            return false;
        }

    }

    public String toString() {
        return "FullMode";
    }

    public Object takeArgument(String argument) {
        String[] tokens = argument.split(" ");
        switch (tokens[0]) {
            case "goal":
                GoalArgumentParser gAP = new GoalArgumentParser(loggedInUser, tokens[1], tokens[2]);
                gAP.execute();
                break;
            case "user":
                UserArgumentParser uAP = new UserArgumentParser(loggedInUser, tokens[1], tokens[2]);
                uAP.execute();

            case "ingredient":
                break;

            case "recipe":
                break;

            case "meal":
                break;

            case "team":
                break;

            case "history":
                break;

            case "notification":
                break;

            case "workout":
                break;

            case "logout":
                break;

        }

        return null;

    }

    public static void main(String[] args) {

    }

}
