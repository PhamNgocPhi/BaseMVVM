package com.example.basemvvm.utils.bus.event

import com.example.basemvvm.utils.bus.action.BackAction
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class BackEvent {
    private var sPublishSubject: PublishSubject<BackAction>? =
        null

    fun publish(action: BackAction) {
        sPublishSubject!!.onNext(action)
    }

    fun subscribe(sub: Consumer<BackAction>?): Disposable {
        return sPublishSubject!!.subscribe(sub)
    }

    companion object {
        
        const val REQUEST_CODE_BACK_FRAGMENT_COMMON = 1001234535 + 1
        
        private var sInstance: BackEvent? = null
        val instance: BackEvent?
            get() {
                if (sInstance == null) {
                    sInstance = BackEvent()
                    sInstance!!.sPublishSubject =
                        PublishSubject.create()
                }
                return sInstance
            }
    }
}