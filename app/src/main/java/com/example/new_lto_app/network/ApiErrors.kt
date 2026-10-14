package com.example.new_lto_app.network

import com.google.gson.JsonParser
import retrofit2.HttpException
import java.io.IOException

// Turns a failed API call into a short message the user can understand.
object ApiErrors {

    fun message(e: Throwable): String = when (e) {
        is HttpException -> {
            val serverMessage = readServerMessage(e)
            when {
                !serverMessage.isNullOrBlank() && e.code() != 500 -> serverMessage
                e.code() == 401 -> "Wrong email or password"
                e.code() == 403 -> "This account has been deactivated"
                e.code() == 409 -> "This email is already registered"
                else -> "Server error (${e.code()}). Please try again."
            }
        }
        is IOException -> "Can't reach the server. Make sure your phone is on the same Wi-Fi as the computer and the API is running."
        else -> e.message ?: "Something went wrong"
    }

    // Spring Boot sends errors as JSON like {"status":409,"message":"Email already registered",...}
    private fun readServerMessage(e: HttpException): String? = try {
        val body = e.response()?.errorBody()?.string()
        if (body.isNullOrBlank()) null
        else JsonParser.parseString(body).asJsonObject.get("message")?.asString
    } catch (ex: Exception) {
        null
    }
}
