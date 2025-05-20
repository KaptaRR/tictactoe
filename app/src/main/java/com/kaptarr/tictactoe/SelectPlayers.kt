package com.kaptarr.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kaptarr.tictactoe.databinding.SelectPlayersBinding

class SelectPlayers : AppCompatActivity() {

    private lateinit var binding: SelectPlayersBinding

    private lateinit var playerOneName: String
    private lateinit var playerTwoName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SelectPlayersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun enterPlayersNames(){
        playerOneName = binding.etPlayerOne.text.toString()
        playerTwoName = binding.etPlayerTwo.text.toString()

    }

    private fun navigateToGame(){
        val intent = Intent(this, GameActivity::class.java)
        //intent.putExtras()
    }
}