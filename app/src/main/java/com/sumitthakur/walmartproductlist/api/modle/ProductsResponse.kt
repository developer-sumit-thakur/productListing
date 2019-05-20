package com.sumitthakur.walmartproductlist.api.modle

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductsResponse {

    @JsonProperty("products")
    var products: List<Product> = ArrayList()

}