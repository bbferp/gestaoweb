package gestaoweb.bbf.com.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import gestaoweb.composeapp.generated.resources.*
import gestaoweb.composeapp.generated.resources.Res
import gestaoweb.composeapp.generated.resources.client
import gestaoweb.composeapp.generated.resources.colaborador
import gestaoweb.composeapp.generated.resources.dashboard
import org.jetbrains.compose.resources.painterResource

object Menu {

    val menuListNames = listOf(
        "DashBoard", "Clientes", "Colaborador","Produtos","Estoque","OS","Configuração"
    )

    @Composable
    fun iconsMenu(): List<Painter> {
    return  listOf(
            painterResource(Res.drawable.dashboard),
            painterResource(Res.drawable.client),
            painterResource(Res.drawable.colaborador),
            painterResource(Res.drawable.products),
            painterResource(Res.drawable.estoque),
            painterResource(Res.drawable.settingsOS),
            painterResource(Res.drawable.settings),
        )
    }
}