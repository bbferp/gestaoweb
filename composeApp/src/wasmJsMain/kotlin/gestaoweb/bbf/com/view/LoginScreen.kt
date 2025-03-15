package gestaoweb.bbf.com.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestaoweb.bbf.com.util.Theme.darkBlueColor
import gestaoweb.bbf.com.util.Theme.gradientBackground
import gestaoweb.bbf.com.viewmodel.falhaAutenticacao
import gestaoweb.bbf.com.viewmodel.usuarioLogado
import gestaoweb.bbf.com.viewmodel.validarUsuario
import gestaoweb.composeapp.generated.resources.*
import gestaoweb.composeapp.generated.resources.Res
import gestaoweb.composeapp.generated.resources.logo
import gestaoweb.composeapp.generated.resources.visibility
import gestaoweb.composeapp.generated.resources.visibility_off
import kotlinx.browser.window
import org.jetbrains.compose.resources.painterResource

@Composable
 fun loginScreen() {
    showUpLoginError()
    authenticationFields()
}

@Composable
fun authenticationFields() {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val focusRequesterUsuario = remember { FocusRequester() }
    val focusRequesterSenha = remember { FocusRequester() }
    val focusRequesterLogin = remember { FocusRequester() }

    Row {
        ShowLogo()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 50.dp)
                    .size(width = 400.dp, height = 420.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(gradientBackground)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {

                    OutlinedTextField(
                        value = usuario,
                        singleLine = true,
                        onValueChange = { usuario = it },
                        label = { Text("Usuário") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterUsuario)
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Enter) {
                                    focusRequesterSenha.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        leadingIcon = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.person),
                                    contentDescription = "Usuário",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.LightGray,
                            unfocusedLabelColor = Color.LightGray,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            cursorColor = Color.White,
                            textColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha") },
                        singleLine = true,
                        modifier = Modifier
                            .focusRequester(focusRequesterSenha)
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .padding(bottom = 50.dp)
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Enter) {
                                    focusRequesterLogin.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        leadingIcon = {
                            Icon(
                                painterResource(Res.drawable.lock),
                                contentDescription = "Senha",
                                tint = Color.White
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.LightGray,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = Color.White,
                            textColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                val visibilityIcon = if (isPasswordVisible) Res.drawable.visibility else Res.drawable.visibility_off
                                Icon(
                                    painter = painterResource( visibilityIcon),
                                    contentDescription = if (isPasswordVisible) "Ocultar senha" else "Mostrar senha",
                                    tint = Color.White
                                )
                            }
                        }
                    )

                    Button(
                        onClick = {
                            validarUsuario(usuario, senha)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterLogin),
                        colors = ButtonDefaults.buttonColors(backgroundColor = darkBlueColor)
                    ) {
                        Text(text = "Entrar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun footerText(){
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = "Terezinha 3201, Recanto Tropical\n" +
                "Cascavel-Pr\n" +
                "CEP: 85807-140\n" +
                "Telefone: 45 99862-4073" +
                "Essence e Aromas ®",
        color = Color.Black,
        style = TextStyle(fontSize = 12.sp)

    )
}

@Composable
fun ShowLogo() {
    AnimatedVisibility(true) {
        Column(
            Modifier
                .padding(100.dp)
                .width(350.dp),
            horizontalAlignment = Alignment.End) {
            Image(painterResource(Res.drawable.logo), null)
        }
    }
}

@Composable
fun showUpLoginError() {
    if (falhaAutenticacao.collectAsState().value) {
        AlertDialog(
            onDismissRequest = { falhaAutenticacao.value = false },
            title = { Text("Erro de Autenticação") },
            text = { Text("Usuário ou senha estão incorretos.") },
            confirmButton = {
                TextButton(onClick = {
                    falhaAutenticacao.value = false
                }) {
                    Text(
                        text = "OK",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            },
            contentColor = Color.White,
            backgroundColor = darkBlueColor,
        )
    }
}

@Composable
 fun fieldLogOut() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(end = 40.dp, top = 15.dp)
                .align(Alignment.TopEnd),
            text = "Usuário Logado: ${usuarioLogado.collectAsState().value.nome}",
            color = Color.Black,
            style = TextStyle(fontSize = 15.sp)
        )

        IconButton(
            onClick = {
                window.location.reload()
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Log Out")
        }
    }
}