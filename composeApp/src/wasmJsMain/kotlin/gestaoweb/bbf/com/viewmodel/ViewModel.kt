package gestaoweb.bbf.com.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import org.bff.erp.model.Usuario

var usuarioValidado = MutableStateFlow(false)
var falhaAutenticacao = MutableStateFlow(false)
var usuarioLogado  = MutableStateFlow(Usuario())

fun validarUsuario(nomeUsuario: String, senhaUsuario: String) {
    if(nomeUsuario == "hml" && senhaUsuario == "01") {
        usuarioValidado.value = true
    } else {
        falhaAutenticacao.value = true
    }
}
