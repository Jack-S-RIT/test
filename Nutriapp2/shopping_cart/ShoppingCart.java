package shopping_cart;

import java.util.HashMap;

public class ShoppingCart {

    private int id;

    private HashMap<Integer, Integer> ingredientStock;

    private int userId;

    public ShoppingCart(int id, int userId) {
        this.id = id;
        this.ingredientStock = new HashMap<>();
        this.userId = userId;
    }

    public ShoppingCart() {
    }

    public int getId() {

        return this.id;
    }

    public HashMap<Integer, Integer> getIngredientStock() {
        return this.ingredientStock;
    }

    public int getUserId() {
        return this.userId;
    }

    public void addIngredient(int ingredient, int quantity) {
        ingredientStock.put(ingredient, quantity);
    }

    public void removeIngredient(int ingredient) {
        ingredientStock.remove(ingredient);
    }

}
