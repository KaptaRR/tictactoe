package com.kaptarr.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kaptarr.tictactoe.databinding.SelectPlayersBinding

class SelectPlayers : AppCompatActivity() {

    private lateinit var binding: SelectPlayersBinding
    private lateinit var playerOneName: String
    private lateinit var playerTwoName: String

    companion object {
        const val EXTRA_PLAYER_ONE_NAME = "PLAYER_ONE_NAME"
        const val EXTRA_PLAYER_TWO_NAME = "PLAYER_TWO_NAME"
    }

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

        initListeners()

    }

    private fun initListeners() {
        binding.btnPlay.setOnClickListener { if (isPlayersNamesValid()) navigateToGame() else namesError() }
    }

    fun isPlayersNamesValid(): Boolean {
        playerOneName = binding.etPlayerOne.text.toString().trim()
        playerTwoName = binding.etPlayerTwo.text.toString().trim()

        return playerOneName.isNotEmpty() && playerTwoName.isNotEmpty() &&
                playerOneName.length <= 8 && playerTwoName.length <= 8
    }


    private fun navigateToGame() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(EXTRA_PLAYER_ONE_NAME, playerOneName)
        intent.putExtra(EXTRA_PLAYER_TWO_NAME, playerTwoName)
        startActivity(intent)
    }

    private fun namesError(){
        Toast.makeText(this, "Incorrect Name", Toast.LENGTH_SHORT).show()
    }
}