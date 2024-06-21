package scari.corp.infoinvest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scari.corp.infoinvest.repository.UserRepository;
import scari.corp.infoinvest.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {   

    @Autowired
    private  UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException( username + "Пользователь не найден"));
        return new CustomUserDetails(userEntity);
    }
    

}
