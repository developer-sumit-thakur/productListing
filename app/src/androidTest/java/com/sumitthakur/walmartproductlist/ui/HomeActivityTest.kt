package com.sumitthakur.walmartproductlist.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sumitthakur.walmartproductlist.R
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testHomeFragmentLoad() {
        activityTestRule.activity.initFragment(HomeFragment(), HomeFragment.TAG)
        onView(Matchers.allOf(withId(R.id.productTitle), ViewMatchers.withText("")))
        onView(Matchers.allOf(withId(R.id.productPrice), ViewMatchers.withText("")))
    }

    @Test
    fun testDetailsFragmentLoad() {
        activityTestRule.activity.initFragment(DetailsFragment(), DetailsFragment.TAG)
        onView(withId(R.id.productTitle)).check(matches((isDisplayed())))
        onView(withId(R.id.productPrice)).check(matches((isDisplayed())))
        onView(withId(R.id.inStock)).check(matches((isDisplayed())))
        onView(withId(R.id.productDesc)).check(matches((isDisplayed())))
    }
}