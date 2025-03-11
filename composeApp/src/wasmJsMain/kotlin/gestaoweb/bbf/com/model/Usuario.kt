package org.bff.erp.model

import kotlinx.serialization.Serializable


@Serializable
 class Usuario {
    var id = 0
    var nome = ""
    var empresa = ""
    var senha = ""
 }
