package gestaoweb.bbf.com

import androidx.compose.runtime.*
import gestaoweb.bbf.com.util.Theme
import gestaoweb.bbf.com.view.*
import gestaoweb.bbf.com.view.clientes.openClientScreen
import gestaoweb.bbf.com.viewmodel.usuarioValidado

@Composable
fun App() {
    Theme.MyAppTheme {
      /*  if (usuarioValidado.collectAsState().value) {
            setupNavigation()
            navigationRail()
            fieldLogOut()
        } else {
            loginScreen()
        }*/
        openClientScreen()
        setupNavigation()
        navigationRail()
        fieldLogOut()
    }
}