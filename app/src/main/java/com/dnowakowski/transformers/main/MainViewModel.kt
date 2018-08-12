package com.dnowakowski.transformers.main

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor() {
    val events = PublishSubject.create<MainEvent>()
}

sealed class MainEvent{
}