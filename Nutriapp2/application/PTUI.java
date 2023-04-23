package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import food.Ingredient;
import food.Meal;
import food.Recipe;
import persistence.ingredient.IngredientDAO;
import persistence.ingredient.IngredientFileDAO;
import persistence.meal.MealDAO;
import persistence.meal.MealFileDAO;
import persistence.recipe.RecipeDAO;
import persistence.recipe.RecipeFileDAO;

public abstract class PTUI {

    public static final String PATH = "Nutriapp2/data/%s.json";

    protected static final String RESOURCE_PATTERN = "(?:goal|user|ingredient|recipe|meal|team|history|notification|workout|login)";
    protected static final String OPERATION_PATTERN = "(?:get|create|update)";
    protected static final String DATA_PATTERN = "(?:.+,\\w+)";

    protected IngredientDAO ingredientDAO;

    protected RecipeDAO recipeDAO;

    protected MealDAO mealDAO;

    public PTUI() {
        try {
            ingredientDAO = new IngredientFileDAO("Nutriapp2/data/ingredients.json", new ObjectMapper());
            recipeDAO = new RecipeFileDAO("Nutriapp2/data/recipes.json", new ObjectMapper());
            mealDAO = new MealFileDAO("Nutriapp2/data/meals.json", new ObjectMapper());
        } catch (IOException e) {
            System.out.println("error with files");
        }
    }

    public static boolean isArgumentValid(String argument) {
        return Pattern.matches(RESOURCE_PATTERN + OPERATION_PATTERN + DATA_PATTERN, argument);
    }

    public String getIngredients() {
        try {
            String entry = "Ingredients: \n";
            Ingredient[] ingredients = ingredientDAO.getIngredients();
            for (int i = 0; i < ingredients.length; i++) {
                Ingredient ingredient = ingredients[i];
                entry += ingredient.toString() + ",\n";
            }
            return entry;
        } catch (IOException e) {
            System.out.println("error with files");
        }
        return null;
    }

    public boolean isValidResource(String resource) {
        return Pattern.matches(RESOURCE_PATTERN, resource);
    }

    private Object[] getRecipeData(HashMap<Integer, Integer> ingredients) throws IOException {
        String ingredientEntry = ", ingredients: [\n";
        int calories = 0;
        int fat = 0;
        int protein = 0;
        int fiber = 0;
        int carbs = 0;
        for (int j : ingredients.keySet()) { // iterates over all the ingredients in a given recipe
            int quantity = ingredients.get(j);
            Ingredient ingredient = ingredientDAO.getIngredient(j);
            if (ingredient != null) {
                ingredientEntry += "\t" + ingredient.toString() + " x " + quantity + ",\n";
                calories += ingredient.getCalories() * quantity;
                fat += ingredient.getFat() * quantity;
                protein += ingredient.getProtein() * quantity;
                fiber += ingredient.getFiber() * quantity;
                carbs += ingredient.getCarbs() * quantity;
            }
        }
        ingredientEntry += "]},\n";

        Object[] entryData = new Object[] { ingredientEntry, calories, fat, protein, fiber, carbs };
        return entryData;
    }

    public String getRecipes() {
        try {
            Recipe[] recipes = recipeDAO.getRecipes(); // gets all recipes
            String entry = "Recipes: \n";
            for (int i = 0; i < recipes.length; i++) { // iterates over all recipes in recipes.json
                String recipeEntry = "{id: %d, name: %s, instructions: %s, calories: %d, fat: %d, protein: %d, fiber: %d, carbs %d";
                Recipe recipe = recipes[i];
                Object[] ingredientEntry = getRecipeData(recipe.getIngredients());
                recipeEntry = String.format(recipeEntry, recipe.getId(), recipe.getName(), recipe.getInstructions(),
                        ingredientEntry[1], ingredientEntry[2], ingredientEntry[3], ingredientEntry[4],
                        ingredientEntry[5]);
                entry += recipeEntry + ingredientEntry[0];
            }
            return entry;
        } catch (

        IOException e) {
            System.out.println("error with files");
        }
        return null;
    }

    private Object[] getMealData(ArrayList<Integer> recipes) throws IOException {
        String entry = ", recipes: [\n";
        int calories = 0;
        int fat = 0;
        int protein = 0;
        int fiber = 0;
        int carbs = 0;

        for (int i : recipes) {
            Recipe recipe = recipeDAO.getRecipe(i);
            if (recipe != null) {
                Object[] recipeData = getRecipeData(recipe.getIngredients());
                String recipeEntry = "\t{id: %d, name: %s, instructions: %s, calories: %d, fat: %d, protein: %d, fiber: %d, carbs %d},\n";
                entry += String.format(recipeEntry, recipe.getId(), recipe.getName(), recipe.getInstructions(),
                        recipeData[1], recipeData[2], recipeData[3], recipeData[4], recipeData[5]);
                calories += (Integer) recipeData[1];
                fat += (Integer) recipeData[2];
                protein += (Integer) recipeData[3];
                fiber += (Integer) recipeData[4];
                carbs += (Integer) recipeData[5];
            }
        }

        entry += "]},\n";

        Object[] entryData = new Object[] { entry, calories, fat, protein, fiber, carbs };
        return entryData;
    }

    public String getMeals() {
        try {
            Meal[] meals = mealDAO.getMeals();
            String entry = "Meals: \n";

            for (int i = 0; i < meals.length; i++) {
                String mealEntry = "{id: %d, name: %s, calories: %d, fat: %d, protein: %d, fiber: %d, carbs %d";
                Meal meal = meals[i];
                Object[] recipeData = getMealData(meal.getRecipes());

                mealEntry = String.format(mealEntry, meal.getId(), meal.getName(), recipeData[1], recipeData[2],
                        recipeData[3], recipeData[4], recipeData[5]);

                entry += mealEntry + recipeData[0];
            }

            return entry;
        } catch (

        IOException e) {
            System.out.println("error with files");
        }
        return null;
    }

    public abstract Object takeArgument(String argument);

    public static void main(String[] args) {
        System.out.println(isArgumentValid("history update 1,rqwrewq,35353"));
    }
}
