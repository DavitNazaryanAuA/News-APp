package com.aua.davitnazaryan.newsapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { newValue: String -> searchText = newValue },
        modifier = Modifier
            .background(Color.White),
        placeholder = { Text("Search") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        leadingIcon = {
            IconButton(
                onClick = { },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        }
    )
}
