package com.example.jetpackcomposesample.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.size
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.TextStyle


@AndroidEntryPoint
class RecipeFragment: Fragment() {

    private var recipeId: MutableState<Int> = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(("recipeId"))?.let { rId -> recipeId.value = rId }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if(recipeId.value != -1){
                            "Selected recipeId: ${recipeId.value}"
                        }else{
                            "Loading...."
                        },
                        style = androidx.compose.ui.text.TextStyle(fontSize = 21.sp)
                    )
                }
            }
        }
    }
}