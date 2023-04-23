package food;

/**
 * abstract class that defines state and behavior for food items, acts as the
 * component in the composite pattern
 * 
 * @author Jackson Shortell
 */
public abstract class Food {

    protected int id;

    protected String name;

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Food() {
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}