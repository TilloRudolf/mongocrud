package com.mongotest.controller;

import com.mongotest.model.User;
import com.mongotest.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Метод возвращает список подьзователей")
	@GetMapping
	public List<User> getUsers(){
		return userService.getUsers();
	}

	@ApiOperation(value = "Метод возвращает пользователя по id")
	@GetMapping(value = "/{id}")
	public User getUserById(@PathVariable String id){
		return userService.getUser(id);
	}

	@ApiOperation(value = "Метод вносит изменения в информацию о пользователе")
	@PostMapping
	public String addUser(@RequestBody User user){
		return userService.update(user);
	}

	@ApiOperation(value = "Метод удаляет польхователя по id")
	@DeleteMapping(value = "/{id}")
	public String deleteUser(@PathVariable String id){
		return userService.remove(id);
	}
}