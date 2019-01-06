package com.example.avjindersinghsekhon.minimaltodo.common

import com.example.avjindersinghsekhon.minimaltodo.checkout.PaymentComponent
import com.example.avjindersinghsekhon.minimaltodo.checkout.PaymentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(MainModule::class), (NetworkModule::class)])
interface MainComponent {
    fun plus(module: PaymentModule): PaymentComponent
}