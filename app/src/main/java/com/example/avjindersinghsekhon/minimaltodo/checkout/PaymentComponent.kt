package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.example.avjindersinghsekhon.minimaltodo.common.FeatureScope
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [(PaymentModule::class)])
interface PaymentComponent {
    fun inject(activity: PaymentActivity)
}
