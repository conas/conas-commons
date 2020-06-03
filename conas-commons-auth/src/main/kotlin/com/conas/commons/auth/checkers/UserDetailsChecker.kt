package com.conas.commons.auth.checkers

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
open class UserDetailsChecker : Checker<UserDetails> {

    override fun check(user: UserDetails) {
        if (user.isAccountNonLocked == false) {
            throw LockedException("User locked")
        }
        if (user.isEnabled == false) {
            throw DisabledException("User disabled")
        }
        if (user.isAccountNonExpired == false) {
            throw AccountExpiredException("Account expired")
        }
        if (user.isCredentialsNonExpired == false) {
            throw CredentialsExpiredException("Credentials expired")
        }
    }
}