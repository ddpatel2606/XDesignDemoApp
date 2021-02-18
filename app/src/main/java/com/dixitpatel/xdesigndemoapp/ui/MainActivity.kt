package com.dixitpatel.xdesigndemoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dixitpatel.xdesigndemoapp.R
import com.dixitpatel.xdesigndemoapp.databinding.ActivityMainBinding
import com.dixitpatel.xdesigndemoapp.builder.MunroBuilder
import com.dixitpatel.xdesigndemoapp.model.MunroResultModel
import com.dixitpatel.xdesigndemoapp.model.MunroTypes
import com.dixitpatel.xdesigndemoapp.model.SortPriority
import com.google.android.material.textview.MaterialTextView
import java.lang.StringBuilder

/**
 * Main Activity : Munro CSV Sorting with MVVM Architecture.
 */
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Parse CSV File
        viewModel.cSVDataLoad()

        binding.btnParseIt.setOnClickListener {

            val munRosResult : List<MunroResultModel> = MunroBuilder
                .Builder(viewModel.inputList)
                .filterCategory(MunroTypes.MUN)
                .sortByName(SortPriority.DESC)
                .build()

            binding.tvCount.text = "${munRosResult.size}"

            val stringBuilder = StringBuilder()
            munRosResult.forEach{ stringBuilder.append("${it.toString()} \t\n") }

            binding.tvAllData.text = stringBuilder.toString()
        }
    }
}