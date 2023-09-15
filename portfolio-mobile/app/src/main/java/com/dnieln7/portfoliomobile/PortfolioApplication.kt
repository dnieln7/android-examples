package com.dnieln7.portfoliomobile

import android.app.Application
import com.dnieln7.portfoliomobile.di.ServiceLocator
import timber.log.Timber

class PortfolioApplication : Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        serviceLocator = ServiceLocator(applicationContext)
    }
}