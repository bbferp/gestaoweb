package gestaoweb.bbf.com.view.clientes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import gestaoweb.bbf.com.util.Theme.backgroundCard
import gestaoweb.bbf.com.util.Theme.borderColor
import gestaoweb.bbf.com.util.Theme.colorIconClient
import gestaoweb.bbf.com.util.Theme.fontDefault
import gestaoweb.bbf.com.util.selecionarImage
import gestaoweb.bbf.com.view.clientScreen
import gestaoweb.composeapp.generated.resources.*
import gestaoweb.composeapp.generated.resources.Res
import gestaoweb.composeapp.generated.resources.ic_dpessoais
import gestaoweb.composeapp.generated.resources.ic_financeiro
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.painterResource

var limparCampos = MutableStateFlow(false)
var abrirControleCreditoView = MutableStateFlow(false)
var abrirCadastroImagemView = MutableStateFlow(false)
var abrirCadastroAromas = MutableStateFlow(false)
var abrirDadosPessoais = MutableStateFlow(false)
var abrirFinanceiro = MutableStateFlow(false)
var abrirPedido = MutableStateFlow(false)
var abrirCadastro = MutableStateFlow(false)
var abrirEditar = MutableStateFlow(false)
var abrirExcluir = MutableStateFlow(false)
var cnpjValue = mutableStateOf(TextFieldValue(""))
var idValue = mutableStateOf(TextFieldValue(""))
var telefoneValue = mutableStateOf(TextFieldValue(""))
var cpfValue = mutableStateOf(TextFieldValue(""))
var dataNascimentoValue = mutableStateOf(TextFieldValue(""))
var showDialogRetornoCadastro = MutableStateFlow(false)
var showAlert  = MutableStateFlow (false)


@Composable
fun openClientScreen() {
    AnimatedVisibility (
        visible = clientScreen.collectAsState().value,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        clientesScreen()
    }
}


@Composable
fun clientesScreen() {
    Card(
      modifier = Modifier
          .width(1200.dp)
          .height(800.dp)
          .padding(start = 200.dp,top = 30.dp,end = 40.dp, bottom = 40.dp)
    ) {
        Column(
            modifier = Modifier.background(backgroundCard)
        ) {
            Row (
                modifier = Modifier
                    .width(400.dp)
                    .background(backgroundCard, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 50.dp))
            ){
                novoCadastroIcon(onClick = { abrirCadastro.value = true })
                editarCadastroIcon(onClick = { abrirEditar.value = true })
                excluirCadastroIcon(onClick = { abrirExcluir.value = !abrirExcluir.value })
            }
            cadastrarClientes()
        }
    }
}

@Composable
fun labelScreen() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center),
            elevation = 0.dp
        ) {
            Text(
                text = labelTitle.collectAsState().value,
                color = borderColor
            )
        }
    }
}

@Composable
fun setupImagem() {
    if(abrirCadastroImagemView.collectAsState().value) {
        selecionarImage()
        abrirCadastroImagemView.value = false
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
                top = 20.dp
            ),
            style = TextStyle(fontSize = fontDefault)
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
                top = 20.dp
            ),
            style = TextStyle(fontSize = fontDefault)
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
                top = 20.dp
            ),
            style = TextStyle(fontSize = fontDefault)
        )
    }
}



