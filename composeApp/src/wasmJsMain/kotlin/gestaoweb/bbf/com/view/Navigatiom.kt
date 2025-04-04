package gestaoweb.bbf.com.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestaoweb.bbf.com.util.Menu.iconsMenu
import gestaoweb.bbf.com.util.Menu.menuListNames
import gestaoweb.bbf.com.util.Theme
import gestaoweb.bbf.com.util.Theme.colorIconClient
import gestaoweb.bbf.com.view.clientes.clientesScreen
import gestaoweb.composeapp.generated.resources.Res
import gestaoweb.composeapp.generated.resources.logo
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.painterResource

val itemMenuSelected = MutableStateFlow(0)

@Composable
 fun setupNavigation() {
    itemMenuSelected.collectAsState().value.let {
        when (it) {
            0 -> dashBoardScreen()
            1 -> clientesScreen()
            2 -> "Colaborador"
            3 -> "Produtos"
            4 -> "Estoque"
            5 -> "OS"
            6 -> "Configuração"
            else -> {}
        }
    }
}

@Composable
 fun navigationRail() {
    val itemSelected by itemMenuSelected.collectAsState()

    Icon(
        painter = painterResource(Res.drawable.logo),
        contentDescription = "Ícone do Menu",
        modifier = Modifier
            .size(150.dp)

            .padding(start = 25.dp, bottom =5.dp),
        tint = colorIconClient
    )

    NavigationRail(
        modifier = Modifier
            .padding(top = 140.dp)
            .width(180.dp),
        backgroundColor = Theme.transparentColor,
        elevation = 0.dp
    ) {
        menuListNames.forEachIndexed { index, item ->
            val isSelected = itemSelected == index

            val isHovered by remember { mutableStateOf(false) }
            val size by animateDpAsState(targetValue = when {
                isHovered -> 65.dp
                isSelected -> 28.dp
                else -> 24.dp
            })

            NavigationRailItem(
                icon = {
                    Icon(
                        iconsMenu()[index],
                        contentDescription = null,
                        modifier = Modifier.size(size),
                        tint = if (isSelected) Color(0xFFFC7900) else Color.White
                    )
                },
                label = {
                    Text(
                        item,
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = TextStyle(
                            color = if (isSelected) Color(0xFFFC7900) else Color.White,
                            fontSize = 10.sp
                        )
                    )
                },
                selected = isSelected,
                onClick = {
                    itemMenuSelected.value = index
                },
            )
        }
    }
}