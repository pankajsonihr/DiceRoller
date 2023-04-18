package pankaj.diceroller

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
//Pankaj - A00244692
class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: LinearLayout
    private lateinit var dice1Layout: LinearLayout
    private lateinit var dice2Layout: LinearLayout
    private lateinit var dice1View: TextView
    private lateinit var dice2View: TextView
    private lateinit var roll1Button: Button
    private lateinit var roll2Button: Button
    private lateinit var spinner: Spinner
    private lateinit var create: Button
    private lateinit var sidesInput: EditText
    private lateinit var testvieww:TextView
    var history = "History: "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        mainLayout = findViewById(R.id.mainLayout)
        dice1Layout = findViewById(R.id.dice1Layout)
        dice2Layout = findViewById(R.id.dice2Layout)
        dice1View = findViewById(R.id.dice1View)
        dice2View = findViewById(R.id.dice2View)
        roll1Button = findViewById(R.id.roll1Button)
        roll2Button = findViewById(R.id.roll2Button)
        spinner = findViewById(R.id.spinner)
        create = findViewById(R.id.Create)
        sidesInput = findViewById(R.id.sidesInput)
        testvieww = findViewById(R.id.history)


        // Retrieve the list of integers from SharedPreferences and populate the Spinner
        val sharedPreferences = getSharedPreferences("intArray", MODE_PRIVATE)
        val str = sharedPreferences.getString("intKey", "")
        val strArray = str?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val intList: ArrayList<Int> = ArrayList()
        if (strArray != null) {
            for (s in strArray) {
                intList.add(s.trim { it <= ' ' }.toInt())
            }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, intList)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        // Handle button clicks
        roll1Button.setOnClickListener {
            rollDice(dice1View)
            dice1Layout.visibility = View.VISIBLE
            dice2Layout.visibility = View.GONE
        }
        roll2Button.setOnClickListener {
            rollDice(dice1View)
            rollDice(dice2View)
            dice1Layout.visibility = View.VISIBLE
            dice2Layout.visibility = View.VISIBLE
        }
        create.setOnClickListener {
            var value = sidesInput.text.toString()
            sidesInput.setText("")

            val sharedPreferences = getSharedPreferences("intArray", MODE_PRIVATE)
            val str = sharedPreferences.getString("intKey","")

            val strArray = str?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }
                ?.toTypedArray()

            val intList: ArrayList<Int> = ArrayList()

            if (strArray != null) {
                for (s in strArray) {
                    intList.add(s.trim { it <= ' ' }.toInt())
                }
            }
            intList.add(value.toInt())

            val editor = sharedPreferences.edit()
            editor.putString("intKey", intList.joinToString(","))
            editor.commit()

            // Create a new ArrayAdapter with the updated list of integers
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, intList)

            // Set the new ArrayAdapter as the adapter for the Spinner
            spinner.adapter = adapter
        }
    }

    private fun rollDice(textView: TextView) {
        val getSelectedValue: Int = spinner.selectedItem.toString().toInt() + 1
        val randomNumber = Random.nextInt(1, getSelectedValue)
        textView.text = randomNumber.toString()
        history = history+randomNumber.toString()+","
        testvieww.text=history
    }

}
