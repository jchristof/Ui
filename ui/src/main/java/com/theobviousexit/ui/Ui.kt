package com.theobviousexit.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.theobviousexit.listener.BasicListenerRegistry
import com.theobviousexit.listener.ListenerRegistry

interface Ui {
    val view: View
}

inline fun <UI : Ui> ViewGroup.createUi(uiFactory: (LayoutInflater, ViewGroup?) -> UI): UI {
    val li = LayoutInflater.from(context)
    return uiFactory(li, this)
}

inline fun <reified T : View> Ui.find(id: Int): T = view.findViewById(id) ?: error("Can't find view with ID ${resources.idString(id)}")
inline val Ui.context: Context get() = view.context
inline val Ui.resources: Resources get() = view.context.resources

fun Resources.idString(id: Int): String {
    return try {
        this.getResourceEntryName(id)
    }
    catch (e: Resources.NotFoundException) {
        id.toString()
    }
}

open class UiFromBinding<B : ViewBinding>(protected val binding: B) : Ui {
    override val view = binding.root
}

open class NotifyingUiFromBinding<L, B : ViewBinding>(binding: B) : UiFromBinding<B>(binding), ListenerRegistry<L> by BasicListenerRegistry()

abstract class PresentingNotifyingUiFromBinding<D, L, B : ViewBinding>(binding: B) : NotifyingUiFromBinding<L, B>(binding), UIState {
    abstract fun show(data: D)
    override fun onRestoreInstanceState(state: Bundle?) = Unit
    override fun onSaveInstanceState(outState: Bundle?) = Unit
}

interface UiAdapterItem {
    val itemType: Int
}

interface UIState {
    fun onSaveInstanceState(outState: Bundle?)
    fun onRestoreInstanceState(state: Bundle?)
}

class UiViewHolder(val ui: Ui) : RecyclerView.ViewHolder(ui.view)