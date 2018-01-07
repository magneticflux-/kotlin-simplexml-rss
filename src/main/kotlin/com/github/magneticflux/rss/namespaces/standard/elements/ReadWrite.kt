package com.github.magneticflux.rss.namespaces.standard.elements

/**
 * Represents an object that can be converted to a read-only form and a writable form.
 *
 * @param T the type of the read-only form
 * @param U the type of the writable form
 */
interface HasReadWrite<out T, out U> : HasToReadOnly<T>, HasToWritable<U>

/**
 * Represents an object that can be converted to a read-only form.
 *
 * @param T the type of the read-only form
 */
interface HasToReadOnly<out T> {
    fun toReadOnly(): T
}

/**
 * Represents an object that can be converted to a writable form.
 *
 * @param T the type of the writable form
 */
interface HasToWritable<out T> {
    fun toWritable(): T
}