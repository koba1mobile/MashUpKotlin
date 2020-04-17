package com.example.gitsearch.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager (
    private val activity: AppCompatActivity,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val isClearOnStop: Boolean = true
): LifecycleObserver{
    fun add(disposable: Disposable){
        // 만약 check 결과가 false이면 IllegalException이 발생
        check(activity.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))

        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun cleanUp(){
        if(!isClearOnStop && !activity.isFinishing){
            return
        }

        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachSelf(){
        compositeDisposable.clear()

        activity.lifecycle.removeObserver(this)
    }

}