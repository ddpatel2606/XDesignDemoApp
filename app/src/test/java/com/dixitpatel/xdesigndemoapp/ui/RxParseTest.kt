package com.dixitpatel.xdesigndemoapp.ui

import com.dixitpatel.xdesigndemoapp.ASSETS_CSV_FILE
import com.dixitpatel.xdesigndemoapp.HEADER_DELIMETER
import com.dixitpatel.xdesigndemoapp.MyApp
import com.dixitpatel.xdesigndemoapp.REGULAR_EXP
import com.dixitpatel.xdesigndemoapp.builder.MunroBuilder
import com.dixitpatel.xdesigndemoapp.model.AllDataModel
import com.dixitpatel.xdesigndemoapp.model.MunroResultModel
import com.dixitpatel.xdesigndemoapp.model.MunroTypes
import com.dixitpatel.xdesignparselib.RxParse
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RxParseTest
{
    private val inputList : ArrayList<MunroResultModel> = arrayListOf()

    @Before
    fun initCSVParse()
    {
        var inputStream: InputStream? = null
        try {
            inputStream =
                    javaClass.classLoader?.getResourceAsStream(ASSETS_CSV_FILE)

            val reader = BufferedReader(InputStreamReader(inputStream))

            val csv = RxParse.from<AllDataModel>(reader, HEADER_DELIMETER, REGULAR_EXP)

            csv.useRows { it.forEach { with(inputList) { add(MunroResultModel(it.name,it.heightMeters,it.classificationPost1997,it.gridRef)) } }}

        } finally {
            inputStream?.close()
        }
    }

    /**
     * Check the first Munro Data is matching with expected Mock Munro Data Result.
     */
    @Test
    fun validParseData() {

        try {

            val expectedResultModel = MunroResultModel("Ben Chonzie","931","MUN","NN773308")

            Assert.assertEquals(expectedResultModel, inputList[0])

        } catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    /**
     * Check the first Munro Data is matching with expected Mock Munro Data Result.
     */
    @Test
    fun validFilterCategorySorting() {

        try {
            val munRosResult : List<MunroResultModel> = MunroBuilder
                    .Builder(inputList)
                    .filterCategory(MunroTypes.MUN)
                    .build()

            if(munRosResult.isNotEmpty())
            {
                Assert.assertEquals(MunroTypes.MUN.toString(), munRosResult[0].hillCategory)
            }
            else
            {
                Assert.assertFalse("Filter Category Sorting Failed",false)
            }
        } catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    /**
     * Check the first Munro Data is matching with expected Mock Munro Data Result.
     */
    @Test
    fun validLimitSorting() {

        try {
            val munRosResult : List<MunroResultModel> = MunroBuilder
                    .Builder(inputList)
                    .filterCategory(MunroTypes.ALL)
                    .limit(15)
                    .build()

            if(munRosResult.isNotEmpty())
            {
                Assert.assertEquals(15, munRosResult.size)
            }
            else
            {
                Assert.assertFalse("Limit Sorting Failed",false)
            }
        } catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

}