package persistence.ingredient;

import java.io.IOException;

import food.Ingredient;




public interface IngredientDAO {
    /**
     * Retrieves all Ingredient 
     * 
     * @return An array of  Ingredient objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient[] getIngredients() throws IOException;

    /**
     * Retrieves a  Ingredient with the given id
     * 
     * @param id The id of the  Ingredient to get
     * 
     * @return a  Ingredient object with the matching id
     * null if no  Ingredient with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient getIngredient(int id) throws IOException;

    /**
     * Creates and saves a  Ingredient
     * 
     * @param ingredient  Ingredient object to be created and saved
     * The id of the Ingredient object is ignored and a new uniqe id is assigned
     *
     * @return new  Ingredient Ingredient if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Ingredient createIngredient(Ingredient ingredient) throws IOException;

    /**
     * Updates and saves a Ingredient 
     * 
     * @param ingredient object to be updated and saved
     * @return updated  Ingredient if successful, null if
     *  Ingredient could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Ingredient updateIngredient(Ingredient ingredient) throws IOException;

    /**
     * Deletes a  Ingredient with the given id
     * 
     * @param id The id of the  Ingredient}
     * 
     * @return true if the Ingredient was deleted
     * false if Ingredient with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteIngredient(int id) throws IOException;
}

