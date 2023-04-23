package food;

/**
 * child class of Food that defines state and behavior for an ingredient, acts
 * as a leaf in the composite pattern
 * 
 * @author Jackson Shortell
 */
public class Ingredient extends Food {

    protected int calories;

    /**
     * all measured in grams
     */
    protected int fat;

    protected int protein;

    protected int fiber;

    protected int carbs;

    public Ingredient(int id, String name, int calories, int fat, int protein, int fiber, int carbs) {
        super(id, name);
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.fiber = fiber;
        this.carbs = carbs;
    }

    public Ingredient() {
        super();
    }

    public int getCalories() {
        return calories;
    }

    public int getFat() {
        return fat;
    }

    public int getProtein() {
        return protein;
    }

    public int getFiber() {
        return fiber;
    }

    public int getCarbs() {
        return carbs;
    }

    @Override
    public String toString() {
        return "{id: " + getId() + ", name: " + getName() + ", calories: " + getCalories() + ", fat: " + getFat()
                + ", protein: " + getProtein() + ", fiber: " + getFiber() + ", carbs: " + getCarbs() + "}";
    }
}