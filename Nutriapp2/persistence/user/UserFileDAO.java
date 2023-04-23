package persistence.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import user.User;

// Implements the functionality for JSON file-based peristance for users

public class UserFileDAO implements UserDAO {
    Map<Integer, User> users; // Provides a local cache of the User objects
                              // so that we don't need to read from the file
                              // each time
    private ObjectMapper objectMapper; // Provides conversion between User
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new User
    private String filename; // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the users from the file
    }

    /**
     * Generates the next id for a new { User User}
     * 
     * @return The next id
     */
    private static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of { User users} from the Hash map
     * 
     * @return The array of {User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {User users} from the Hash map for any
     * { User users} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {User
     * users}
     * in the Hash map
     * 
     * @return The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> UserArrayList = new ArrayList<>();

        for (User User : users.values()) {
            if (containsText == null || User.getUsername().contains(containsText)) {
                UserArrayList.add(User);
            }
        }

        User[] UserArray = new User[UserArrayList.size()];
        UserArrayList.toArray(UserArray);
        return UserArray;
    }

    /**
     * Saves the {User users} from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the {User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] UserArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), UserArray);
        return true;
    }

    /**
     * Loads {User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * @throws DatabindException
     * @throws StreamReadException
     * 
     * @throws IOException         when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] UserArray;

        UserArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each User to the Hash map and keep track of the greatest id
        for (User User : UserArray) {
            users.put(User.getId(), User);
            if (User.getId() > nextId)
                nextId = User.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public User[] getUsers() {

        return getUsersArray();

    }

    public User getUser(int id) {

        if (users.containsKey(id))
            return users.get(id);
        else
            return null;

    }

    public User createUser(User user) throws IOException {

        // We create a new User object because the id field is immutable
        // and we need to assign the next unique id
        int newID = nextId();
        User newUser = new User(newID, user.getUsername(), user.getPassword());
        users.put(newID, newUser);
        save(); // may throw an IOException
        return newUser;

    }

    public User loginUser(User user) throws IOException {
        for (User element : getUsersArray(user.getUsername())) {
            if (element.getUsername().equals(user.getUsername()) && element.getPassword().equals(user.getPassword())) {
                return element;
            }
        }
        // createUser(user);
        System.out.println("uDAO 172");
        return null;
    }

    public User updateUser(User User) throws IOException {

        if (users.containsKey(User.getId()) == false)
            return null; // User does not exist
        users.put(User.getId(), User);
        save(); // may throw an IOException
        return User;

    }

    public boolean deleteUser(int id) throws IOException {

        if (users.containsKey(id)) {
            users.remove(id);
            return save();
        } else
            return false;

    }

    // public static void main(String[] args) {
    // try {
    // UserFileDAO uFD = new UserFileDAO("Nutriapp2/data/users.json", new
    // ObjectMapper());
    // User jkson = new User(1, "jackson123", "password");
    // // uFD.createUser(jkson);
    // jkson.setBirthDate("2003-08-09");
    // jkson.setHeight(74);
    // jkson.setWeight(160);
    // jkson.setName("jackson");
    // uFD.updateUser(jkson);
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

}