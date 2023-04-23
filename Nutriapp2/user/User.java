package user;

public class User {

    private int id;

    private String username;

    private String password;

    private String name;

    private int height;

    private int weight;

    private String birthDate;

    public User(int id, String username, String password, String name, int height, int weight, String birthDate) {
        this.id = id;
        this.username = username;
        this.password = hash(password);
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthDate = birthDate;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = hash(password);
    }

    public User() {
    }

    public static String hash(String str) {
        return String.valueOf(str.hashCode());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", name='" + getName() + "'" +
                ", height='" + getHeight() + "'" +
                ", weight='" + getWeight() + "'" +
                ", birthDate='" + getBirthDate() + "'" +
                "}";
    }

}
