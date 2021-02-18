package com.dixitpatel.xdesignparselib

import java.io.BufferedReader
import java.io.Reader
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

/**
 *  Main Class of CSV Parsing and convert fields to Object.
 *  Also convert any fields in other DataType.
 */
class RxParse <Row : Any> @PublishedApi internal constructor(
    @PublishedApi internal val reader: BufferedReader,
    private val cls: KClass<Row>,
    delimiterForHeader: String = ",",
    private val delimiterForData: String = ",")
{
    /**
     * This annotation may be applied to any value parameter of a data class to provide an alternate name for the parameter to be matched against the headers of the CSV text
     */
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.VALUE_PARAMETER)
    annotation class Serializable(val value: String)

    private val headers = reader.readLine().split(delimiterForHeader)

    /**
     * Calls the [block] callback giving it a sequence of all the parsed [Row]s in this file and closes the reader once
     * the processing is complete.
     *
     * @return the value returned by [block].
     * @throws IllegalArgumentException if one or more of the following is true:
     * - [Row] is not a data class,
     * - The non-optional parameters of [Row] do not have names or annotated names corresponding to the names of the headers of the CSV text
     * - The types of the parameters of [Row] are not parsable by default, nor have had parsers registered for them
     */
    inline fun <T> useRows(block: (Sequence<Row>) -> T): T = reader.use {
        block(it.lineSequence().map(this@RxParse::parseRow))
    }

    @PublishedApi internal fun parseRow(row: String): Row {
        val split = row.split(delimiterForData.toRegex())
        require(cls.isData)

        val ctor = cls.primaryConstructor!!

        val parsedParams = ctor.parameters.associateWith {
            val name = it.findAnnotation<Serializable>()?.value ?: it.name

            val idx = headers.indexOf(name)
            require(idx != -1)
            if (idx !in split.indices) return@associateWith null

            require(it.type.javaType in ParserMap)
            ParserMap[it.type.javaType]!!.invoke(split[idx])
        }

        return ctor.callBy(parsedParams.filterValues { it != null })
    }

    companion object {

        @PublishedApi internal val ParserMap = mutableMapOf<Class<*>, (String) -> Any>(
            Int::class.java to String::toInt,
            Double::class.java to String::toDouble,
            String::class.java to { it },
            Boolean::class.java to String::toBoolean
        )

        /**
         * Globally registers a parser for [T], which may or may not be parsable by default
         * @param parser a function mapping a [String] to an arbitrary type [T]
         */
        inline fun <reified T : Any> registerParser(noinline parser: (String) -> T) {
            ParserMap[T::class.java] = parser
        }

        /**
         * Creates a new RxParse<T> instance from the specified [reader] whose lines may be parsed into instances of [T]
         *
         * @param reader a [Reader] containing `n` lines of text, each line containing `m` fields separated by a [delimiter]
         * @param delimiter the delimiter to use
         */
        inline fun <reified T : Any> from(reader: Reader, delimiterForHeader: String = ","
                                          ,delimiterForData: String = ","): RxParse<T> {
            val br = if (reader is BufferedReader) reader else BufferedReader(reader)
            return RxParse(br, T::class, delimiterForHeader, delimiterForData)
        }
    }
}