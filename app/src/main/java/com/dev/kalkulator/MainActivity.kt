package com.dev.kalkulator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.kalkulator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var firstNumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        binding.apply {
            // Number Buttons
            val numberButtons = listOf(
                one, two, three, four, five, six, seven, eight, nine, zero
            )
            numberButtons.forEach { button ->
                button.setOnClickListener {
                    val buttonText = button.text.toString()
                    if (currentOperator.isEmpty()) {
                        firstNumber += buttonText
                        tvResult.text = firstNumber
                    } else {
                        currentNumber += buttonText
                        tvResult.text = currentNumber
                    }
                }
            }

            // Operator Buttons
            plus.setOnClickListener { setOperator("+") }
            mines.setOnClickListener { setOperator("-") }
            multiply.setOnClickListener { setOperator("*") }
            devide.setOnClickListener { setOperator("/") }

            // Dot Button
            titik.setOnClickListener {
                addDot()
            }

            // Clear Button
            clear.setOnClickListener {
                resetCalculator()
            }

            // Equals Button
            equal.setOnClickListener {
                calculateResult()
            }
        }
    }

    private fun setOperator(operator: String) {
        if (currentNumber.isEmpty()) {
            currentOperator = operator
            binding.tvResult.text = "0"
        }
    }

    private fun addDot() {
        if (currentOperator.isEmpty()) {
            if (!firstNumber.contains(".")) {
                if (firstNumber.isEmpty()) firstNumber = "0."
                else firstNumber += "."
                binding.tvResult.text = firstNumber
            }
        } else {
            if (!currentNumber.contains(".")) {
                if (currentNumber.isEmpty()) currentNumber = "0."
                else currentNumber += "."
                binding.tvResult.text = currentNumber
            }
        }
    }

    private fun calculateResult() {
        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            binding.tvFormula.text = "$firstNumber $currentOperator $currentNumber"
            result = evaluateExpression(firstNumber, currentNumber, currentOperator)
            firstNumber = result
            currentNumber = ""
            currentOperator = ""
            binding.tvResult.text = result
        }
    }

    private fun resetCalculator() {
        firstNumber = ""
        currentNumber = ""
        currentOperator = ""
        binding.tvResult.text = "0"
        binding.tvFormula.text = ""
    }

    private fun evaluateExpression(first: String, second: String, operator: String): String {
        val num1 = first.toDouble()
        val num2 = second.toDouble()
        return when (operator) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
            else -> ""
        }
    }
}
