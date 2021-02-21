package com.shadows.bitsodream.domain.models

//Base class to be able to handle states with View Model
sealed class Resource<out T>() {

    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()

}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}