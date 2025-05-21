package com.kaptarr.tictactoe

import android.app.Dialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kaptarr.tictactoe.databinding.ActivityGameBinding

import com.kaptarr.tictactoe.SelectPlayers.Companion.EXTRA_PLAYER_ONE_NAME
import com.kaptarr.tictactoe.SelectPlayers.Companion.EXTRA_PLAYER_TWO_NAME
import com.kaptarr.tictactoe.databinding.DialogWinnerBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var dialogBinding: DialogWinnerBinding
    private lateinit var dialog: Dialog


    private var isPlayerOneTurn = true
    val board: Array<Array<String>> = Array(3) { Array(3) { "" } }

    companion object {
        const val X: String = "X"
        const val O: String = "O"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        initUI()
        initListeners()

    }

    private fun initListeners() {
        val buttons = listOf(
            binding.ibOne, binding.ibTwo, binding.ibThree,
            binding.ibFour, binding.ibFive, binding.ibSix,
            binding.ibSeven, binding.ibEight, binding.ibNine
        )

        buttons.forEachIndexed { index, button ->
            val row = index / 3
            val col = index % 3

            button.setOnClickListener {
                if (isCellEmpty(row, col)) {
                    board[row][col] = circleOrX()
                    button.setImageResource(
                        if (isPlayerOneTurn) R.drawable.ic_circle else R.drawable.ic_x
                    )
                    val winner = checkWinner()
                    if (winner != null) {
                        showDialogWinner(winner)
                    } else if (isboardFull()) {
                        drawResult()
                    } else {
                        isPlayerOneTurn = !isPlayerOneTurn
                        printPlayerTurn(isPlayerOneTurn)
                    }
                }
            }

        }
    }

    private fun initUI() {
        setPlayersName()
        printPlayerTurn(isPlayerOneTurn)
        createDialog()
    }

    private fun setPlayersName() {
        binding.tvPlayer1.text = intent.getStringExtra(EXTRA_PLAYER_ONE_NAME)
        binding.tvPlayer2.text = intent.getStringExtra(EXTRA_PLAYER_TWO_NAME)
    }

    private fun circleOrX(): String = if (isPlayerOneTurn) O else X

    private fun printPlayerTurn(playerTurn: Boolean) {
        if (isPlayerOneTurn) {
            binding.ibFirstPlayer.setBackgroundResource(R.drawable.card_border)
            binding.ibSecondPlayer.setBackgroundResource(R.color.white)
        } else {
            binding.ibSecondPlayer.setBackgroundResource(R.drawable.card_border)
            binding.ibFirstPlayer.setBackgroundResource(R.color.white)
        }

    }

    private fun isCellEmpty(row: Int, col: Int): Boolean {
        return board[row][col].isEmpty()
    }
    private fun isboardFull(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun checkWinner(): String? {
        // Revisar filas
        for (i in 0..2) {
            if (board[i][0].isNotEmpty() &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]
            ) {
                return board[i][0]
            }
        }

        // Revisar columnas
        for (j in 0..2) {
            if (board[0][j].isNotEmpty() &&
                board[0][j] == board[1][j] &&
                board[1][j] == board[2][j]
            ) {
                return board[0][j]
            }
        }

        // Revisar diagonal principal
        if (board[0][0].isNotEmpty() &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]
        ) {
            return board[0][0]
        }

        // Revisar diagonal secundaria
        if (board[0][2].isNotEmpty() &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]
        ) {
            return board[0][2]
        }

        return null
    }

    private fun showDialogWinner(winner: String) {
        val playerOneName = binding.tvPlayer1.text.toString()
        val playerTwoName = binding.tvPlayer2.text.toString()

        if (winner == O) {
            dialogBinding.ivWinner.setImageResource(R.drawable.ic_circle)
            dialogBinding.tvWinner.text = "$playerOneName is Winner".uppercase()
        } else {
            dialogBinding.ivWinner.setImageResource(R.drawable.ic_x)
            dialogBinding.tvWinner.text = "$playerTwoName is Winner".uppercase()
        }

        dialogBinding.btnPlayAgain.setOnClickListener { clearGame() }

        dialog.show()

    }

    private fun drawResult() {
        dialogBinding.ivWinner.setImageResource(R.drawable.unhappy_12561686)
        dialogBinding.tvWinner.text = "Draw".uppercase()
        dialogBinding.btnPlayAgain.setOnClickListener { clearGame() }
        dialog.show()
    }

    private fun clearGame() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = ""
            }
        }

        val buttons = listOf(
            binding.ibOne, binding.ibTwo, binding.ibThree,
            binding.ibFour, binding.ibFive, binding.ibSix,
            binding.ibSeven, binding.ibEight, binding.ibNine
        )

        buttons.forEach { button ->
            button.setImageDrawable(null)
        }

        isPlayerOneTurn = true
        printPlayerTurn(isPlayerOneTurn)

        dialog.dismiss()
    }

    private fun createDialog() {
        dialogBinding = DialogWinnerBinding.inflate(layoutInflater)
        dialog = Dialog(this)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

    }
}

