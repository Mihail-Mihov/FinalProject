package com.example.myproject.config;

import com.example.myproject.service.OfferService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private OfferService offerService;
    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }


    public boolean hasPrivileges(Long id) {
        String userName = currentUserName();
        if (userName != null) {
            return offerService.hasPrivileges(userName, id);
        }
        return false;
    }


    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    public String currentUserName() {
        Authentication auth = getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails)auth.getPrincipal()).getUsername();
        }
        return null;
    }

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
