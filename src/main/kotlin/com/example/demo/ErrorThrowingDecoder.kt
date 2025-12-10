package com.example.demo

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import java.text.ParseException

class ErrorThrowingDecoder: JwtDecoder {
    override fun decode(token: String?): Jwt {
        throw ParseException("example",0)
    }
}