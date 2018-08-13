package com.dnowakowski.transformers.main

import com.dnowakowski.transformers.injection.activity.ActivityScope
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor() {
    val events = PublishSubject.create<MainEvent>()
}

sealed class MainEvent{
}