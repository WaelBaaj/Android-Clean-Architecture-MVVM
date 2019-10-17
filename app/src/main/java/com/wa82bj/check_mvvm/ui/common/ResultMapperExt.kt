package com.wa82bj.check_mvvm.ui.common

import androidx.annotation.CheckResult
import com.wa82bj.check_mvvm.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single

@CheckResult
fun <T> Flowable<T>.toResult(schedulerProvider: SchedulerProvider):
        Flowable<Result<T>> {
    return compose { item ->
        item
            .map {
                return@map Result.success(it)
            }
            .onErrorReturn { e ->
                Result.failure(
                    e.message ?: "unknown", e
                )
            }
            .observeOn(schedulerProvider.ui())
            .startWith(Result.inProgress())
    }
}

@CheckResult
fun <T> Single<T>.toResult(schedulerProvider: SchedulerProvider):
        Flowable<Result<T>> {
    return toFlowable().toResult(schedulerProvider)
}