package persistence.user;

import java.io.IOException;

import user.User;




public interface UserDAO {
    /**
     * Retrieves all User 
     * 
     * @return An array of  User objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a  User with the given id
     * 
     * @param id The id of the  User to get
     * 
     * @return a  User object with the matching id
     * null if no  User with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    /**
     * Creates and saves a  User
     * 
     * @param User  User object to be created and saved
     * The id of the User object is ignored and a new uniqe id is assigned
     *
     * @return new  User User if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * Logs in an already existing User
     * 
     * @param User User object to be verified as existing
     *
     * @return current User if successful, otherwise creates new user using same informations
     * 
     * @throws IOException if an issue with underlying storage
     */
    User loginUser(User user) throws IOException;

    /**
     * Updates and saves a User 
     * 
     * @param User object to be updated and saved
     * @return updated  User if successful, null if
     *  User could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User User) throws IOException;

    /**
     * Deletes a  User with the given id
     * 
     * @param id The id of the  User}
     * 
     * @return true if the User was deleted
     * false if User with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;
}
