package com.sumitthakur.walmartproductlist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.util.ImageUtil

/**
 * Recycler view to show list of products
 * @author Sumit.T
 */

class ProductsAdapter(private val activity: FragmentActivity?, private var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return if (products != null) {
            products?.size
        } else 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.productTitle)
        var price: TextView = view.findViewById(R.id.productPrice)
        var rating: TextView = view.findViewById(R.id.productRating)
        var productImage: ImageView = view.findViewById(R.id.productImage)

    }

    fun setProducts(products: List<Product>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = 20
        with(products[position]) {
            holder.productImage.tag = position
            activity?.apply {
                productImage?.apply {
                    ImageUtil().loadImage(activity, holder.productImage, imageUrl = this)
                }
            }
            holder.title.setText(productName)
            holder.price.setText(context?.getString(R.string.dollar, price))
            holder.rating.setText(context?.getString(R.string.rating, reviewRating))
        }
        holder.itemView.setOnClickListener {
            listener?.apply {
                onItemClick(products[position])
            }
        }
    }
}