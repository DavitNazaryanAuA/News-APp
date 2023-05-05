package com.aua.davitnazaryan.newsapp.views

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aua.davitnazaryan.newsapp.util.GsonSerializer
import com.aua.davitnazaryan.newsapp.model.NewsCategory
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.navigation.Screen
import com.aua.davitnazaryan.newsapp.util.Resource
import com.aua.davitnazaryan.newsapp.viewModel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: NewsViewModel,
    navController: NavController
) {
    val topHeadlinesState = viewModel.topHeadlines.observeAsState()
    val newsCategoryState = viewModel.newsCategoryState
    val searchParamState = viewModel.searchParamState

    val categoryOptionsOpened =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        viewModel.updateTopHeadlines()
        onDispose { }
    }

    LaunchedEffect(newsCategoryState.value, ) {
        viewModel.updateTopHeadlines()
    }

    LaunchedEffect(searchParamState.value, ) {
        viewModel.updateTopHeadlines()
    }

    ModalBottomSheetLayout(
        sheetState = categoryOptionsOpened,
        sheetContent = {
            FiltersBottomSheet(
                filterApplyHandler = {
                    newsCategoryState.value = it
                }
            )
        }
    ) {
        MainPage(
            topHeadlinesState = topHeadlinesState,
            scope = scope,
            sheetState = categoryOptionsOpened,
            navController = navController,
            searchState = searchParamState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainPage(
    topHeadlinesState: State<Resource<NewsResponse>?>,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    navController: NavController,
    searchState: MutableState<String>
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color(0xFF0645AA))
        ) {
            Column() {
                Text(
                    text = "News ",
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                    modifier = Modifier.padding(15.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    SearchBar(
                        onTextChange = {
                            scope.launch {
                                delay(1500)
                                searchState.value = it
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (!sheetState.isVisible) {
                                    sheetState.show()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(55.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(30)
                            )
                    ) {
                        Icon(imageVector = Icons.Default.List, contentDescription = "Filter")
                    }
                }
            }
        }
        ArticleList(
            articlesResult = topHeadlinesState.value,
            articleOnClickHandler = {
                val articleJson = Uri.encode(GsonSerializer.toJson(it))
                navController.navigate(Screen.DetailedArticleScreen.route.replace("{article}", articleJson ))
            }
        )
    }
}

@Composable
fun FiltersBottomSheet(
    filterApplyHandler: (NewsCategory) -> Unit
) {
    val selectedOptionState = remember {
        mutableStateOf(NewsCategory.General)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select news category",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        NewsCategory.values().forEach { filter ->
            FilterOption(
                category = filter,
                selectOption = {
                    selectedOptionState.value = it
                }
            )
        }
        Button(
            onClick = {
                filterApplyHandler(selectedOptionState.value)

            },
            modifier = Modifier
                .padding(3.dp)
                .align(alignment = Alignment.End),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) { Text(text = "Apply", color = Color.White) }
    }
}

@Composable
fun FilterOption(
    category: NewsCategory,
    selectOption: (NewsCategory) -> Unit
) {
    val selectedState = remember {
        mutableStateOf(false)
    }

    Button(
        onClick = {
            if (selectedState.value) {
                selectedState.value = false
            } else {
                selectOption(category)
                selectedState.value = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selectedState.value) Color.Blue else Color.White
        )
    ) {
        Text(
            color = if (selectedState.value) Color.White else Color.Blue,
            text = category.name
        )
    }
}