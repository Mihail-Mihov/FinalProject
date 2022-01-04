package com.example.myproject.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// TODO - tozi klas moje bi e nenujen zashtoto v myuserdetailsservice v mapa direktno go mapnahme kam User bez tova tuk
public class MyUser extends User {

    public MyUser(String username, String password,
                  Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
    }

    public String getUserIdentifier(){
        return this.getUsername();
    }
}
