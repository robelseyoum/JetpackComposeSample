package com.example.jetpackcomposesample.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.jetpackcomposesample.R
import com.example.jetpackcomposesample.presentation.components.RecipeCard
import com.example.jetpackcomposesample.presentation.BaseApplication
import com.example.jetpackcomposesample.presentation.components.RecipeList
import com.example.jetpackcomposesample.presentation.components.SearchAppBar
import androidx.navigation.findNavController
import com.example.jetpackcomposesample.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {
                    val isShowing = remember { mutableStateOf(false) }
                    val snackbarHostState = remember { SnackbarHostState() }

                    Column {
                        Button(
                            onClick = {
                                snackbarHostState.showSnackbar(
                                    message = "Hey look a snackbar",
                                    
                                )
                            }) {
                            Text("Show snackbar")
                        }
//                        SnackBarDemo(isShowing = isShowing.value, onHideSnackbar = {
//                            isShowing.value = false
//                        })
                    }
//
//                    val recipes = viewModel.recipes.value
//                    val query = viewModel.query.value
//                    val loading = viewModel.loading.value
//                    val selectedCategory = viewModel.selectedCategory.value
//                    val page = viewModel.page.value
//
//                    Scaffold(
//                        topBar = {
//                            SearchAppBar(
//                                query = query,
//                                onQueryChanged = viewModel::onQueryChanged,
//                                onExecuteSearch = viewModel::newSearch,
//                                selectedCategory = selectedCategory,
//                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
//                                onToggleTheme = { application.toggleLightTheme() }
//                            )
//                        }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(MaterialTheme.colors.surface)
//                        ) {
//                            LazyColumn {
//                                itemsIndexed(
//                                    items = recipes
//                                ) { index, recipe ->
//                                    RecipeCard(recipe = recipe, onClick = {})
//                                }
//                            }
//                            //circularIndeterminateProgressBar(isDisplayed = loading, 0.3f)
//                            RecipeList(
//                                loading = loading,
//                                recipes = recipes,
//                                onChangeScrollPosition = {},
//                                page = page,
//                                onTriggerNextPage = { },
//                                onNavigateToRecipeDetailScreen = {
//                                    val bundle = Bundle()
//                                    bundle.putInt("recipeId", it)
//                                    findNavController().navigate(R.id.viewRecipe, bundle)
//                                }
//                            )
//                        }
//                    }
                }
            }
        }
    }

}


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
                                text = "Hide",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text("Hey look a snack bar")
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

