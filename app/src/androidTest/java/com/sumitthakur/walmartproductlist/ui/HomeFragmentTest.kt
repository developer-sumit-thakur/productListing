package com.sumitthakur.walmartproductlist.ui

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import com.sumitthakur.walmartproductlist.viewmodel.ProductListViewModel
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.mockito.Mockito


/**
 * Home Fragment test class
 * @author Sumit.T
 */

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    private lateinit var mockedViewModel: ProductListViewModel
    private lateinit var homeFragment: HomeFragment
    private lateinit var productLiveData: MutableLiveData<List<Product>>

    @Before
    fun setUp() {
        homeFragment = HomeFragment()
        mockedViewModel = Mockito.mock(ProductListViewModel::class.java)
        homeFragment.productListViewModel = mockedViewModel
        activityTestRule.activity.initFragment(homeFragment, HomeFragment.TAG)
        productLiveData = MutableLiveData()
    }

    @After
    fun tearDown() {
    }


    @Test
    fun onFailureResponse() {
        mockedViewModel.productLiveData?.postValue(ArrayList())
        Espresso.onView(ViewMatchers.withId(R.id.productListView))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))

        ViewMatchers.assertThat("RecyclerView item count",
            homeFragment.adapter.itemCount, CoreMatchers.equalTo(0))

    }

    @Test
    fun onSuccessResponse() {
        var mockedValues: ArrayList<Product> = ArrayList()
        mockedValues.add(getMockedProduct())
        mockedViewModel.productLiveData?.postValue(mockedValues)
        Espresso.onView(ViewMatchers.withId(R.id.productListView))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))

        ViewMatchers.assertThat("RecyclerView item count",
            homeFragment.adapter.itemCount, CoreMatchers.equalTo(0))

    }

    private fun getMockedProduct(): Product {
        return Product(
            "Ellerton media console is well-suited for today&rsquo;s casual lifestyle.",
            "/images/image2.jpeg",
            "testid",
            "24",
            "$300.00",
            true,
            "White Glove Delivery Included",
            "2",
            "Ellerton TV Console\""
        )
    }
}