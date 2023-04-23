package persistence.goal;

import java.io.IOException;

import goal.Goal;

public interface GoalDAO {
    /**
     * Retrieves all Goals
     * 
     * @return An array of Goal objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Goal[] getGoals() throws IOException;


    /**
     * Retrieves a Goal with the given id
     * 
     * @param id The id of the Goal to get
     * 
     * @return a  Goal object with the matching id
     * null if no User with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Goal getGoal(int id) throws IOException;


    /**
     * Retrieves a Goal with the given user id
     * 
     * @param id The user id of the Goal to get
     * 
     * @return a  Goal object with the matching user id
     * null if no User with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Goal getGoalByUserId(int userId) throws IOException;

    /**
     * Creates and saves a Goal
     * 
     * @param Goal  Goal object to be created and saved
     * The id of the Goal object is ignored and a new uniqe id is assigned
     *
     * @return new  Goal Goal if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Goal createGoal(Goal goal) throws IOException;

    

    /**
     * Updates and saves a Goal 
     * 
     * @param Goal object to be updated and saved
     * @return updated  Goal if successful, null if
     *  Goal could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Goal updateGoal(Goal goal) throws IOException;

    /**
     * Deletes a  goal with the given id
     * 
     * @param id The id of the  goal}
     * 
     * @return true if the goal was deleted
     * false if goal with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteGoal(int id) throws IOException;
}

