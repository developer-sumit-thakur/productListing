package com.sumitthakur.walmartproductlist.api

import com.sumitthakur.walmartproductlist.api.modle.ProductsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceInterface {
    @GET("/walmartproducts/{pageNumber}/{pageSize}")
    fun getProductlist(@Path("pageNumber") pageNumber: String, @Path("pageSize") pageSize: String): Observable<ProductsResponse>
}