package gestaoweb.bbf.com.util

import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File
import org.w3c.files.get


val imagemSelecionada = MutableStateFlow<File?>(null)

fun selecionarImage() {
    val input = document.createElement("input") as HTMLInputElement
    input.type = "file"

    input.addEventListener("change") {
        val fileList = input.files
        if (fileList != null && fileList.length > 0) {
            val file = fileList[0]
            if (file != null) {
                imagemSelecionada.value = file
            }
        }
    }
    input.click()
}
