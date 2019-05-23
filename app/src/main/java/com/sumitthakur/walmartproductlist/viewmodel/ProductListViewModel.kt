package com.sumitthakur.walmartproductlist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sumitthakur.walmartproductlist.api.BackendService
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.api.modle.ProductsResponse
import com.sumitthakur.walmartproductlist.testing.OpenForTesting
import io.reactivex.observers.DisposableObserver

/**
 * ViewModel class to load data from backend
 * and show it on UI
 * @author Sumit.T
 */

@OpenForTesting
open class ProductListViewModel : ViewModel() {
    companion object {
        private val TAG: String = ProductListViewModel::class.java.simpleName
    }

    lateinit var backendService: BackendService

    private var pageNumber: Int = 1
    private var pageSize: Int = 10 //default load size

    var productLiveData: MutableLiveData<List<Product>>? = null

    fun loadData() {
        if (productLiveData == null) {
            productLiveData = MutableLiveData()
        }
        productLiveData?.apply {
            Log.d(TAG, "loadData() pageNumber: $pageNumber & pageSize: $pageSize")
            backendService = BackendService.newInstance()
            backendService.initService()
            backendService.getProducts(pageNumber, pageSize).subscribe(object : DisposableObserver<ProductsResponse>() {
                override fun onComplete() {}

                override fun onNext(response: ProductsResponse) {
                    if (response != null) {
                        Log.d(TAG, "Response : " + Gson().toJson(response.toString()))
                        productLiveData?.postValue(response.products)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "Error  ${e.message}")
                    productLiveData?.postValue(null)
                }
            })
        }
    }

    fun loadMore(pageNumber: Int) {
        this.pageNumber = pageNumber
        loadData()
    }
}