package com.example.avjindersinghsekhon.minimaltodo.checkout

import dagger.Subcomponent

@Subcomponent(modules = [(PaymentModule::class)])
interface PaymentComponent {
    fun inject(activity: PaymentActivity)
}
