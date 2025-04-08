package gestaoweb.bbf.com.view.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import gestaoweb.bbf.com.util.Theme.darkBlueColor
import gestaoweb.bbf.com.util.Theme.fontDefault
import gestaoweb.bbf.com.util.Theme.heightField
import gestaoweb.bbf.com.viewmodel.clienteDto

@Composable
fun pedidoScreen() {
    val mockDataList = listOf(
        Pair("2025-04-10", "R$ 150,00"),
        Pair("2025-04-15", "R$ 200,00"),
        Pair("2025-04-20", "R$ 120,00"),
        Pair("2025-04-25", "R$ 180,00")
    )

    var isAVistaChecked by remember { mutableStateOf(false) }
    var isBoletoChecked by remember { mutableStateOf(false) }
    var isCartaoChecked by remember { mutableStateOf(false) }
    var isChequeChecked by remember { mutableStateOf(false) }
    var isPixChecked by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            OutlinedTextField(
                value = idValue.value,
                onValueChange = { clienteDto.value.id = it.text.toInt() },

                label = {
                    Text(
                        "Código do Pedido",
                        style = TextStyle(
                            fontSize = fontDefault
                        )
                    )
                },
                textStyle = TextStyle(fontSize = fontDefault),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .height(heightField),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = darkBlueColor,
                    focusedLabelColor = darkBlueColor,
                    cursorColor = Color.Black,
                    textColor = Color.Black
                ),
                enabled = false
            )

            OutlinedTextField(
                value = cnpjValue.value,
                onValueChange = { newValue -> },

                label = {
                    Text(
                        "Data",
                        style = TextStyle(
                            fontSize = fontDefault
                        )
                    )
                },
                textStyle = TextStyle(fontSize = fontDefault),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .height(heightField)
                    .onKeyEvent { keyEvent ->
                        when (keyEvent.key) {
                            Key.Enter -> true
                            Key.Tab -> true
                            else -> false
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = darkBlueColor,
                    focusedLabelColor = darkBlueColor,
                    cursorColor = Color.Black,
                    textColor = Color.Black
                ),
            )

            Column {
                Row(modifier = Modifier.padding(start = 30.dp)) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "Vencimento",
                        fontSize = 10.sp
                    )

                    Text(
                        modifier = Modifier.padding(start = 30.dp),
                        text = "Valor",
                        fontSize = 10.sp
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 30.dp)
                        .width(150.dp)
                ) {
                    items(mockDataList) { item ->
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.first,
                                style = TextStyle(fontSize = fontDefault)
                            )
                            Text(
                                modifier = Modifier.padding(end = 8.dp),
                                text = item.second,
                                style = TextStyle(fontSize = fontDefault)
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(start = 150.dp),
                        text = "Forma de Pagamento",
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row{
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                checked = isAVistaChecked,
                                onCheckedChange = { isAVistaChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = darkBlueColor
                                )
                            )
                            Text(
                                text = "À vista",
                                fontSize = 10.sp

                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isBoletoChecked,
                                onCheckedChange = { isBoletoChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = darkBlueColor
                                )
                            )
                            Text(
                                text = "Boleto",
                                fontSize = 10.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isCartaoChecked,
                                onCheckedChange = { isCartaoChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = darkBlueColor
                                )
                            )
                            Text(
                                text = "Cartão",
                                fontSize = 10.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChequeChecked,
                                onCheckedChange = { isChequeChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = darkBlueColor
                                )
                            )
                            Text(
                                text = "Cheque",
                                fontSize = 10.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isPixChecked,
                                onCheckedChange = { isPixChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = darkBlueColor
                                )
                            )
                            Text(
                                text = "Pix",
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
