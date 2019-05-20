package com.sumitthakur.walmartproductlist.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import kotlinx.android.synthetic.main.home_activity.*

/**
 * Home Activity - starting point of the application
 * @author Sumit.T
 */
open class HomeActivity : AppCompatActivity(), HomeFragment.InteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle(getString(R.string.list_of_product))
        setContentView(R.layout.home_activity)

        showProgress(true)

        if (savedInstanceState == null) {
            initFragment(HomeFragment(), HomeFragment.TAG)
        }
    }

    fun showProgress(show: Boolean) {
        progressView?.apply {
            visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.apply {
            setTitle(title)
        }
    }

    fun initFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
    }

    override fun onSuccess() {
        contentView?.apply {
            visibility = View.VISIBLE
        }

        showProgress(false)

        errorView?.apply {
            visibility = View.GONE
        }
    }

    override fun onItemClick(product: Product) {
        var bundle = Bundle()
        setToolbarTitle(getString(R.string.product_details))
        bundle.putParcelable(DetailsFragment.EXTRA_DATA, product)
        initFragment(DetailsFragment.newInstance(bundle), DetailsFragment.TAG)
    }

    override fun onFailure() {
        contentView?.apply {
            visibility = View.GONE
        }

        showProgress(false)

        errorView?.apply {
            visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        takeIf { supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) != null }
            ?.apply {
                setToolbarTitle(getString(R.string.list_of_product))
                initFragment(HomeFragment(), HomeFragment.TAG)
            } ?: super.onBackPressed()
    }
}