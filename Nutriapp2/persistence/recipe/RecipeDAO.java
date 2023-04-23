package persistence.recipe;

import java.io.IOException;

import food.Recipe;

public interface RecipeDAO {
    /**
     * Retrieves all Ingredient
     * 
     * @return An array of Ingredient objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe[] getRecipes() throws IOException;

    /**
     * Retrieves a Recipe with the given id
     * 
     * @param id The id of the Recipe to get
     * 
     * @return a Recipe object with the matching id
     *         null if no Recipe with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe getRecipe(int id) throws IOException;

    /**
     * Creates and saves a Recipe
     * 
     * @param Recipe Recipe object to be created and saved
     *                   The id of the Recipe object is ignored and a new uniqe
     *                   id is assigned
     *
     * @return new Recipe Recipe if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Recipe createRecipe(Recipe recipe) throws IOException;

    /**
     * Updates and saves a Recipe
     * 
     * @param Recipe object to be updated and saved
     * @return updated Recipe if successful, null if
     *         Recipe could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Recipe updateRecipe(Recipe recipe) throws IOException;

    /**
     * Deletes a Recipe with the given id
     * 
     * @param id The id of the Recipe}
     * 
     * @return true if the Recipe was deleted
     *         false if Recipe with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteRecipe(int id) throws IOException;
}
