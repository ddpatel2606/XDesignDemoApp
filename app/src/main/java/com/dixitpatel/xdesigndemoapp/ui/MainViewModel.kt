package com.dixitpatel.xdesigndemoapp.ui

import androidx.lifecycle.ViewModel
import com.dixitpatel.xdesigndemoapp.ASSETS_CSV_FILE
import com.dixitpatel.xdesigndemoapp.HEADER_DELIMETER
import com.dixitpatel.xdesigndemoapp.MyApp
import com.dixitpatel.xdesigndemoapp.REGULAR_EXP
import com.dixitpatel.xdesigndemoapp.model.AllDataModel
import com.dixitpatel.xdesigndemoapp.model.MunroResultModel
import com.dixitpatel.xdesignparselib.RxParse
import kotlinx.coroutines.*
import java.io.BufferedReader

/**
 * MainViewModel bind with MainActivity.
 */
class MainViewModel : ViewModel() {

    internal val inputList : ArrayList<MunroResultModel> = arrayListOf()

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        if(viewModelJob.isActive) {
            viewModelJob.cancel()
        }

    }

    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun cSVDataLoad() {
        uiScope.launch {
            parseList()
        }
    }

    // Move the execution off the main thread using withContext(Dispatchers.Default)
    private suspend fun parseList() = withContext(Dispatchers.Default) {

        // Parsing work
        val fileReader = BufferedReader(MyApp.instance.assets?.open(ASSETS_CSV_FILE)?.reader())
        val csv = RxParse.from<AllDataModel>(fileReader,HEADER_DELIMETER,REGULAR_EXP)
        // print all data.
        //csv.useRows { it.forEach(::println) }

        csv.useRows { it.forEach { with(inputList) { add(MunroResultModel(it.name,it.heightMeters,it.classificationPost1997,it.gridRef)) } }}

    }
}