package gestaoweb.bbf.com.networking

import gestaoweb.bbf.com.model.Cliente
import gestaoweb.bbf.com.model.Endereco
import gestaoweb.bbf.com.util.imagemSelecionada
import gestaoweb.bbf.com.viewmodel.retornoStatusCliente
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bff.erp.util.BaseApi.BASE_S3
import org.bff.erp.util.BaseApi.BASE_SERVIDOR
import org.w3c.files.File
import org.w3c.xhr.XMLHttpRequest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

suspend fun getCep(cep: String): Endereco {
    var endereco = Endereco()

    try {
        val response = window.fetch("https://viacep.com.br/ws/$cep/json/").then { res ->
            if (res.ok) {
                res.text()
            } else {
                throw Exception("Erro na requisição: ${res.status} ${res.statusText}")
            }
        }

        response.await<JsString>().toString().let { retorno ->
            val json = Json { ignoreUnknownKeys = true }
            endereco = json.decodeFromString(retorno)
            println("Dados do CEP: $endereco")

        }
    } catch (e: Exception) {
        println("Erro ao buscar o CEP: ${e.message}")
    }

    return endereco
}

 suspend fun setCadastroCliente(cliente: Cliente) {
    try {
        CoroutineScope(Dispatchers.Main).launch {

            imagemSelecionada.value?.let {
                cliente.clienteTemImagem = true
            }

            XMLHttpRequest().apply {
                open(
                    "PUT",
                    "$BASE_SERVIDOR/clientes/add"
                )

                setRequestHeader("Content-Type", "application/json")

                onload = {
                    if (status.toInt() == 200) {
                        if (responseText.toBooleanStrictOrNull() == true) {
                            retornoStatusCliente.value = 200

                            println("Cliente adicionado: $responseText")
                        } else {
                            println("Cliente não adicionado: $responseText")
                        }

                        println("onload bem-sucedido: $responseText")

                    } else {
                        retornoStatusCliente.value = 400
                        error("Erro na requisição: $status $statusText")
                    }
                }
                onerror = {
                    retornoStatusCliente.value = 400
                    error("Erro na requisição: $status $statusText")
                }

                send(Json.encodeToString(cliente))
            }
        }
    } catch (e: Exception) {
        println("Erro: ${e.message}")
    }
}

@OptIn(ExperimentalUuidApi::class)
fun setImageCliente(file: File, cliente: Cliente) {

    try {
        if (imagemSelecionada.value != null) {
            cliente.idImagem = Uuid.random().toString()
        }

        XMLHttpRequest().apply {
            open(
                "PUT",
                "https://imagens-s3.$BASE_S3/imagens/${cliente.idImagem}"
            )

            onload = {
                if (status.toInt() == 200) {
                    retornoStatusCliente.value = 200
                    println("Upload bem-sucedido: $responseText")

                } else {
                    retornoStatusCliente.value = 400
                    error("Erro na requisição: $status $statusText")
                }
            }
            onerror = {
                retornoStatusCliente.value = 400
                error("Erro na requisição: $status $statusText")
            }

            send(file)
            imagemSelecionada.value = null
        }
    } catch (e: Exception) {
        println("Erro: ${e.message}")
    }
}


