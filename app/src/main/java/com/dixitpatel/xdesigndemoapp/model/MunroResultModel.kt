package com.dixitpatel.xdesigndemoapp.model

/**
 * Munro Result Model
 */
data class MunroResultModel(val name: String ,
                             val heightMeters: String ,
                             val hillCategory: String ,
                             val gridReference: String )
{
    fun heightMetersDouble() : Double
    {
        return if (heightMeters.isNotEmpty()) heightMeters.toDouble() else 0.0
    }

    override fun toString(): String {
        return "Name= $name, Height= ${heightMetersDouble()}, HillCategory= $hillCategory, GridRef= $gridReference"
    }

}