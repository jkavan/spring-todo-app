package jkavan.todo.web;

import jkavan.todo.domain.User;
import jkavan.todo.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used by Spring Security to authenticate and authorize an user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository repository;

  @Autowired
  public UserDetailServiceImpl(UserRepository userRepository) {
    this.repository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User curruser = repository.findByUsername(username);
    UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(curruser.getRole()));
    return user;
  }
}