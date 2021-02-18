package com.dixitpatel.xdesigndemoapp.model

import com.dixitpatel.xdesignparselib.RxParse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * All munro Data Model Class bind with key same as csv file.
 */
data class AllDataModel(
        @RxParse.Serializable("Running No") val runningNo: String,
        @RxParse.Serializable("DoBIH Number") val doBIHNumber: String,
        @RxParse.Serializable("Streetmap")  val streetMapURL: String,
        @RxParse.Serializable("Geograph")  val geoGraphURL: String,
        @RxParse.Serializable("Hill-bagging")  val hillBagging: String,
        @RxParse.Serializable("Name") val name: String,
        @RxParse.Serializable("SMC Section")  val smcSection: String,
        @RxParse.Serializable("RHB Section")  val rhbSection: String,
        @RxParse.Serializable("_Section")  val section: String,
        @RxParse.Serializable("Height (m)")  val heightMeters: String,
        @RxParse.Serializable("Height (ft)")  val heightFeet: String,
        @RxParse.Serializable("Map 1:50")  val mapOneFifty: String,
        @RxParse.Serializable("Map 1:25")  val mapOneTwentyFive: String,
        @RxParse.Serializable("Grid Ref")  val gridRef: String,
        @RxParse.Serializable("GridRefXY")  val gridRefXY: String,
        @RxParse.Serializable("xcoord")  val xCoord: String,
        @RxParse.Serializable("ycoord")  val yCoord: String,
        @RxParse.Serializable("1891")  val classification1891: String,
        @RxParse.Serializable("1921")  val classification1921: String,
        @RxParse.Serializable("1933")  val classification1933: String,
        @RxParse.Serializable("1953")  val classification1953: String,
        @RxParse.Serializable("1969")  val classification1969: String,
        @RxParse.Serializable("1974")  val classification1974: String,
        @RxParse.Serializable("1981")  val classification1981: String,
        @RxParse.Serializable("1984")  val classification1984: String,
        @RxParse.Serializable("1990")  val classification1990: String,
        @RxParse.Serializable("1997")  val classification1997: String,
        @RxParse.Serializable("Post 1997")  val classificationPost1997: String,
        @RxParse.Serializable("Comments")  val comments: String)