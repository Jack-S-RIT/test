package persistence.meal;

import java.io.IOException;

import food.Meal;

public interface MealDAO {
    /**
     * Retrieves all Ingredient
     * 
     * @return An array of Ingredient objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Meal[] getMeals() throws IOException;

    /**
     * Retrieves a Meal with the given id
     * 
     * @param id The id of the Meal to get
     * 
     * @return a Meal object with the matching id
     *         null if no Meal with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Meal getMeal(int id) throws IOException;

    /**
     * Creates and saves a Meal
     * 
     * @param Meal Meal object to be created and saved
     *             The id of the Meal object is ignored and a new uniqe
     *             id is assigned
     *
     * @return new Meal Meal if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Meal createMeal(Meal Meal) throws IOException;

    /**
     * Updates and saves a Meal
     * 
     * @param Meal object to be updated and saved
     * @return updated Meal if successful, null if
     *         Meal could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Meal updateMeal(Meal Meal) throws IOException;

    /**
     * Deletes a Meal with the given id
     * 
     * @param id The id of the Meal}
     * 
     * @return true if the Meal was deleted
     *         false if Meal with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteMeal(int id) throws IOException;
}
