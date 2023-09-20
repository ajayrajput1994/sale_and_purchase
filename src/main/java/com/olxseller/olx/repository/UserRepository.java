package com.olxseller.olx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.User;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {
	
	@Query("select u from User u where u.email= :email")
	public User getUserByUserName(@Param("email") String email);
	
	@Query(value="select * from user where role='ROLE_USER'",nativeQuery=true) 
	 public Page<User>  getAllUsers(Pageable pageable);
	/*
	 * public List<User> findById(String id);
	 * 
	 * public List<User> findByEmailAndPassword(String email,String password);
	 */
	
	/* @Query("select u from user u")
	public List<User> getAllUser();
	
	 @Query("select u from User u where u.phone=:phone and u.password=pass")
	public User getUserByUserName(@Param("ph") String phone,@Param("pass") String password); */
	
	/*
	 * @Query(value="select * from user",nativeQuery=true) public List<User>
	 * getUsers();
	 */
}
