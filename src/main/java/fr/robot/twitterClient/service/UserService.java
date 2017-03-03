package fr.robot.twitterClient.service;


import fr.robot.twitterClient.service.exceptions.UserAlreadyExistException;
import fr.robot.twitterClient.dataaccess.entity.User;
import fr.robot.twitterClient.web.dto.UserDto;

public interface UserService{
	
    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    User findUserByEmail(String email);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

}
