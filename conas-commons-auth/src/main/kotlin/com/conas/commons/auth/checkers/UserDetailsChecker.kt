package com.conas.commons.auth.checkers

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
open class UserDetailsChecker : Checker<UserDetails> {

    override fun check(checkObject: UserDetails) {
        if (!checkObject.isAccountNonLocked) {
            throw LockedException("User locked")
        }
        if (!checkObject.isEnabled) {
            throw DisabledException("User disabled")
        }
        if (!checkObject.isAccountNonExpired) {
            throw AccountExpiredException("Account expired")
        }
        if (!checkObject.isCredentialsNonExpired) {
            throw CredentialsExpiredException("Credentials expired")
        }
    }
}