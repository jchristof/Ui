package com.theobviousexit.myui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.theobviousexit.myui.databinding.ActivityMainBinding
import com.theobviousexit.ui.PresentingNotifyingUiFromBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = MainUi(layoutInflater, null)
        ui.show(MainDisplay())
        setContentView(ui.view)
    }
}

interface MainUiListener {}
data class MainDisplay(val any: Any? = null)
typealias MainUiWidget = PresentingNotifyingUiFromBinding<MainDisplay, MainUiListener, ActivityMainBinding>

class MainUi(inflater: LayoutInflater, parent: ViewGroup?) :
    MainUiWidget(ActivityMainBinding.inflate(inflater, parent, false)) {
    override fun show(data: MainDisplay) {
        binding.greeting.text = "Hello from Ui!"
    }
}