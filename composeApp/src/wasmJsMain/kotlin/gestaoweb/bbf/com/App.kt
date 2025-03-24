package gestaoweb.bbf.com

import androidx.compose.runtime.*
import gestaoweb.bbf.com.util.Theme
import gestaoweb.bbf.com.view.fieldLogOut
import gestaoweb.bbf.com.view.loginScreen
import gestaoweb.bbf.com.view.navigationRail
import gestaoweb.bbf.com.view.setupNavigation
import gestaoweb.bbf.com.viewmodel.usuarioValidado

@Composable
fun App() {
    Theme.MyAppTheme {
   /*     if (usuarioValidado.collectAsState().value) {
            setupNavigation()
            navigationRail()
            fieldLogOut()
        } else {
            loginScreen()
        }*/
        setupNavigation()
        navigationRail()
        fieldLogOut()
    }
}