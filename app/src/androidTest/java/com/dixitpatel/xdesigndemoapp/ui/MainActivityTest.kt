package com.dixitpatel.xdesigndemoapp.ui

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dixitpatel.xdesigndemoapp.R
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.hamcrest.core.IsNot.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase()
{

    @Rule
    @JvmField
    val mainActivityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun IsViewVisible() {
        Assert.assertTrue(mainActivityRule.activity.findViewById<View>(R.id.btnParseIt).isVisible)

        (mainActivityRule.activity as MainActivity).viewModel.cSVDataLoad()

    }

    @Test
    fun onDataValidCheck() {
        Assert.assertFalse((mainActivityRule.activity as MainActivity).viewModel.inputList.size > 0)
    }

    @Test
    fun onParseClickCheck() {
        onView(withId(R.id.btnParseIt)).perform(click())

    }


}