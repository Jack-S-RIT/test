package persistence.meal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import food.Meal;

public class MealFileDAO implements MealDAO {
    Map<Integer, Meal> meals; // Provides a local cache of the Recipe objects
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
    public MealFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
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
     * Generates an array of Meals from the hash map
     * 
     * @return The array of Meals, may be empty
     */
    private Meal[] getMealArray() {
        return getMealArray(null);
    }

    /**
     * Generates an array of Meal from the hash map for any
     * Meal that contains the text specified by containsText
     * If containsText is null, the array contains all of the Meal
     * in the hash map
     * 
     * @return The array of Meal, may be empty
     */
    private Meal[] getMealArray(String containsText) { // if containsText == null, no filter
        ArrayList<Meal> mealArrayList = new ArrayList<>();
        for (Meal i : meals.values()) {
            if (containsText == null) {
                mealArrayList.add(i);
            }
        }

        Meal[] mealArray = new Meal[mealArrayList.size()];
        mealArrayList.toArray(mealArray);
        return mealArray;
    }

    /**
     * Saves the Mealfrom the map into the file as an array of
     * JSON objects
     * 
     * @return true if the Mealwere written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Meal[] recipeArray = getMealArray();

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
        meals = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of ingredient
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Meal[] mealArray;

        mealArray = objectMapper.readValue(new File(filename), Meal[].class);

        // Add each ingredient to the hash map and keep track of the greatest id
        for (Meal i : mealArray) {
            meals.put(i.getId(), i);
            if (i.getId() > nextId)
                nextId = i.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public Meal[] getMeals() {

        return getMealArray();

    }

    public Meal getMeal(int id) {

        if (meals.containsKey(id))
            return meals.get(id);
        else
            return null;

    }

    public Meal createMeal(Meal meal) throws IOException {

        // We create a new Meal object because the id field is immutable
        // and we need to assign the next unique id
        int newID = nextId();
        Meal meal2 = new Meal(newID, meal.getName());
        meals.put(newID, meal2);
        save(); // may throw an IOException
        return meal2;

    }

    public Meal updateMeal(Meal meal) throws IOException {

        if (meals.containsKey(meal.getId()) == false)
            return null; // User does not exist
        meals.put(meal.getId(), meal);
        save(); // may throw an IOException
        return meal;

    }

    public boolean deleteMeal(int id) throws IOException {

        if (meals.containsKey(id)) {
            meals.remove(id);
            return save();
        } else
            return false;

    }

    public static void main(String[] args) {
        try {
            MealFileDAO mFD = new MealFileDAO("Nutriapp2/data/meals.json", new ObjectMapper());
            Meal m = new Meal(1, "big meal");
            m.addRecipe(1);
            m.addRecipe(2);
            mFD.updateMeal(m);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
