package application;

import parser.UserArgumentParser;

public class GuestMode extends PTUI {

    public GuestMode() {
        super();
    }

    public Object takeArgument(String argument) {
        String[] tokens = argument.split(" ");
        Object response = null;
        if (isValidResource(tokens[0])) {
            switch (tokens[0]) {
                case "ingredient":
                    System.out.println(getIngredients());
                    return response;
                case "recipe":
                    System.out.println(getRecipes());
                    return response;
                case "meal":
                    System.out.println(getMeals());
                    return response;
                case "user":
                    UserArgumentParser uAP = new UserArgumentParser(null, tokens[1], tokens[2]);
                    response = uAP.execute();
                    break;
                default:
                    return "invalid argument";
            }
        }
        return response;

    }

    @Override
    public String toString() {
        return "GuestMode";
    }

}
