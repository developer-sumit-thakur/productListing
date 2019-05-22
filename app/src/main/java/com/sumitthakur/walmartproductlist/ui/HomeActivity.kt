package com.sumitthakur.walmartproductlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product

/**
 * Home Activity - starting point of the application
 * @author Sumit.T
 */
open class HomeActivity : AppCompatActivity(), HomeFragment.InteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle(getString(R.string.list_of_product))
        setContentView(R.layout.home_activity)

        if (savedInstanceState == null) {
            initFragment(HomeFragment(), HomeFragment.TAG)
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

    override fun onItemClick(product: Product) {
        var bundle = Bundle()
        setToolbarTitle(getString(R.string.product_details))
        bundle.putParcelable(DetailsFragment.EXTRA_DATA, product)
        initFragment(DetailsFragment.newInstance(bundle), DetailsFragment.TAG)
    }

    override fun onBackPressed() {
        takeIf { supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) != null }
            ?.apply {
                setToolbarTitle(getString(R.string.list_of_product))
                initFragment(HomeFragment(), HomeFragment.TAG)
            } ?: super.onBackPressed()
    }
}