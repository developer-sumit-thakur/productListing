package com.sumitthakur.walmartproductlist.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sumitthakur.walmartproductlist.R
import com.sumitthakur.walmartproductlist.viewmodel.ProductListViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)
    private lateinit var homeActivity: HomeActivity
    private lateinit var mockedViewModel: ProductListViewModel

    @Before
    fun setUp() {
        homeActivity = HomeActivity()
        mockedViewModel = Mockito.mock(ProductListViewModel::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testOnSuccess() {
        homeActivity.onSuccess()
        onView(withId(R.id.contentView)).check(matches((isDisplayed())))
    }

    @Test
    fun testOnItemClick() {
    }

    @Test
    fun testOnFailure() {
        homeActivity.onFailure()
        onView(withId(R.id.errorView)).check(matches((isDisplayed())));
    }

    fun mockedListener(): HomeFragment.InteractionListener {
        return activityTestRule.activity
    }
}