package com.credit.service.serviceimpl;

import com.credit.repository.UserRepository;
import com.credit.util.UserVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserDetailsService")
public class SecurityUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        com.credit.pojo.User user = userRepository.getUserByUserCode(userCode);
        if (user == null) {
            throw new UsernameNotFoundException("不存在该用户！");

        }
        String userStatus = String.valueOf(user.getUserStatus());
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(userStatus);

        return new UserVerify(user.getUserId(),user.getUserCode(),user.getUserPassword(),user.getUserName(),user.getUserFaculty(), auths);

    }
}
