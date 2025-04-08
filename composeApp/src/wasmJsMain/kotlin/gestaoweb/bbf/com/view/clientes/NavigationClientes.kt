package gestaoweb.bbf.com.view.clientes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow

val labelTitle = MutableStateFlow("")


@Composable
fun navigateToEditarCliente() {
    AnimatedVisibility (
        visible = abrirEditar.collectAsState().value,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        editarClienteScreen()
    }
}

// SubTelas

@Composable
fun navigateToDadosPessoais() {
    AnimatedVisibility (
        visible = abrirDadosPessoais.collectAsState().value,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        labelTitle.value = ""
        labelTitle.value = "DADOS PESSOAIS"
        if (abrirDadosPessoais.value) {
            abrirPedido.value = false
        }
        cadastroScreen()
    }
}


@Composable
fun navigateToPedido() {
    AnimatedVisibility (
        visible = abrirPedido.collectAsState().value,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        if (abrirPedido.value) {
            abrirDadosPessoais.value = false
        }
        labelTitle.value = ""
        labelTitle.value = "CADASTRO DE PEDIDO"
        pedidoScreen()
    }
}