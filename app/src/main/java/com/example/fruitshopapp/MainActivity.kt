package com.example.fruitshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.data.EmptyGroup.data
import androidx.compose.ui.unit.dp
import com.example.fruitshopapp.ui.theme.FruitShopAppTheme
import kotlin.math.roundToInt

data class Fruit(
    val name: List<String>,
    val price: List<Double>
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FruitShopAppTheme {
                // A surface container using the 'background' color from the theme
                ShopForFruits()
            }
        }
    }
}

@Composable
fun ShopForFruits(modifier: Modifier = Modifier){

    var quantityInput by remember {
        mutableStateOf("")
    }
   val quantity = quantityInput.toDoubleOrNull()?: 0.0

   var totalCalculated by remember {
       mutableStateOf("")
   }

    var priceOfFruits by remember {
        mutableDoubleStateOf(0.0)
    }

    val list = listOf(
        Fruit(listOf("Watermelon"), listOf(2.3)),
        Fruit(listOf("Kiwi"), listOf(2.3)),
        Fruit(listOf("Mango"), listOf(2.3)),
        Fruit(listOf("Apple"), listOf(2.3)),
        Fruit(listOf("Grapes"), listOf(2.3)),
        Fruit(listOf("Coconut"), listOf(2.3)),
        Fruit(listOf("Pineapple"), listOf(2.3)),
    )
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var fruitSelected by remember {
        mutableStateOf<Fruit?>(null)
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally, 
        verticalArrangement = Arrangement.Center) 
    {
        Column(
            modifier
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Box(modifier.clickable
            { isExpanded = !isExpanded })
            {
                Text(text = fruitSelected?.let { "${it.name.first()} - ${it.price.first()}" } ?: "Select an item")
            }
            DropdownMenu(expanded = isExpanded, onDismissRequest =
            { isExpanded=false })
            {
                list.forEach {
                        item ->
                    DropdownMenuItem(text = {
                        Text(text = "${item.name.joinToString(",")} " +
                                "- " +
                                item.price.joinToString(",")
                        )
                    }, onClick = {
                        fruitSelected = item
                        priceOfFruits = item.price.first()
                        isExpanded = false
                    }
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(30.dp))

TextField(value = quantityInput, onValueChange = {
    quantityInput = it
    quantityInput.toDoubleOrNull()?.times(priceOfFruits)
},
    singleLine = true,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    ),
    modifier = modifier
        .fillMaxWidth()
        .padding(30.dp))


           Spacer(modifier =
        modifier.
        height(20.dp)
        )

        Button(onClick =
        {
            val result = calculateTotal(fruitSelected?.price?.firstOrNull(), quantityInput.toDoubleOrNull())
            totalCalculated = result?.toString() ?: // Handle invalid input
                    "Invalid input"
        })
        {
            Text(text = "Calculate")
        }
        Spacer(modifier = modifier.height(10.dp))

        if (totalCalculated.isNotEmpty()) {
            Text(
                text = "Total: $totalCalculated",
            )
        }else{
            Text(
                text = "Zero total found for total price calculated",
            )
        }
    }

}
fun calculateTotal(price: Double?, quantity: Double?): Double? {
    if (price != null && quantity != null && quantity >= 0) {
        return price * quantity.roundToInt() // Round to integer for quantity
    }
    return null
}