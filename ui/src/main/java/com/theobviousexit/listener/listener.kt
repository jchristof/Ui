package com.theobviousexit.listener

interface ListenerRegistry<T> {
    val allListeners: Iterable<T>

    fun addListener(listener: T)
    fun removeListener(listener: T)
    fun removeAllListeners()
}

inline fun <T> ListenerRegistry<T>.notifyListeners(block: T.() -> Unit) {
    allListeners.forEach { it.block() }
}

class BasicListenerRegistry<T> : ListenerRegistry<T> {
    private val listeners = mutableSetOf<T>()

    override val allListeners: Iterable<T>
        get() = listeners

    override fun addListener(listener: T) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    override fun removeListener(listener: T) {
        listeners.remove(listener)
    }

    override fun removeAllListeners() {
        listeners.clear()
    }
}