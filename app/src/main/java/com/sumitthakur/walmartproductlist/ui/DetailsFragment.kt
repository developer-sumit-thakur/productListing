package com.sumitthakur.walmartproductlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.databinding.DetailsFragmentBinding
import com.sumitthakur.walmartproductlist.testing.OpenForTesting
import com.sumitthakur.walmartproductlist.util.ImageUtil

/**
 * Class to show details of the product
 * @author Sumit.T
 */

@OpenForTesting
open class DetailsFragment : Fragment() {
    var product: Product? = null

    companion object {
        val TAG = DetailsFragment::class.java.simpleName
        val EXTRA_DATA = "extra_data"

        fun newInstance(bundle: Bundle): DetailsFragment {
            var detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        product = arguments?.getParcelable(EXTRA_DATA)

        val itemBinding: DetailsFragmentBinding = inflate(layoutInflater, R.layout.details_fragment, container, false)
        itemBinding.root.context?.apply {
            product?.apply {
                itemBinding.product = product
                longDescription?.apply {
                    itemBinding.longDescription = HtmlCompat.fromHtml(
                        this,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    ).toString()
                }

                itemBinding.availability = if (inStock) {
                    "Available"
                } else "Un-availabile"


                productImage?.apply {
                    takeIf { this.isNotEmpty() }?.apply {
                        takeIf { activity != null }?.apply {
                            ImageUtil().loadImage(activity, itemBinding.productImage, this)
                        }
                    }
                }
            }
        }

        return itemBinding.root
    }
}