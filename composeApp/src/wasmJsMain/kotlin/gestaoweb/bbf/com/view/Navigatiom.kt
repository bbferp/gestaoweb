package gestaoweb.bbf.com.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gestaoweb.bbf.com.view.clientes.cadastrarClientes
import gestaoweb.bbf.com.view.clientes.clientesCadastrados
import kotlinx.coroutines.flow.MutableStateFlow

val itemMenuSelected = MutableStateFlow(0)

@Composable
 fun setupNavigation() {
    itemMenuSelected.collectAsState().value.let {
        when (it) {
            0 -> dashBoardScreen()
            1 -> cadastrarClientes()
            2 -> clientesCadastrados()
        }
    }
}

@Composable
 fun navigationRail() {
    val items = listOf(
        "DashBoard", "Clientes", "OS",
    )
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Person,
        Icons.Default.Add,
    )

    val itemSelected by itemMenuSelected.collectAsState()

    NavigationRail(
        modifier = Modifier.width(120.dp),
        elevation = 8.dp,
        backgroundColor = Color.Blue,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = itemSelected == index

            val isHovered by remember { mutableStateOf(false) }
            val size by animateDpAsState(targetValue = when {
                isHovered -> 40.dp
                isSelected -> 28.dp
                else -> 24.dp
            })

            NavigationRailItem(
                icon = {
                    Icon(
                        icons[index],
                        contentDescription = null,
                        modifier = Modifier.size(size)

                    )
                },
                label = {
                    Text(
                        item,
                        modifier = Modifier.padding(vertical = 8.dp)
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