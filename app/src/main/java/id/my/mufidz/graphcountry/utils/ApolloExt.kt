package id.my.mufidz.graphcountry.utils

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloException

fun <T : Operation.Data> ApolloResponse<T>.awaitResult(): DataResult<T> {
    return try {
        DataResult.Success(this.dataAssertNoErrors)
    } catch (exception: ApolloException) {
        DataResult.Failure(exception)
    }
}