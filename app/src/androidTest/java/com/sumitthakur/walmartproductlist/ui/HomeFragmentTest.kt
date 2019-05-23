package com.sumitthakur.walmartproductlist.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.sumitthakur.walmartproductlist.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import java.util.concurrent.TimeUnit


/**
 * Home Fragment test class
 * @author Sumit.T
 */

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    private val VERIFY_TIMEOUT = TimeUnit.SECONDS.toMillis(2)

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    private lateinit var homeFragment: HomeFragment

    @Before
    fun setUp() {
        homeFragment = HomeFragment()
        activityTestRule.activity.initFragment(homeFragment, HomeFragment.TAG)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testTitleBar() {
        onView(Matchers.allOf(ViewMatchers.withText("List of Products"), ViewMatchers.isDisplayed()))
    }

    @Test
    fun onFailureResponse() {
        onView(withId(R.id.productListView)).check(matches((ViewMatchers.isDisplayed())))
        assertThat(
            "RecyclerView item count", homeFragment.adapter.itemCount, CoreMatchers.equalTo(0)
        )
    }

    @Test
    fun onSuccessResponse() {
        Thread.sleep(VERIFY_TIMEOUT)
        onView(withId(R.id.productListView)).check(matches((ViewMatchers.isDisplayed())))
        assertThat(
            "RecyclerView item count",
            homeFragment.adapter.itemCount, CoreMatchers.equalTo(10)
        )
    }
}