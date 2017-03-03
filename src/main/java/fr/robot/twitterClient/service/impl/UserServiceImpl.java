package fr.robot.twitterClient.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.robot.twitterClient.service.UserService;
import fr.robot.twitterClient.service.exceptions.UserAlreadyExistException;
import fr.robot.twitterClient.dataaccess.RoleRepository;
import fr.robot.twitterClient.dataaccess.UserRepository;
import fr.robot.twitterClient.dataaccess.entity.User;
import fr.robot.twitterClient.web.dto.UserDto;
 
@Service
public class UserServiceImpl implements UserService{

	@Autowired private UserRepository repo;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException(
					"There is an account with that email adress: "
							+ accountDto.getEmail());
		}
		final User user = new User();

		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		user.setEnabled(true);
		return repo.save(user);
	}

	public void saveRegisteredUser(User user) {
		repo.save(user);
		
	}

	public void deleteUser(User user) {
		repo.delete(user);
		
	}

	public User findUserByEmail(String email) {
		return repo.findByEmail(email);
	}

	public User getUserByID(long id) {
		return repo.getOne(id);
	}

	public void changeUserPassword(User user, String password) {
		// TODO Auto-generated method stub
		
	}

	public boolean checkIfValidOldPassword(User user, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	
//==  NON API
	private boolean emailExist(final String email) {
		return repo.findByEmail(email) != null;
	}

}
