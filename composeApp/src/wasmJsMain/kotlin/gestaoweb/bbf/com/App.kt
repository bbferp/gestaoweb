package gestaoweb.bbf.com

import androidx.compose.runtime.*
import gestaoweb.bbf.com.util.Theme
import gestaoweb.bbf.com.view.fieldLogOut
import gestaoweb.bbf.com.view.navigationRail
import gestaoweb.bbf.com.view.setupNavigation

@Composable
fun App() {
    Theme.MyAppTheme {
       /* if (usuarioValidado.collectAsState().value) {
            setupNavigation()
            navigationRail()
           // setupNavigationClientes()
            fieldLogOut()
        } else {
            loginScreen()
        }*/
        setupNavigation()
        navigationRail()
        // setupNavigationClientes()
        fieldLogOut()
    }
}