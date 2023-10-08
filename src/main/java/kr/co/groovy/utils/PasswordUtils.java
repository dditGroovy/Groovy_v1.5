package kr.co.groovy.utils;

import kr.co.groovy.security.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUtils(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isPasswordValid(String inputPassword, String encodedPassword) {
        return passwordEncoder.matches(inputPassword, encodedPassword);
    }

    public boolean isCurrentUserPasswordValid(String inputPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser user = (CustomUser) authentication.getPrincipal();
        if (user != null) {
            String encodedPassword = user.getEmployeeVO().getEmplPassword();
            return isPasswordValid(inputPassword, encodedPassword);
        }
        return false;
    }
}
