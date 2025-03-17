package gestaoweb.bbf.com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

@Serializable
class Endereco {
    var idCliente = 0
    var logradouro = ""
    var localidade = ""
    var bairro = ""
    var estado = ""
    var numero = ""
    var complemento = ""
    var cep = ""
}

class EnderecoDto {
    var logradouro by mutableStateOf("")
    var localidade by mutableStateOf("")
    var bairro by mutableStateOf("")
    var estado by mutableStateOf("")
    var numero by mutableStateOf("")
    var complemento by mutableStateOf("")
    var cep by mutableStateOf("")
}

fun enderecoToEnderecoDto(endereco: Endereco): EnderecoDto {
    return EnderecoDto().apply {
        logradouro = endereco.logradouro
        localidade = endereco.localidade
        bairro = endereco.bairro
        estado = endereco.estado
        numero = endereco.numero
        complemento = endereco.complemento
        cep = endereco.cep
    }
}