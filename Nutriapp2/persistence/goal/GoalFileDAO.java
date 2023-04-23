package persistence.goal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import goal.Goal;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class GoalFileDAO implements GoalDAO {

    Map<Integer, Goal> goals; // Provides a local cache of the goal objects
                              // so that we don't need to read from the file
                              // each time
    private ObjectMapper objectMapper; // Provides conversion between User
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new User
    private String filename; // Filename to read from and write to

    /**
     * Creates a Goal File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public GoalFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the goals from the file
    }

    /**
     * Generates the next id for a new goal
     * 
     * @return The next id
     */
    private static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of goals from the hash map
     * 
     * @return The array of goals, may be empty
     */
    private Goal[] getGoalsArray() {
        return getGoalsArray(null);
    }

    /**
     * Generates an array of goal from the hash map for any
     * goal that contains the text specified by containsText
     * If containsText is null, the array contains all of the goals
     * in the hash map
     * 
     * @return The array of goals, may be empty
     */
    private Goal[] getGoalsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Goal> goalArrayList = new ArrayList<>();
        for (Goal goal : goals.values()) {
            if (containsText == null) {
                goalArrayList.add(goal);
            }
        }

        Goal[] goalArray = new Goal[goalArrayList.size()];
        goalArrayList.toArray(goalArray);
        return goalArray;
    }

    /**
     * Saves the goal from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the goal were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Goal[] goalArray = getGoalsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), goalArray);
        return true;
    }

    /**
     * Loads goals from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * @throws DatabindException
     * @throws StreamReadException
     * 
     * @throws IOException         when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        goals = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of goal
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Goal[] goalArray;

        goalArray = objectMapper.readValue(new File(filename), Goal[].class);

        // Add each goal to the hash map and keep track of the greatest id
        for (Goal goal : goalArray) {
            goals.put(goal.getId(), goal);
            if (goal.getId() > nextId)
                nextId = goal.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public Goal[] getGoals() {

        return getGoalsArray();

    }

    public Goal getGoal(int id) {

        if (goals.containsKey(id))
            return goals.get(id);
        else
            return null;

    }

    @Override
    public Goal createGoal(Goal goal) throws IOException {

        // We create a new goal object because the id field is immutable
        // and we need to assign the next unique id
        for (Goal data : goals.values()) {
            if (data.getUserId() == goal.getUserId()) { // checks to see if user already has a goal before adding one
                return null;
            }
        }
        int newID = nextId();
        Goal newGoal = new Goal(newID, goal.getState(), goal.getTargetWeight(), goal.getUserId());
        goals.put(newID, newGoal);
        save(); // may throw an IOException
        return newGoal;

    }

    @Override
    public Goal updateGoal(Goal goal) throws IOException {

        if (goals.containsKey(goal.getId()) == false)
            return null; // User does not exist
        goals.put(goal.getId(), goal);
        save(); // may throw an IOException
        return goal;

    }

    @Override
    public boolean deleteGoal(int id) throws IOException {

        if (goals.containsKey(id)) {
            goals.remove(id);
            return save();
        } else
            return false;

    }

    @Override
    public Goal getGoalByUserId(int userId) throws IOException {
        for (Goal goal : goals.values()) {
            if (goal.getUserId() == userId) {
                return goal;
            }
        }
        return null;
    }

}
