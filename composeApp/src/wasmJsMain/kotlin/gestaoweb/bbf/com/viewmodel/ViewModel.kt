package gestaoweb.bbf.com.viewmodel

import androidx.compose.runtime.mutableStateOf
import gestaoweb.bbf.com.model.*
import gestaoweb.bbf.com.networking.getCep
import gestaoweb.bbf.com.networking.setCadastroCliente
import gestaoweb.bbf.com.view.abrirViewLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.bff.erp.model.Usuario

var usuarioValidado = MutableStateFlow(false)
var falhaAutenticacao = MutableStateFlow(false)
var usuarioLogado  = MutableStateFlow(Usuario())
var retornoStatusCliente = MutableStateFlow(0)
var enderecoDto = mutableStateOf(EnderecoDto())
var clienteDto = mutableStateOf(ClienteDto())
var getAllClientesList = MutableStateFlow<MutableList<Cliente>>(mutableListOf())


fun validarUsuario(nomeUsuario: String, senhaUsuario: String) {
    if(nomeUsuario == "hml" && senhaUsuario == "01") {
        usuarioValidado.value = true
    } else {
        falhaAutenticacao.value = true
    }
}

fun fetchCep(cep: String) {
    CoroutineScope(Dispatchers.Main).launch {
        enderecoDto.value = enderecoToEnderecoDto(getCep(cep))
    }
}

fun bindCadastroCliente() {
    CoroutineScope(Dispatchers.Main).launch {
        setCadastroCliente(convertDtoToCadastroCliente())
        abrirViewLoading.value = true
        delay(3000)
        abrirViewLoading.value = false
    }
}

fun convertDtoToCadastroCliente(): Cliente {
    return Cliente().apply {
        if (clienteDto.value.idImagem != "") {
            idImagem = clienteDto.value.idImagem
        }
        nome = clienteDto.value.nome.trim()
        fantasia = clienteDto.value.fantasia.trim()
        cnpj_cpf = clienteDto.value.cnpj_cpf.trim()
        endereco = getEnderecoCadastrado(idCliente)
        email = clienteDto.value.email.trim()
        dataNascimento = clienteDto.value.dataNascimento.trim()
        rg_ie = clienteDto.value.rg_ie.trim()
        telefone = clienteDto.value.telefone.trim()
        observacao = clienteDto.value.observacao.trim()
        limiteCredito = clienteDto.value.limiteCredito.trim()
        tipoVenda = clienteDto.value.tipoVenda
        formaAutorizada = clienteDto.value.formaAutorizada
        vendedor = clienteDto.value.vendedor.trim()
    }
}

fun getEnderecoCadastrado(id: Int): Endereco {
    return Endereco().apply {
        idCliente = id
        logradouro = enderecoDto.value.logradouro.trim()
        localidade = enderecoDto.value.localidade.trim()
        bairro = enderecoDto.value.bairro.trim()
        numero = enderecoDto.value.numero.trim()
        estado = enderecoDto.value.estado.trim()
        complemento = enderecoDto.value.complemento.trim()
        cep = enderecoDto.value.cep.trim()
    }
}

