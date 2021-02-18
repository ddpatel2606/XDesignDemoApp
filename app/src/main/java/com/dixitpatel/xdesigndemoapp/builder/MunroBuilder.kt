package com.dixitpatel.xdesigndemoapp.builder

import android.util.Log
import com.dixitpatel.xdesigndemoapp.model.*
import java.util.*

/**
 * Main MunroBuilder class using Builder Design Pattern.
 * @param munRos
 * @param categoryFilter
 * @param limit
 * @param maxHeight
 * @param minHeight
 * @param nameSort
 * @param heightSort
 */
class MunroBuilder() {

    lateinit var munRos: ArrayList<MunroResultModel>
    private var categoryToFilter = MunroTypes.ALL
    private var limit = 0
    private var maxHeight = 0.0
    private var minHeight = 0.0
    private var nameSortParam = SortPriority.DEFAULT
    private var heightSortParam = SortPriority.DEFAULT

    constructor(munRosData: ArrayList<MunroResultModel>,
            categoryToFilter: MunroTypes,
            limit: Int,
            minHeight: Double,
            maxHeight: Double,
            nameSortParam: SortPriority,
            heightSortParam: SortPriority) : this()
    {
        munRos = munRosData
        this.categoryToFilter = categoryToFilter
        this.limit = limit
        this.minHeight = minHeight
        this.maxHeight = maxHeight
        this.nameSortParam = nameSortParam
        this.heightSortParam = heightSortParam
        applyFilters()
    }


    private fun applyFilters() {

        var sortedByHeight = false
        if (categoryToFilter != MunroTypes.ALL) {
            filterCategory(categoryToFilter)
        }
        if (maxHeight > -1) {
            if (maxHeight < minHeight) {
                Log.e("TAG","Max height filter is smaller than min height filter and that will not applied")
            } else {
                filterMaxHeight(maxHeight)
            }
        }
        if (minHeight > -1) {
            if (minHeight > maxHeight && maxHeight != -1.0) {
                Log.e("TAG","Min height filter is greater than max height filter and that will not applied")
            } else {
                filterMinHeight(minHeight)
            }
        }
        if (heightSortParam != SortPriority.DEFAULT) {
            sortByHeight(heightSortParam)
            sortedByHeight = true
        }
        if (nameSortParam != SortPriority.DEFAULT) {
            if (!sortedByHeight) {
                sortByName(nameSortParam)
            } else {
                sortSecondaryByName()
            }
        }
        if (limit > 0) {
            limitBy(limit)
        }
    }


    private fun filterCategory(filterOption: MunroTypes) {
        if (filterOption == MunroTypes.TOP) {
            munRos.removeIf { munroResultModel -> munroResultModel.hillCategory == MunroTypes.MUN.toString() }
        } else {
            munRos.removeIf { munroResultModel -> munroResultModel.hillCategory == MunroTypes.TOP.toString() }
        }
    }


    private fun filterMaxHeight(maxHeight: Double) {
        munRos.removeAll { munroResultModel -> munroResultModel.heightMetersDouble() > maxHeight }
    }


    private fun filterMinHeight(minHeight: Double) {
        munRos.removeAll { munroResultModel -> munroResultModel.heightMetersDouble() < minHeight }
    }

    private fun limitBy(limit: Int) {
        if (limit < munRos.size) {
            munRos = ArrayList<MunroResultModel>(munRos.subList(0, limit))
        }
    }

    private fun sortByHeight(sortParam: SortPriority) {
        munRos.sortBy { it.heightMetersDouble() }
        if (sortParam == SortPriority.DESC) {
            munRos.sortByDescending { it.heightMetersDouble() }
        }
    }

    private fun sortByName(sortParam: SortPriority) {
        munRos.sortBy { it.name }
        if (sortParam == SortPriority.DESC) {
            munRos.sortByDescending { it.name }
        }
    }


    private fun sortSecondaryByName() {
        var startOfDuplicatesIndex = -1
        var endOfDuplicatesIndex = -1
        for (i in 0 until munRos.size - 1)
        {
            val thisHeight: Double = munRos[i].heightMetersDouble()
            val nextHeight: Double = munRos[i + 1].heightMetersDouble()

            if (thisHeight == nextHeight) {
                if (startOfDuplicatesIndex == -1) {
                    startOfDuplicatesIndex = i
                }
                endOfDuplicatesIndex = i + 1
            } else {
                if (startOfDuplicatesIndex != -1) {
                    sortDuplicateHeightsByName(
                            startOfDuplicatesIndex,
                            endOfDuplicatesIndex
                    )
                    startOfDuplicatesIndex = -1
                    endOfDuplicatesIndex = -1
                }
            }
            if (i == munRos.size - 2 && startOfDuplicatesIndex != -1) {
                sortDuplicateHeightsByName(
                        startOfDuplicatesIndex,
                        endOfDuplicatesIndex
                )
            }
        }
    }


    private fun sortDuplicateHeightsByName(startOfDuplicatesIndex: Int, endOfDuplicatesIndex: Int) {
        munRos.subList(startOfDuplicatesIndex, endOfDuplicatesIndex + 1).sortBy { it.name }
        if (nameSortParam == SortPriority.DESC) {
            munRos.subList(startOfDuplicatesIndex, endOfDuplicatesIndex + 1).asReversed()
        }
    }

    // Sub Builder Class
    class Builder(private val munRos: ArrayList<MunroResultModel>) {

        private var categoryToFilter = MunroTypes.ALL
        private var limit: Int
        private var minHeight: Double
        private var maxHeight: Double
        private var nameSortParam: SortPriority
        private var heightSortParam: SortPriority

        init {
            limit = -1
            maxHeight = -1.0
            minHeight = -1.0
            nameSortParam = SortPriority.DEFAULT
            heightSortParam = SortPriority.DEFAULT
        }

        fun filterCategory(filterOption: MunroTypes): Builder {
            categoryToFilter = filterOption
            return this
        }

        fun limit(limit: Int): Builder {
            this.limit = limit
            return this
        }

        fun maxHeight(maxHeight: Double): Builder {
            this.maxHeight = maxHeight
            return this
        }

        fun minHeight(minHeight: Double): Builder {
            this.minHeight = minHeight
            return this
        }

        fun sortByName(sortParam: SortPriority): Builder {
            nameSortParam = sortParam
            return this
        }

        fun sortByHeight(sortParam: SortPriority): Builder {
            heightSortParam = sortParam
            return this
        }

        fun build(): ArrayList<MunroResultModel> {
            val munroSorter = MunroBuilder(
                    munRos,
                    categoryToFilter,
                    limit,
                    minHeight,
                    maxHeight,
                    nameSortParam,
                    heightSortParam
            )
            return munroSorter.munRos
        }
    }

}