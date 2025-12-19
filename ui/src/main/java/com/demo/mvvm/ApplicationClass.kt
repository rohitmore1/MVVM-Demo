package com.demo.mvvm

import android.app.Application
import android.content.Context
import com.demo.domain.feature.domainService.useCase.InitializeApiServiceUseCase
import com.demo.domain.model.ApiServiceParameters
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ApplicationClass : Application() {

    private var mContext: Context? = null

    @Inject
    lateinit var initializeApiServiceUseCase: InitializeApiServiceUseCase

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        mContext = this

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            initializeApiServiceUseCase
                .execute(ApiServiceParameters())
                ?.collect {
                    Timber.e("Init result: $it")
                }
        }
    }

    private fun initializeTimber() {
        Timber.Forest.uprootAll()
        Timber.Forest.plant(Timber.DebugTree())
    }


}