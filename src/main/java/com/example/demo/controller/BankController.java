package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.Users;
import com.example.demo.error.InvalidException;
import com.example.demo.error.LowBalanceException;
import com.example.demo.error.NoTransactionException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.service.BankService;

@RestController
public class BankController {
	
	@Autowired
	BankService service;
	
	@PostMapping("/admin/save")
	public Users saveUser(@RequestBody Users user) {
		return service.saveUser(user);
	}
	
	@DeleteMapping("/admin/delete/{acno}")
	public String removeUser(@PathVariable long acno) throws UserNotFoundException {
		return service.removeUser(acno);
	}
	
	@PutMapping("/admin/{acno}")
	public Users updateUser(@PathVariable long acno,@RequestBody Users user) throws UserNotFoundException {
		return service.updateUser(acno, user);
	}
	
	@GetMapping("/admin/")
	public List<Users> getUsers(){
		return service.getUsers();
	}
	
	@GetMapping("/admin/user/{acno}")
	public Users getUser(@PathVariable long acno) {
		return service.getUser(acno);
	}
	
	@GetMapping("/admin/{n}")
	public List<Transaction> getTrans(@PathVariable int n) throws NoTransactionException{
		return service.getTrans(n);
	}
	
	@PutMapping("/users/update/{acno}")
	public Users updateUserPass(@PathVariable long acno, @RequestBody Users user) {
		return service.updateUserPass(acno,user);
	}
	
	@GetMapping("/users/{acno}/{n}")
	public List<Transaction> getTransactions(@PathVariable("acno") long acno, @PathVariable("n") int n) throws NoTransactionException {
		return service.getTransactions(acno, n);
	}
	
	@PutMapping("/users/{acno}")
	public String depwit(@PathVariable long acno, @RequestBody Transaction tr) throws LowBalanceException, UserNotFoundException, InvalidException {
		return service.depwit(acno, tr);
	}
	
	@GetMapping("/users/{acno}")
	public String displayBal(@PathVariable long acno) {
		return service.displayBal(acno);
	}
	
	@PutMapping("/users/{acno}/{acno1}/{amount}")
	public String transfer(@PathVariable double amount)
	{
		return service.transfer(amount);
	}
	
	
}
