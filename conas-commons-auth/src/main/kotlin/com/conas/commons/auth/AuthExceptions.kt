package com.conas.commons.auth

import org.springframework.security.core.AuthenticationException


class UnauthorizedException :
    AuthenticationException("Unauthorized")

class MissingAuthorizationHeaderException():
    AuthenticationException("Missing \"Authorization\" header")

class InvalidAuthorizationHeader:
    AuthenticationException("Invalid \"Authorization\" header")
