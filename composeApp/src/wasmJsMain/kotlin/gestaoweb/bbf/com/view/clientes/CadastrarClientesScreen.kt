package gestaoweb.bbf.com.view.clientes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestaoweb.bbf.com.model.ClienteDto
import gestaoweb.bbf.com.model.EnderecoDto
import gestaoweb.bbf.com.util.Theme.colorIconClient
import gestaoweb.bbf.com.util.Theme.darkBlueColor
import gestaoweb.bbf.com.util.Theme.fontDefault
import gestaoweb.bbf.com.util.Theme.heightField
import gestaoweb.bbf.com.viewmodel.*
import gestaoweb.composeapp.generated.resources.*
import gestaoweb.composeapp.generated.resources.Res
import gestaoweb.composeapp.generated.resources.ic_dpessoais
import gestaoweb.composeapp.generated.resources.ic_financeiro
import kotlinx.coroutines.flow.MutableStateFlow
import org.bff.erp.util.Format.formatCnpj
import org.bff.erp.util.Format.formatCpf
import org.bff.erp.util.Format.formatDataNascimento
import org.bff.erp.util.Format.formatTelefone
import org.jetbrains.compose.resources.painterResource

var limparCampos = MutableStateFlow(false)
var abrirControleCreditoView = MutableStateFlow(false)
var abrirCadastroImagemView = MutableStateFlow(false)
var abrirCadastroAromas = MutableStateFlow(false)
var abrirDadosPessoais = MutableStateFlow(false)
var abrirFinanceiro = MutableStateFlow(false)
var abrirPedido = MutableStateFlow(false)
var cnpjValue = mutableStateOf(TextFieldValue(""))
var telefoneValue = mutableStateOf(TextFieldValue(""))
var cpfValue = mutableStateOf(TextFieldValue(""))
var dataNascimentoValue = mutableStateOf(TextFieldValue(""))
var showDialogRetornoCadastro = MutableStateFlow(false)
var showAlert  = MutableStateFlow (false)


@Composable
fun cadastrarClientes() {
    var errorMessage by remember { mutableStateOf("") }
    var cpfDto = ""
    val focusRequesterNome = remember { FocusRequester() }
    val focusRequesterNomeFantasia = remember { FocusRequester() }
    val cnpj = remember { FocusRequester() }
    val logradouro = remember { FocusRequester() }
    val search = remember { FocusRequester() }
    val cidade = remember { FocusRequester() }
    val bairro = remember { FocusRequester() }
    val numero = remember { FocusRequester() }
    val complemento = remember { FocusRequester() }
    val estado = remember { FocusRequester() }
    val cep = remember { FocusRequester() }
    val email = remember { FocusRequester() }
    val dataNascimento = remember { FocusRequester() }
    val rg = remember { FocusRequester() }
    val telefone = remember { FocusRequester() }
    val observacao = remember { FocusRequester() }
    val cadastrar = remember { FocusRequester() }



    Card(
        modifier = Modifier
            .padding(start = 180.dp, end = 25.dp, top = 50.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                dadosPessoaisIcon(onClick = {abrirDadosPessoais.value = !abrirDadosPessoais.value})
                financeiroIcon  (onClick = { abrirFinanceiro.value = !abrirFinanceiro.value})
                pedidoIcon  (onClick = { abrirPedido.value = !abrirPedido.value})
            }

            Row {
                OutlinedTextField(
                    value = cnpjValue.value,
                    onValueChange = { newValue ->
                        val cleaned = newValue.text.replace("[^\\d]".toRegex(), "")
                        val formattedCnpj = when {
                            cleaned.length <= 11 -> formatCpf(cleaned)
                            cleaned.length > 11 -> formatCnpj(cleaned)
                            else -> cleaned
                        }

                        cnpjValue.value = TextFieldValue(
                            AnnotatedString(formattedCnpj),
                            TextRange(calculateCursorPosition(cleaned.length, formattedCnpj.length, formattedCnpj))
                        )
                        clienteDto.value.cnpj_cpf = formattedCnpj
                    },

                    label = {
                        Text(
                            "CNPJ/CPF",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },
                    textStyle = TextStyle(fontSize = fontDefault),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .height(heightField)
                        .width(180.dp)
                        .focusRequester(cnpj)
                        .onKeyEvent { keyEvent ->
                            when {
                                keyEvent.key == Key.Enter -> {
                                    validarCpfCnpj(clienteDto.value.cnpj_cpf)
                                    true
                                }
                                keyEvent.key == Key.Tab -> {
                                    focusRequesterNome.requestFocus()
                                    true
                                }
                                else -> false
                            }

                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    ),
                    trailingIcon = {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Filled.Clear),
                            contentDescription = "Cpj/cpf clean",
                            modifier = Modifier
                                .size(10.dp)
                                .clickable { cnpjValue.value = TextFieldValue() }
                        )
                    }
                )

                OutlinedTextField(
                    value = clienteDto.value.nome,
                    onValueChange = { clienteDto.value.nome = it },
                    label = {
                        Text(
                            "Nome Cliente/RazãoSocial${if (clienteDto.value.nome.isEmpty() && errorMessage.isNotEmpty()) " *" else ""}",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .padding(start = 8.dp, end = 20.dp)
                        .width(300.dp)
                        .focusRequester(focusRequesterNome)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                focusRequesterNomeFantasia.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )
            }

            Row {
                OutlinedTextField(
                    value = clienteDto.value.fantasia,
                    onValueChange = { clienteDto.value.fantasia = it },
                    label = {
                        Text(
                            "Nome Fantasia",
                            style = TextStyle(fontSize = fontDefault)
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .width(300.dp)
                        .focusRequester(focusRequesterNomeFantasia)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                rg.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = clienteDto.value.rg_ie,
                    onValueChange = { clienteDto.value.rg_ie = it },
                    label = {
                        Text(
                            "RG/IE",
                            style = TextStyle(fontSize = fontDefault)
                        )
                    },
                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(heightField)
                        .width(200.dp)
                        .focusRequester(rg)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                cep.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )
            }

            Row {
                OutlinedTextField(
                    value = enderecoDto.value.cep,
                    onValueChange = { enderecoDto.value.cep = it },
                    label = {
                        Text(
                            "Cep${if (enderecoDto.value.cep.isEmpty() && errorMessage.isNotEmpty()) " *" else ""}",
                            style = TextStyle(fontSize = fontDefault)
                        )
                    },
                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .width(200.dp)
                        .height(heightField)
                        .focusRequester(cep)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Enter) {
                                if (enderecoDto.value.cep.isNotEmpty()) {
                                    fetchCep(enderecoDto.value.cep)
                                }
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    ),
                    trailingIcon = {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Filled.Search),
                            contentDescription = "Search",
                            modifier = Modifier
                                .clickable { fetchCep(enderecoDto.value.cep) }
                                .focusRequester(search)
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.key == Key.Enter) {
                                        if (enderecoDto.value.cep.isNotEmpty()) {
                                            fetchCep(enderecoDto.value.cep)
                                        }
                                        true
                                    } else {
                                        false
                                    }
                                }
                        )
                    },
                    maxLines = 1,
                    singleLine = true
                )
                OutlinedTextField(
                    value = enderecoDto.value.logradouro,
                    onValueChange = { enderecoDto.value.logradouro = it },
                    label = {
                        Text(
                            "Logradouro",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .padding(start = 8.dp)
                        .width(350.dp)
                        .focusRequester(logradouro)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                numero.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = enderecoDto.value.numero,
                    onValueChange = { enderecoDto.value.numero = it },
                    label = {
                        Text(
                            "Numero",
                            style = TextStyle(fontSize = fontDefault)
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(heightField)
                        .width(100.dp)
                        .focusRequester(numero)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                bairro.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = enderecoDto.value.bairro,
                    onValueChange = { enderecoDto.value.bairro = it },
                    label = {
                        Text(
                            "Bairro",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(heightField)
                        .width(250.dp)
                        .focusRequester(bairro)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                cidade.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )
            }

            Row{
                OutlinedTextField(
                    value = enderecoDto.value.localidade,
                    onValueChange = { enderecoDto.value.localidade = it },
                    label = {
                        Text(
                            "Cidade",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .width(250.dp)
                        .focusRequester(cidade)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                estado.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = enderecoDto.value.estado,
                    onValueChange = { enderecoDto.value.estado = it },
                    label = {
                        Text(
                            "Estado",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(heightField)
                        .width(150.dp)
                        .focusRequester(estado)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                complemento.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = enderecoDto.value.complemento,
                    onValueChange = { enderecoDto.value.complemento = it },
                    label = {
                        Text(
                            "Complemento",
                            style = TextStyle(fontSize = fontDefault)
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .width(250.dp)
                        .padding(start = 8.dp)
                        .focusRequester(complemento)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                telefone.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )
            }

            Row {
                OutlinedTextField(
                    value = telefoneValue.value,
                    onValueChange = { newValue ->
                        newValue.text.replace("[^\\d]".toRegex(), "").let { cleaned ->
                            formatTelefone(cleaned).let {
                                telefoneValue.value = TextFieldValue(
                                    AnnotatedString(it),
                                    TextRange(calculateCursorPosition(cleaned.length, it.length, it))
                                )
                                clienteDto.value.telefone = it
                            }

                        }

                    },
                    label = {
                        Text(
                            "Telefone${if (clienteDto.value.telefone.isEmpty() && errorMessage.isNotEmpty()) " *" else ""}",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },

                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .height(heightField)
                        .width(150.dp)
                        .focusRequester(telefone)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                dataNascimento.requestFocus()
                                true
                            } else {
                                false
                            }
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = clienteDto.value.email,
                    onValueChange = { clienteDto.value.email = it },
                    label = {
                        Text(
                            "E-mail",
                            style = TextStyle(
                                fontSize = fontDefault
                            )
                        )
                    },
                    textStyle = TextStyle(fontSize = fontDefault),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(heightField)
                        .width(200.dp)
                        .focusRequester(email)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Tab) {
                                observacao.requestFocus()
                                true
                            } else {
                                false
                            }
                        },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = darkBlueColor,
                        focusedLabelColor = darkBlueColor,
                        cursorColor = Color.Black,
                        textColor = Color.Black
                    )
                )
            }

            OutlinedTextField(
                value = clienteDto.value.observacao,
                onValueChange = { clienteDto.value.observacao = it },
                label = {
                    Text(
                        "Observação",
                        style = TextStyle(fontSize = fontDefault)
                    )
                },

                textStyle = TextStyle(fontSize = fontDefault),
                modifier = Modifier
                    .width(500.dp)
                    .height(heightField)
                    .focusRequester(observacao)
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Tab) {
                            cadastrar.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = darkBlueColor,
                    focusedLabelColor = darkBlueColor,
                    cursorColor = Color.Black,
                    textColor = Color.Black
                )
            )

            Row {
                Button(
                    onClick = {
                        errorMessage = ""
                        if(clienteDto.value.cnpj_cpf.isEmpty()){
                            clienteDto.value.cnpj_cpf = cpfDto
                        }

                        if (validarCampos(clienteDto.value, enderecoDto.value)) {
                            bindCadastroCliente()
                        } else {
                            errorMessage = "Por favor, preencha todos os campos obrigatórios(*)."
                        }
                    },
                    modifier = Modifier
                        .padding(start = 100.dp, top = 10.dp)
                        .height(35.dp)
                        .width(250.dp)
                        .focusRequester(cadastrar),
                    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
                ) {
                    Text(text = "Cadastrar", color = Color.White)
                }
                observarRetornoStatus()
                if (errorMessage.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = errorMessage,
                        color = Color.Red,
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}

fun calculateCursorPosition(oldLength: Int, newLength: Int, formattedText: String): Int {
    var cursorPosition = newLength
    val formatCharactersCount = formattedText.count { it == '.' || it == '-' || it == '/' || it == ' ' }

    if (newLength > oldLength) {
        cursorPosition += formatCharactersCount
    } else if (newLength < oldLength) {
        cursorPosition -= formatCharactersCount
    }

    return cursorPosition.coerceIn(0, formattedText.length)
}


@Composable
private fun observarRetornoStatus() {
    when(retornoStatusCliente.collectAsState().value){
        200 -> showDialogRetornoCadastro.value = true
        400 -> showDialogRetornoCadastro.value = true
    }
}

private fun validarCampos(cliente: ClienteDto, end: EnderecoDto): Boolean {
    return cliente.nome.isNotEmpty() &&
            cliente.telefone.isNotEmpty() &&
            cliente.cnpj_cpf.isNotEmpty() &&
            end.cep.isNotEmpty()
}

@Composable
fun iconImagem(onClick: () -> Unit) {
    Row(modifier = Modifier
        .padding(8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                tint = Color.Black,
                imageVector = Icons.Default.Menu,
                contentDescription = "Cadastrar imagem"
            )
        }

        Text(
            text = "Selecionar Imagem",
            color = Color.Black,
            modifier = Modifier.padding(
                start = 8.dp, top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun iconCadastroAroma(onClick: () -> Unit) {
    Row(modifier = Modifier
        .padding(8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                tint = Color.Black,
                imageVector = Icons.Default.Menu,
                contentDescription = "Cadastrar Produtos"
            )
        }

        Text(
            text = "Cadastrar Produtos",
            color = Color.Black,
            modifier = Modifier.padding(
                start = 8.dp, top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
private fun iconControleCredito(onClick: () -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                tint = Color.Black,
                imageVector = Icons.Default.Lock,
                contentDescription = "Controle de Crédito"
            )
        }

        Text(
            text = "Controle de Crédito",
            color = Color.Black,
            modifier = Modifier.padding(
                top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun dadosPessoaisIcon(onClick: () -> Unit){
    Row(modifier =
        Modifier.padding(8.dp)
        ) {
        IconButton(onClick = onClick) {
            Icon(
                painterResource(Res.drawable.ic_dpessoais),
                contentDescription = "D.PESSOAIS",
                tint = colorIconClient,
                )
        }

        Text(
            text = "D.PESSOAIS",
            color = Color.Black,
            modifier = Modifier.padding(
                top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun financeiroIcon(onClick: () -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                painterResource(Res.drawable.ic_financeiro),
                tint = colorIconClient,
                contentDescription = "FINANCEIRO"
            )
        }

        Text(
            text = "FINANCEIRO",
            color = Color.Black,
            modifier = Modifier.padding(
                top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun pedidoIcon(onClick: () -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        IconButton(onClick = onClick) {
            Icon(
                painterResource(Res.drawable.ic_pedido),
                contentDescription = "D.Pessoais",
                tint = colorIconClient,
                )
        }

        Text(
            text = "PEDIDO",
            color = Color.Black,
            modifier = Modifier.padding(
                top = 16.dp
            ),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}


fun validarCpfCnpj(cpfCnpj: String) {
    getAllClientesList.value.forEach {
        if (cpfCnpj == it.cnpj_cpf){
            showAlert.value = true
            clienteDto.value.cnpj_cpf = ""
        }
    }
}
