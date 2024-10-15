package com.example.calculator2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvSolution: TextView
    private var currentNumber: String = ""
    private var previousNumber: String = ""
    private var operation: String = ""
    private var isNewOperation = true
    private var history: String = ""  // Biến lưu trữ lịch sử các phép tính

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.result_tv)
        tvSolution = findViewById(R.id.solution_tv)
        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val btnPlus: Button = findViewById(R.id.btn_plus)
        val btnMinus: Button = findViewById(R.id.btn_sub)
        val btnMultiply: Button = findViewById(R.id.btn_mul)
        val btnDivide: Button = findViewById(R.id.btn_div)
        val btnEquals: Button = findViewById(R.id.btn_equal)
        val btnClear: Button = findViewById(R.id.btnC)
        val btnClearE: Button = findViewById(R.id.btnCE)
        val btnBS : Button = findViewById(R.id.btnBS)

        // Xử lý khi nhấn nút số

        val numberListener = { view: Button ->
            if (isNewOperation) {
                currentNumber = ""
                isNewOperation = false
            }
            currentNumber += view.text
            tvResult.text = currentNumber

            // Cập nhật lịch sử nhập liệu
            history += view.text
            tvSolution.text = history
        }
        btn0.setOnClickListener { numberListener(btn0) }
        btn1.setOnClickListener { numberListener(btn1) }
        btn2.setOnClickListener { numberListener(btn2) }
        btn3.setOnClickListener { numberListener(btn3) }
        btn4.setOnClickListener { numberListener(btn4) }
        btn5.setOnClickListener { numberListener(btn5) }
        btn6.setOnClickListener { numberListener(btn6) }
        btn7.setOnClickListener { numberListener(btn7) }
        btn8.setOnClickListener { numberListener(btn8) }
        btn9.setOnClickListener { numberListener(btn9) }

        fun opListener(op: String) {
            previousNumber = currentNumber
            operation = op
            isNewOperation = true

            // Xóa phép toán trước đó nếu đã có trong lịch sử
            if (history.isNotEmpty() && (history.last() in listOf('+', '-', 'x', '/'))) {
                history = history.dropLast(3)  // Xóa phép toán cũ khỏi chuỗi lịch sử
            }
            // Cập nhật lịch sử với phép toán mới
            history += " $op "
            tvSolution.text = history
            // Cập nhật lịch sử với phép toán

        }

        // Xử lý sự kiện khi nhấn các nút phép tính
        btnPlus.setOnClickListener { opListener("+") }
        btnMinus.setOnClickListener { opListener("-") }
        btnMultiply.setOnClickListener { opListener("x") }
        btnDivide.setOnClickListener { opListener("/") }


         fun equalsListener() {
            val secondNumber = currentNumber
            var result = 0.0

            if (previousNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                when (operation) {
                    "+" -> result = previousNumber.toDouble() + secondNumber.toDouble()
                    "-" -> result = previousNumber.toDouble() - secondNumber.toDouble()
                    "x" -> result = previousNumber.toDouble() * secondNumber.toDouble()
                    "/" -> result = previousNumber.toDouble() / secondNumber.toDouble()
                }
            }

             // Kiểm tra xem kết quả có phải số nguyên không
             if (result == result.toInt().toDouble()) {
                 // Nếu là số nguyên, hiển thị dưới dạng Int
                 tvResult.text = result.toInt().toString()
             } else {
                 // Nếu không, hiển thị dưới dạng Double
                 tvResult.text = result.toString()
             }

             // Cập nhật lịch sử với kết quả cuối cùng
             history += " = "
            tvSolution.text = history
            isNewOperation = true

        }

        // Xử lý sự kiện cho nút Backspace
        btnBS.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                // Xóa ký tự cuối cùng của currentNumber
                currentNumber = currentNumber.dropLast(1)

                // Cập nhật lại lịch sử
                history = " "

                // Hiển thị giá trị mới
                if (currentNumber.isEmpty()) {
                    tvResult.text = "0"  // Hiển thị 0 nếu không còn số nào
                } else {
                    tvResult.text = currentNumber
                }

                tvSolution.text = history
            }
        }

        // Nút bằng
        btnEquals.setOnClickListener { equalsListener() }

        // Nút xóa C
        btnClear.setOnClickListener {
            currentNumber = ""
            previousNumber = ""
            operation = ""
            isNewOperation = true
            tvResult.text = "0"
            history = ""  // Xóa lịch sử
            tvSolution.text = history
            }

        // Nút xóa CE
        btnClearE.setOnClickListener {
            currentNumber = ""
            previousNumber = ""
            operation = ""
            isNewOperation = true
            tvResult.text = "0"
            history = ""  // Xóa lịch sử
            tvSolution.text = history
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}