package food;

import java.util.HashMap;

/**
 * child class of Food that defines state and behavior for a recipe, acts
 * as a composite in the composite pattern
 * 
 * @author Jackson Shortell
 */
public class Recipe extends Food {

    private String instructions;

    /**
     * hash map where the keys are the ingredients ids and the values are the how
     * many
     * times that ingredient is used in the recipe aka the quantity
     */
    private HashMap<Integer, Integer> ingredients;

    public Recipe(int id, String name, String instructions) {
        super(id, name);
        instructions = this.instructions;
        ingredients = new HashMap<>();
    }

    public Recipe() {
        super();
    }

    public HashMap<Integer, Integer> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(String name) {
        this.name = name;
    }

    /**
     * adds an ingredient and associated quantity to the recipes ingredients
     * then updates recipe info
     * 
     * @param ingredientId Ingredient id
     * @param quantity     int
     */
    public void addIngredient(int ingredientId, int quantity) {
        if (quantity > 0) {
            ingredients.put(ingredientId, quantity);

        }
    }

    /**
     * removes an ingredient from recipes ingredients
     * then updates recipe info
     * 
     * @param ingredientId Ingredient id
     */
    public void removeIngredient(int ingredientId) {
        ingredients.remove(ingredientId);
    }

}