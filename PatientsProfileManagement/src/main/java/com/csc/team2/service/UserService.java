package com.csc.team2.service;

import com.csc.team2.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}