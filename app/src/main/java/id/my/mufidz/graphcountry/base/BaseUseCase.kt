package id.my.mufidz.graphcountry.base

import id.my.mufidz.graphcountry.utils.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<RequestParam, ResponseObject, UseCaseResult> {

    protected abstract suspend fun execute(param: RequestParam?): DataResult<ResponseObject>

    protected abstract suspend fun DataResult<ResponseObject>.transformToResult(): UseCaseResult

    suspend fun getResult(param: RequestParam? = null): UseCaseResult =
        withContext(Dispatchers.IO) {
            execute(param).transformToResult()
        }

    suspend fun getDeferredResult(
        coroutineScope: CoroutineScope,
        param: RequestParam? = null
    ): UseCaseResult =
        withContext(coroutineScope.coroutineContext + Dispatchers.IO) {
            execute(param).transformToResult()
        }
}