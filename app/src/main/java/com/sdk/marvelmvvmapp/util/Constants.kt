package com.sdk.marvelmvvmapp.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

object Constants {
    const val BASE_URL = "https://gateway.marvel.com/"
    val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
    const val API_KEY = "b75c293e02f2d1216aaa7ce1ccda14d2"
    private const val PRIVATE_KEY = "7237ec4076b2114952c7a22a1c8e5390c66eb8d5"
    const val limit = "20"
    fun hash(): String {
        val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}