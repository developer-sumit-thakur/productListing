package com.sumitthakur.walmartproductlist.api.modle

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * data class
 * @author sumit.T
 */
//parcel: Parcel) : Parcelable {

@JsonIgnoreProperties(ignoreUnknown = true)
class Product(
    var longDescription: String?,
    var productImage: String?,
    var productId: String?,
    var reviewCount: String?,
    var price: String?,
    var inStock: Boolean,
    var shortDescription: String?,
    var reviewRating: String?,
    var productName: String?
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt() == 1,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    /*fun getProduct(title: String, price: String, imageUrl: String, rating: String): Product {
        this.productName = title
        this.price = price
        this.productImage = imageUrl
        this.reviewRating = rating
        return this
    }

    init {
        longDescription = parcel.readString()
        productImage = parcel.readString()
        productId = parcel.readString()
        reviewCount = parcel.readString()
        price = parcel.readString()
        inStock = parcel.readInt() == 1
        shortDescription = parcel.readString()
        reviewRating = parcel.readString()
        productName = parcel.readString()
    }*/


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(longDescription)
        parcel.writeString(productImage)
        parcel.writeString(productId)
        parcel.writeString(reviewCount)
        parcel.writeString(price)
        parcel.writeInt(if (inStock) 1 else 0)
        parcel.writeString(shortDescription)
        parcel.writeString(reviewRating)
        parcel.writeString(productName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Products [longDescription = $longDescription, productImage = $productImage, productId = $productId, reviewCount = $reviewCount, price = $price, inStock = $inStock, shortDescription = $shortDescription, reviewRating = $reviewRating, productName = $productName]"
    }
}
