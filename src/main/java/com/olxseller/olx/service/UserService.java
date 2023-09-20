package com.olxseller.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	/*
	 * private static List<User> list=new ArrayList<>();
	 * 
	 * static { list.add(new User(1,"aj","fadfa","ada","adfa")); list.add(new
	 * User(2,"aj","fadfa","ada","adfa")); list.add(new
	 * User(3,"aj","fadfa","ada","adfa")); }
	 */

	// get all Users
	public List<User> getAllUsers() {
		List<User> list = (List<User>) this.userRepo.findAll();
		return list;
	}

	// get single user by id
	public User getUserById(int id) {
		User user = null;
		try {
			// user = list.stream().filter(e->e.getId()==id).findFirst().get();
			Optional<User> findById = this.userRepo.findById(id);
			if (findById.isPresent()) {
				user = findById.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;

	}

	// create user
	public User addUsers(User u) {
		User result = userRepo.save(u);
		return result;
	}

	// delete user
	public void deleteUsers(int id) {
		// list.stream().filter(user->user.getId()!=id).collect(Collectors.toList());
		userRepo.deleteById(id);
	}

	// update user
	public void updateUsers(User user, int id) {
		/*
		 * list = list.stream().map(u->{ if(u.getId()==id) { u.setName(user.getName());
		 * u.setEmail(user.getEmail()); u.setPhone(user.getPhone());
		 * u.setPassword(user.getPassword()); } return u;
		 * }).collect(Collectors.toList());
		 */

		user.setId(id);
		userRepo.save(user);
	}
}
