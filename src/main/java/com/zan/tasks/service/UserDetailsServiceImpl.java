package com.zan.tasks.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zan.tasks.dao.UserDAO;
import com.zan.tasks.model.Role;
import com.zan.tasks.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username);
		
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
	}
}
