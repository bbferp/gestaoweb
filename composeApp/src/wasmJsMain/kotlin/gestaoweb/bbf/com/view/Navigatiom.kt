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
import gestaoweb.bbf.com.view.clientes.cadastrarClientes
import kotlinx.coroutines.flow.MutableStateFlow

val itemMenuSelected = MutableStateFlow(0)

@Composable
 fun setupNavigation() {
    itemMenuSelected.collectAsState().value.let {
        when (it) {
            0 -> dashBoardScreen()
            1 -> cadastrarClientes()
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

    NavigationRail(
        modifier = Modifier.width(180.dp),
        elevation = 3.dp,
        backgroundColor = Theme.transparentColor,
    ) {

        menuListNames.forEachIndexed { index, item ->
            val isSelected = itemSelected == index

            val isHovered by remember { mutableStateOf(false) }
            val size by animateDpAsState(targetValue = when {
                isHovered -> 45.dp
                isSelected -> 28.dp
                else -> 24.dp
            })

            NavigationRailItem(
                icon = {
                    Icon(
                        iconsMenu()[index],
                        contentDescription = null,
                        modifier = Modifier.size(size),
                        tint = Color.White

                    )
                },
                label = {
                    Text(
                        item,
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = TextStyle(
                            color = Color.White,
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