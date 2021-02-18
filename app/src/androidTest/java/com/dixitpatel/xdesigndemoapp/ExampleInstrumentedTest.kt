package com.dixitpatel.xdesigndemoapp

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.dixitpatel.xdesigndemoapp.ui.MainActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun IsAppInstalled() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.dixitpatel.xdesigndemoapp", appContext.packageName)
    }


}