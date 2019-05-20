package com.sumitthakur.walmartproductlist.api

import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.api.modle.ProductsResponse
import com.sumitthakur.walmartproductlist.testing.OpenClass
import com.sumitthakur.walmartproductlist.testing.OpenForTesting
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@OpenForTesting
open class BackendService {
    companion object {
        const val BASE_URL = "https://mobile-tha-server.firebaseapp.com"

        lateinit var dataService: ServiceInterface
        var instance: BackendService? = null

        fun newInstance(): BackendService {
            if (instance == null) instance = BackendService()
            return instance as BackendService
        }
    }

    fun initService() {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        dataService = retrofit.create<ServiceInterface>(ServiceInterface::class.java)
    }

    fun getProducts(pageNumber: Int, pageSize: Int, responseListener: ResponseListener): Observable<List<Product>>? {
        var retVal: Observable<List<Product>>? = null

        try {
            dataService.getProductlist(pageNumber.toString(), pageSize.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<ProductsResponse>() {
                    override fun onComplete() {}

                    override fun onNext(value: ProductsResponse) {
                        if (responseListener != null) {
                            responseListener.onSuccess(value)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (responseListener != null) {
                            responseListener.onError("error: ${e.message}")
                        }
                    }
                })
        } catch (ex: Exception) {
            if (responseListener != null) {
                responseListener.onError("error: ${ex.message}")
            }
        }

        return retVal
    }

    interface ResponseListener {
        fun onSuccess(productList: ProductsResponse)
        fun onError(errorMsg: String)
    }
}