package food;

import java.util.ArrayList;

/**
 * child class of Food that defines state and behavior for a meal, acts
 * as a composite in the composite pattern
 * 
 * @author Jackson Shortell
 */
public class Meal extends Food {

    private ArrayList<Integer> recipes;

    public Meal(int id, String name) {
        super(id, name);
        recipes = new ArrayList<>();
    }

    public Meal() {
    }

    public ArrayList<Integer> getRecipes() {
        return recipes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(String name) {
        this.name = name;
    }

    /**
     * adds a recipe to the list of recipes
     * then updates meal info
     * 
     * @param recipe   Recipe id
     * @param quantity int
     */
    public void addRecipe(Integer recipeId) {
        recipes.add(recipeId);
    }

    /**
     * removes a recipe to the list of recipes
     * then updates meal info
     * 
     * @param recipe   Recipe object
     * @param quantity int
     */
    public void removeRecipe(Integer recipeId) {
        recipes.remove(recipeId);
    }

}