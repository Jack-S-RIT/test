package persistence.ingredient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import food.Ingredient;

public class IngredientFileDAO implements IngredientDAO {

    Map<Integer, Ingredient> ingredients; // Provides a local cache of the ingredient objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between User
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
    public IngredientFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
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
     * Generates an array of ingredients from the Hash map
     * 
     * @return The array of ingredients, may be empty
     */
    private Ingredient[] getIngredientArray() {
        return getIngredientArray(null);
    }

    /**
     * Generates an array of ingredient from the Hash map for any
     * ingredient that contains the text specified by containsText
     * If containsText is null, the array contains all of the ingredient
     * in the Hash map
     * 
     * @return The array of ingredient, may be empty
     */
    private Ingredient[] getIngredientArray(String containsText) { // if containsText == null, no filter
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        for (Ingredient i : ingredients.values()) {
            if (containsText == null) {
                ingredientArrayList.add(i);
            }
        }

        Ingredient[] ingredientArray = new Ingredient[ingredientArrayList.size()];
        ingredientArrayList.toArray(ingredientArray);
        return ingredientArray;
    }

    /**
     * Saves the ingredient from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the ingredient were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Ingredient[] ingredientArray = getIngredientArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), ingredientArray);
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
        ingredients = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of ingredient
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Ingredient[] ingredientArray;

        ingredientArray = objectMapper.readValue(new File(filename), Ingredient[].class);

        // Add each ingredient to the Hash map and keep track of the greatest id
        for (Ingredient i : ingredientArray) {
            ingredients.put(i.getId(), i);
            if (i.getId() > nextId)
                nextId = i.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public Ingredient[] getIngredients() {

        return getIngredientArray();

    }

    public Ingredient getIngredient(int id) {

        if (ingredients.containsKey(id))
            return ingredients.get(id);
        else
            return null;

    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) throws IOException {

        // We create a new ingredient object because the id field is immutable
        // and we need to assign the next unique id
        int newID = nextId();
        Ingredient ingredient2 = new Ingredient(newID, ingredient.getName(), ingredient.getCalories(),
                ingredient.getFat(), ingredient.getProtein(), ingredient.getFiber(), ingredient.getCarbs());
        ingredients.put(newID, ingredient2);
        save(); // may throw an IOException
        return ingredient2;

    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) throws IOException {

        if (ingredients.containsKey(ingredient.getId()) == false)
            return null; // User does not exist
        ingredients.put(ingredient.getId(), ingredient);
        save(); // may throw an IOException
        return ingredient;

    }

    @Override
    public boolean deleteIngredient(int id) throws IOException {

        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return save();
        } else
            return false;

    }

    public static void main(String[] args) {
        try {
            IngredientFileDAO iFD = new IngredientFileDAO("Nutriapp2/data/ingredients.json", new ObjectMapper());
            Ingredient i = new Ingredient(1, "egg", 5, 10, 15, 20, 30);
            iFD.createIngredient(i);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
