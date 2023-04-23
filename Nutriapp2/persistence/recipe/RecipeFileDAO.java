package persistence.recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import food.Recipe;

public class RecipeFileDAO implements RecipeDAO {

    Map<Integer, Recipe> recipes; // Provides a local cache of the Recipe objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Recipe
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new User
    private String filename; // Filename to read from and write to

    /**
     * Creates a ingredient File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public RecipeFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the goals from the file
    }

    /**
     * Generates the next id for a new ingredient
     * 
     * @return The next id
     */
    private static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of Recipes from the Hash map
     * 
     * @return The array of Recipes, may be empty
     */
    private Recipe[] getRecipeArray() {
        return getRecipeArray(null);
    }

    /**
     * Generates an array of Recipe from the Hash map for any
     * Recipe that contains the text specified by containsText
     * If containsText is null, the array contains all of the Recipe
     * in the Hash map
     * 
     * @return The array of Recipe, may be empty
     */
    private Recipe[] getRecipeArray(String containsText) { // if containsText == null, no filter
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        for (Recipe i : recipes.values()) {
            if (containsText == null) {
                recipeArrayList.add(i);
            }
        }

        Recipe[] recipeArray = new Recipe[recipeArrayList.size()];
        recipeArrayList.toArray(recipeArray);
        return recipeArray;
    }

    /**
     * Saves the Recipe from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the Recipe were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Recipe[] recipeArray = getRecipeArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), recipeArray);
        return true;
    }

    /**
     * Loads ingredients from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * @throws DatabindException
     * @throws StreamReadException
     * 
     * @throws IOException         when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        recipes = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of ingredient
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Recipe[] recipeArray;

        recipeArray = objectMapper.readValue(new File(filename), Recipe[].class);

        // Add each ingredient to the Hash map and keep track of the greatest id
        for (Recipe i : recipeArray) {
            recipes.put(i.getId(), i);
            if (i.getId() > nextId)
                nextId = i.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public Recipe[] getRecipes() {

        return getRecipeArray();

    }

    public Recipe getRecipe(int id) {

        if (recipes.containsKey(id))
            return recipes.get(id);
        else
            return null;

    }

    @Override
    public Recipe createRecipe(Recipe recipe) throws IOException {

        // We create a new Recipe object because the id field is immutable
        // and we need to assign the next unique id
        int newID = nextId();
        Recipe recipe2 = new Recipe(newID, recipe.getName(), recipe.getInstructions());
        recipes.put(newID, recipe2);
        save(); // may throw an IOException
        return recipe2;

    }

    @Override
    public Recipe updateRecipe(Recipe recipe) throws IOException {

        if (recipes.containsKey(recipe.getId()) == false)
            return null; // User does not exist
        recipes.put(recipe.getId(), recipe);
        save(); // may throw an IOException
        return recipe;

    }

    @Override
    public boolean deleteRecipe(int id) throws IOException {

        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return save();
        } else
            return false;

    }

    public static void main(String[] args) {
    try {
    RecipeFileDAO rFD = new RecipeFileDAO("Nutriapp2/data/recipes.json", new
    ObjectMapper());
    Recipe r = new Recipe(1, "food", "cook for 30 min");
    r.addIngredient(1, 2);
    r.addIngredient(2, 4);
    r.addIngredient(7, 1);
    rFD.updateRecipe(r);
    } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }

    }

}
