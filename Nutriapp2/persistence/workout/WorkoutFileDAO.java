package persistence.workout;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import workout.Workout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class WorkoutFileDAO {

    Map<Integer, Workout> workouts; // Provides a local cache of the workout objects
                                    // so that we don't need to read from the file
                                    // each time
    private ObjectMapper objectMapper; // Provides conversion between User
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new User
    private String filename; // Filename to read from and write to

    /**
     * Creates a workout File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public WorkoutFileDAO(String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the workouts from the file
    }

    /**
     * Generates the next id for a new workout
     * 
     * @return The next id
     */
    private static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of Workouts from the hash map
     * 
     * @return The array of Workouts, may be empty
     */
    private Workout[] getWorkoutsArray() {
        return getWorkoutsArray(null);
    }

    /**
     * Generates an array of Workout from the hash map for any
     * Workout that contains the text specified by containsText
     * If containsText is null, the array contains all of the Workouts
     * in the hash map
     * 
     * @return The array of Workouts, may be empty
     */
    private Workout[] getWorkoutsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Workout> workoutArrayList = new ArrayList<>();
        for (Workout workout : workouts.values()) {
            if (containsText == null) {
                workoutArrayList.add(workout);
            }
        }

        Workout[] workoutArray = new Workout[workoutArrayList.size()];
        workoutArrayList.toArray(workoutArray);
        return workoutArray;
    }

    /**
     * Saves the workout from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the workout were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Workout[] workoutArray = getWorkoutsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), workoutArray);
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
        workouts = new HashMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Workout
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Workout[] workoutArray;

        workoutArray = objectMapper.readValue(new File(filename), Workout[].class);

        // Add each goal to the hash map and keep track of the greatest id
        for (Workout workout : workoutArray) {
            workouts.put(workout.getId(), workout);
            if (workout.getId() > nextId)
                nextId = workout.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    public Workout[] getWorkouts() {

        return getWorkoutsArray();

    }

    public Workout getWorkout(int id) {

        if (workouts.containsKey(id))
            return workouts.get(id);
        else
            return null;

    }

    public Workout createWorkout(Workout workout) throws IOException {

        // We create a new Workout object because the id field is immutable
        // and we need to assign the next unique id

        int newID = nextId();
        Workout newWorkout = new Workout(newID, workout.getMinutes(), workout.getIntensity(), workout.getDate(),
                workout.getUserId());
        workouts.put(newID, newWorkout);
        save(); // may throw an IOException
        return newWorkout;

    }

    public Workout updateWorkout(Workout workout) throws IOException {

        if (workouts.containsKey(workout.getId()) == false)
            return null; // User does not exist
        workouts.put(workout.getId(), workout);
        save(); // may throw an IOException
        return workout;

    }

    public boolean deleteWorkout(int id) throws IOException {

        if (workouts.containsKey(id)) {
            workouts.remove(id);
            return save();
        } else
            return false;

    }

    public Workout getWorkoutByUserId(int userId) throws IOException {
        for (Workout workout : workouts.values()) {
            if (workout.getUserId() == userId) {
                return workout;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            WorkoutFileDAO wFD = new WorkoutFileDAO("Nutriapp2/data/workouts.json", new ObjectMapper());
            Workout wK = new Workout(1, 5, "high", "2023-09-08", 1);
            wFD.createWorkout(wK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
