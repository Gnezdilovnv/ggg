package com.example.ggg

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentInput = ""
    private var operator: String? = null
    private var firstOperand = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                numberClick((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClick("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClick("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClick("/") }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { dotClick() }
    }

    private fun numberClick(value: String) {
        if (isNewOperation) {
            currentInput = ""
            isNewOperation = false
        }
        currentInput += value
        display.text = currentInput
    }

    private fun operatorClick(op: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
            operator = op
            isNewOperation = true
        }
    }

    private fun calculate() {
        if (operator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "*" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
                else -> 0.0
            }
            display.text = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                result.toString()
            }
            currentInput = display.text.toString()
            operator = null
            isNewOperation = true
        }
    }

    private fun clear() {
        currentInput = ""
        operator = null
        firstOperand = 0.0
        isNewOperation = true
        display.text = "0"
    }

    private fun dotClick() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) currentInput = "0"
            currentInput += "."
            display.text = currentInput
        }
    }
}