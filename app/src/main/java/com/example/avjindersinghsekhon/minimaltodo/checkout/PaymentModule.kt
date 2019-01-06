package com.example.avjindersinghsekhon.minimaltodo.checkout

import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepository
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.PaymentRepositoryImpl
import com.example.avjindersinghsekhon.minimaltodo.checkout.repository.ReceiptService
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
    fun providePresenter(view: PaymentView): PaymentPresenter = PaymentPresenterImpl(view)

    @MutableExecutorDecorator
    @Provides
    fun provideView(decorator: MutableDecorator<PaymentView>): PaymentView = decorator.asDecorated()

    @Provides
    fun provideViewDecorator(executor: Executor): MutableDecorator<PaymentView> =
        PaymentViewDecorator(executor)

}
