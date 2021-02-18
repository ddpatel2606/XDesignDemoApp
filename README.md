# XDesignDemoApp

Munro Data Sheet 
 
 ## Activity
 - MainActivity - Parsing and Sorting Munro Data
 
 ## Tech stack & Open-source libraries
 - Minimum SDK level 24
 - [Kotlin](https://kotlinlang.org/) and [Java](https://www.java.com/en/) based,
 - [kotlin-reflect](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/)
 - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
 - [CSVParser](https://github.com/richardrobinson0924/CSVParsers)
 
 - JetPack
   - LiveData - notify domain layer data to views.
   - ViewModel - UI related data holder, lifecycle aware.
   
 - Architecture
   - MVVM Architecture (Model View ViewModel - DataBinding)

## Functions

- Sort By Munro Category - MUN, TOP

<pre>
val munRosResult : List<MunroResultModel> = MunroBuilder
                   .Builder(viewModel.inputList)
                   .filterCategory(MunroTypes.MUN)
                   .build()
</pre>                   
         

- Sort By Munro Limit data
<pre>
val munRosResult : List<MunroResultModel> = MunroBuilder
                   .Builder(viewModel.inputList)
                   .filterCategory(MunroTypes.MUN)
                   .limit(10)
                   .build()
</pre>

- Sort By Munro minHeight, maxHeight
<pre>
val munRosResult : List<MunroResultModel> = MunroBuilder
                   .Builder(viewModel.inputList)
                   .filterCategory(MunroTypes.MUN)
                   .limit(10)
                   .minHeight(1000.0)
                   .maxHeight(700.0)
                   .build()
</pre>

- Sort By Name ASC, DESC
<pre>
val munRosResult : List<MunroResultModel> = MunroBuilder
                   .Builder(viewModel.inputList)
                   .filterCategory(MunroTypes.MUN)
                   .limit(10)
                   .minHeight(1000.0)
                   .maxHeight(700.0)
                   .sortByName(SortPriority.ASC)
                   .build()
</pre>

- Sort By Height ASC, DESC
<pre>
 val munRosResult : List<MunroResultModel> = MunroBuilder
                   .Builder(viewModel.inputList)
                   .filterCategory(MunroTypes.MUN)
                   .limit(10)
                   .minHeight(1000.0)
                   .maxHeight(700.0)
                   .sortByName(SortPriority.ASC)
                   .sortByHeight(SortPriority.DESC)
                   .build()
</pre>

- Added Some Proper UI and Unit Test Cases.