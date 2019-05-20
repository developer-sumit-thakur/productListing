package com.sumitthakur.walmartproductlist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sumitthakur.walmartproductlist.api.BackendService
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.api.modle.ProductsResponse
import com.sumitthakur.walmartproductlist.testing.OpenForTesting

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
    private var pageSize: Int = 10

    var productLiveData: MutableLiveData<List<Product>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            loadData(field)
            return field
        }

    fun loadData(result: MutableLiveData<List<Product>>?) {
        result?.apply {
            Log.d(TAG, "loadData()")
            backendService = BackendService.newInstance()
            backendService.initService()
            backendService.getProducts(pageNumber, pageSize, object : BackendService.ResponseListener {
                override fun onSuccess(response: ProductsResponse) {
                    Log.d(TAG, "Response : " + Gson().toJson(response.toString()))
                    result?.postValue(response.products)
                }

                override fun onError(errorMsg: String) {
                    Log.e(TAG, errorMsg)
                    result?.postValue(null)
                }
            })
        }
    }

    fun loadMore(pageNumber: Int, pageSize: Int) {
        this.pageSize = pageSize
        this.pageNumber = pageNumber
        loadData(productLiveData)
    }
}