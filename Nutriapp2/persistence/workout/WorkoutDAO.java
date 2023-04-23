package persistence.workout;

import java.io.IOException;

import workout.Workout;

public interface WorkoutDAO {

    /**
     * Retrieves all Workout
     * 
     * @return An array of Workout objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Workout[] getWorkouts() throws IOException;

    /**
     * Retrieves a Workout with the given id
     * 
     * @param id The id of the Workout to get
     * 
     * @return a Workout object with the matching id
     *         null if no Workout with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Workout getWorkout(int id) throws IOException;

    /**
     * Retrieves a Workout with the given id
     * 
     * @param id The id of the Workout to get
     * 
     * @return a Workout object with the matching id
     *         null if no Workout with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Workout getWorkoutByUserId(int userId) throws IOException;

    /**
     * Creates and saves a Workout
     * 
     * @param Workout Workout object to be created and saved
     *                The id of the Workout object is ignored and a new uniqe id is
     *                assigned
     *
     * @return new Workout Workout if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Workout createWorkout(Workout Workout) throws IOException;

    /**
     * Updates and saves a Workout
     * 
     * @param Workout object to be updated and saved
     * @return updated Workout if successful, null if
     *         Workout could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Workout updateWorkout(Workout Workout) throws IOException;

    /**
     * Deletes a Workout with the given id
     * 
     * @param id The id of the Workout}
     * 
     * @return true if the Workout was deleted
     *         false if Workout with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteWorkout(int id) throws IOException;

}
