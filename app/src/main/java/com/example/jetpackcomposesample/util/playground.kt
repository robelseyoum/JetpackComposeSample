package com.example.jetpackcomposesample.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


//val isShowing = remember { mutableStateOf(false) }
//val snackbarHostState = remember { SnackbarHostState() }

/**
Column {
Button(
onClick = {
lifecycleScope.launch {
snackbarHostState.showSnackbar(
message = "Hey look a snackbar",
actionLabel = "Hide",
duration = SnackbarDuration.Short
)
}
}) {
Text("Show SnackBar")
}
//                        SnackBarDemo(isShowing = isShowing.value, onHideSnackbar = {
//                            isShowing.value = false
//                        })
DecoupledSnackBarDemo(snackbarHostState = snackbarHostState)
}
 */


@Composable
fun DecoupledSnackBarDemo(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel ?: "",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(snackbarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )
    }
}


@Composable
fun SnackBarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(
                            onClick = onHideSnackbar,
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {
                Text("Hey look a snack bar")
            }

        }
    }
}


@Composable
fun MyBottomNavigation(
    // navigationController: NavController
) {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.BrokenImage, contentDescription = "Search") },
            selected = false,
            onClick = {
//                navigationController.navigate()
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Security, contentDescription = "Search") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            selected = false,
            onClick = {}
        )

    }
}


@Composable
fun MyDrawer() {
    Column() {
        Text("Item1")
        Text("Item2")
        Text("Item3")
        Text("Item4")
        Text("Item5")
    }
}

