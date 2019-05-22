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
        fun onItemClick(product: Product)
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

        showProgress(true)
        llayoutManager = LinearLayoutManager(activity)
        productListView.setHasFixedSize(true)
        productListView.layoutManager = llayoutManager
        adapter = ProductsAdapter(activity, ArrayList())
        productListView.adapter = adapter
        adapter.setOnItemClickListener(this)
        initScrollListener(productListView, llayoutManager as LinearLayoutManager)

        productListViewModel?.productLiveData?.observe(this, Observer {
            it?.apply {
                onSuccess()
                adapter.setProducts(this)
                isLoading = false
            } ?: takeIf { adapter.itemCount == 0 }?.apply { onFailure() }
        })

        retryButton.setOnClickListener {
            productListViewModel?.productLiveData
        }
    }

    override fun onItemClick(product: Product) {
        interactionListener.onItemClick(product)
    }

    private fun initScrollListener(productListView: RecyclerView, llayoutManager: LinearLayoutManager) {
        productListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val total = llayoutManager.itemCount
                val lastVisibleItemCount = llayoutManager.findLastVisibleItemPosition()

                if (total > 0)
                    if (total - 3 == lastVisibleItemCount && !isLoading) {
                        isLoading = true
                        showProgress(true)
                        productListViewModel?.loadMore(total / 10 + 1)
                    }
            }
        })
    }

    private fun showProgress(show: Boolean) {
        progressView?.apply {
            visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun onSuccess() {
        itemsView?.apply {
            visibility = View.VISIBLE
        }

        showProgress(false)

        errorView?.apply {
            visibility = View.GONE
        }
    }

    private fun onFailure() {
        itemsView?.apply {
            visibility = View.GONE
        }

        showProgress(false)

        errorView?.apply {
            visibility = View.VISIBLE
        }
    }
}