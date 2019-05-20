package com.sumitthakur.walmartproductlist.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.api.modle.Product
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith

/**
 * @author sumit.T
 */
@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    lateinit var detailsFragment: DetailsFragment

    @Before
    fun setUp() {
        detailsFragment = DetailsFragment()
        activityTestRule.activity.initFragment(detailsFragment, DetailsFragment.TAG)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onSuccessResponse() {
        detailsFragment.product = getMockedProduct()
        onView(withId(R.id.productTitle)).check(matches((isDisplayed())))
        onView(withId(R.id.productPrice)).check(matches((isDisplayed())))
        onView(withId(R.id.inStock)).check(matches((isDisplayed())))
        onView(withId(R.id.productDesc)).check(matches((isDisplayed())))
        onView(allOf(withId(R.id.productTitle), withText("Ellerton TV Console")))
        onView(allOf(withId(R.id.productPrice), withText("\$300.00")))
        onView(allOf(withId(R.id.inStock), withText("Available")))
    }

    @Test
    fun onFailureResponse() {
        detailsFragment.product = null
        onView(withId(R.id.productTitle)).check(matches((isDisplayed())))
        onView(withId(R.id.productPrice)).check(matches((isDisplayed())))
        onView(withId(R.id.inStock)).check(matches((isDisplayed())))
        onView(withId(R.id.productDesc)).check(matches((isDisplayed())))
        onView(allOf(withId(R.id.productTitle), withText("")))
        onView(allOf(withId(R.id.productPrice), withText("")))
        onView(allOf(withId(R.id.inStock), withText("")))
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
            "Ellerton TV Console"
        )
    }
}