package id.my.mufidz.graphcountry.utils

sealed class DataResult<out T> {
    data class Success<T>(val value: T) : DataResult<T>()
    data class Failure(val exception: Exception) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $value]"
            is Failure -> "Failure[exception = $exception]"
        }
    }
}