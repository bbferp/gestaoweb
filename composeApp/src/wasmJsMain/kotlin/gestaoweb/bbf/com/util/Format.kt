package org.bff.erp.util

object Format {

    fun formatCnpj(cnpj: String): String {
        return when {
            cnpj.length <= 2 -> cnpj
            cnpj.length <= 5 -> cnpj.replace(Regex("^(\\d{2})(\\d{1,3})"), "$1.$2")
            cnpj.length <= 8 -> cnpj.replace(Regex("^(\\d{2})(\\d{3})(\\d{1,3})"), "$1.$2.$3")
            cnpj.length <= 12 -> cnpj.replace(Regex("^(\\d{2})(\\d{3})(\\d{3})(\\d{1,4})"), "$1.$2.$3/$4")
            else -> cnpj.replace(Regex("^(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})"), "$1.$2.$3/$4-$5")
        }
    }

    fun formatCpf(cpf: String): String {
        return when {
            cpf.length <= 3 -> cpf
            cpf.length <= 6 -> cpf.replace(Regex("^(\\d{3})(\\d{1,3})"), "$1.$2")
            cpf.length <= 9 -> cpf.replace(Regex("^(\\d{3})(\\d{3})(\\d{1,3})"), "$1.$2.$3")
            else -> cpf.replace(Regex("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})"), "$1.$2.$3-$4")
        }
    }

    fun formatTelefone(telefone: String): String {
        val cleaned = telefone.replace("[^\\d]".toRegex(), "")

        return when {
            cleaned.length < 3 -> cleaned
            cleaned.length < 7 -> "(${cleaned.take(2)}) ${cleaned.drop(2)}"
            cleaned.length < 11 -> "(${cleaned.take(2)}) ${cleaned[2]} ${cleaned.substring(3)}"
            cleaned.length == 11 -> "(${cleaned.take(2)}) ${cleaned[2]} ${cleaned.substring(3, 7)}-${cleaned.takeLast(4)}"
            else -> cleaned
        }
    }

    fun formatDataNascimento(data: String): String {
        val cleaned = data.replace("[^\\d]".toRegex(), "")

        return when {
            cleaned.length < 2 -> cleaned
            cleaned.length < 4 -> "${cleaned.take(2)}/${cleaned.drop(2)}"
            cleaned.length < 8 -> "${cleaned.take(2)}/${cleaned.substring(2, 4)}/${cleaned.drop(4)}"
            cleaned.length == 8 -> "${cleaned.take(2)}/${cleaned.substring(2, 4)}/${cleaned.substring(4, 8)}"
            else -> cleaned
        }
    }
}