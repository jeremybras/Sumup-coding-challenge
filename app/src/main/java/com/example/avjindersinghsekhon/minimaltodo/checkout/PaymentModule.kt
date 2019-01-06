package com.example.avjindersinghsekhon.minimaltodo.checkout

import android.content.res.Resources
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepository
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepositoryImpl
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.ReceiptService
import com.example.avjindersinghsekhon.minimaltodo.common.DecimalFormatter
import com.example.avjindersinghsekhon.minimaltodo.common.FeatureScope
import com.example.avjindersinghsekhon.minimaltodo.common.MainThreadExecutor
import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator
import com.nicolasmouchel.executordecorator.MutableDecorator
import com.nicolasmouchel.executordecorator.MutableExecutorDecorator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.concurrent.Executor

@Module
class PaymentModule {

    @ImmutableExecutorDecorator
    @Provides
    fun provideController(interactor: PaymentInteractor, executor: Executor): PaymentController =
        PaymentControllerDecorator(executor, PaymentControllerImpl(interactor))

    @Provides
    fun provideInteractor(
        presenter: PaymentPresenter,
        repository: PaymentRepository
    ): PaymentInteractor = PaymentInteractor(presenter, repository)

    @Provides
    fun provideRepository(retrofit: Retrofit): PaymentRepository = PaymentRepositoryImpl(
        retrofit.create(ReceiptService::class.java)
    )

    @Provides
    fun providePresenter(
        view: PaymentView,
        resources: Resources
    ): PaymentPresenter =
        PaymentPresenterImpl(view, resources, DecimalFormatter())

    @MutableExecutorDecorator
    @Provides
    fun provideView(decorator: MutableDecorator<PaymentView>): PaymentView = decorator.asDecorated()

    @FeatureScope
    @Provides
    fun provideViewDecorator(executor: MainThreadExecutor): MutableDecorator<PaymentView> =
        PaymentViewDecorator(executor)

}
