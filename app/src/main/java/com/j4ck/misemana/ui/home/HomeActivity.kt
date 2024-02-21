package com.j4ck.misemana.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.j4ck.misemana.databinding.ActivityHomeBinding

/*ViewBinding Docu https://developer.android.com/topic/libraries/view-binding?hl=es-419#activities */
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHeader()
    }

    private fun setHeader() {
        binding.cicloTextView.setText("Ciclo VII")
        binding.creditosTexView.setText("20 Créditos")
    }
}