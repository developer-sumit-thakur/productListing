package com.sumitthakur.walmartproductlist.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.testing.OpenForTesting
import com.sumitthakur.walmartproductlist.ui.adapter.ProductsAdapter
import com.sumitthakur.walmartproductlist.viewmodel.ProductListViewModel
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Class to show list of products
 * @author Sumit.T
 */

@OpenForTesting
open class HomeFragment : Fragment(), ProductsAdapter.OnItemClickListener {
    companion object {
        val TAG = HomeFragment::class.java.simpleName
    }

    lateinit var adapter: ProductsAdapter
    private var llayoutManager: RecyclerView.LayoutManager? = null
    lateinit var interactionListener: InteractionListener
    var productListViewModel: ProductListViewModel? = null
    var isLoading = false

    interface InteractionListener {
        fun onSuccess()
        fun onItemClick(product: Product)
        fun onFailure()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is InteractionListener) {
            interactionListener = activity as InteractionListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.home_fragment, container, false)
        productListViewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llayoutManager = LinearLayoutManager(activity)
        productListView.setHasFixedSize(true)
        productListView.setLayoutManager(llayoutManager)
        adapter = ProductsAdapter(activity, ArrayList())
        productListView.setAdapter(adapter)
        adapter.setOnItemClickListener(this)
        initScrollListener(productListView, llayoutManager as LinearLayoutManager)

        productListViewModel?.productLiveData?.observe(this, Observer {
            it?.apply {
                interactionListener.onSuccess()
                adapter.setProducts(this)
                isLoading = false
            } ?: takeIf { adapter.itemCount == 0 }?.apply { interactionListener.onFailure() }
        })
    }

    override fun onItemClick(product: Product) {
        interactionListener.onItemClick(product)
    }

    private fun initScrollListener(productListView: RecyclerView, llayoutManager: LinearLayoutManager) {
        productListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val total = llayoutManager.getItemCount()
                val lastVisibleItemCount = llayoutManager.findLastVisibleItemPosition()

                if (!isLoading) {
                    if (total > 0)
                        if (total - 2 == lastVisibleItemCount) {
                            isLoading = true
                            productListViewModel?.loadMore(1, total + 10)
                        }
                }
            }
        })
    }
}