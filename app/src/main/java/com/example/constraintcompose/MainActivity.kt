package com.example.constraintcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

import com.example.constraintcompose.ui.theme.ConstraintComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            val constraintSet = ConstraintSet {
                val greenBox = createRefFor("greenbox")
                val redBox = createRefFor("redbox")
                val guideLine = createGuidelineFromTop(0.5f)


                constrain(greenBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                }
                constrain(redBox) {
                   // top.linkTo(guideLine)
                    top.linkTo(parent.top)
                    start.linkTo(greenBox.end)
                    end.linkTo(parent.end)
                    width = Dimension.value(100.dp)
//                    width = Dimension.fillToConstraints
                    height = Dimension.value(100.dp)
                }

                createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Packed)
            }

           Column(modifier = Modifier.fillMaxSize()) {
               ConstraintLayout(constraintSet, modifier = Modifier
                   .weight(1f)
                   .fillMaxSize()) {
                   Box(modifier = Modifier
                       .background(Color.Green)
                       .layoutId("greenbox"))

                   Box(modifier = Modifier
                       .background(Color.Red)
                       .layoutId("redbox"))
               }

               Scaffold(scaffoldState = scaffoldState, modifier = Modifier
                   .weight(1f)
                   .fillMaxSize()) {

                 /*  var counter by remember {
                       mutableStateOf(0)
                   }*/

                   var counter = produceState(initialValue = 0) {
                       kotlinx.coroutines.delay(3000L)
                       value = 4
                   }

                   if(counter.value % 5 == 0 && counter.value > 0) {

                       LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
                           scaffoldState.snackbarHostState.showSnackbar("Hello Bakyt")
                       }

                     /*  scope.launch {
                           scaffoldState.snackbarHostState.showSnackbar("Hello Bakyt")
                       }*/
                   }
                   Button(onClick = { }) {
                       Text(text = "Click me: ${counter.value}")
                   }
                   
               }

           }



          /*  ConstraintComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }*/
        }
    }




    @Composable
    fun MyComposable(backPressedDispather: OnBackPressedDispatcher) {

        val callBack = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
        }

        
        DisposableEffect(key1 = backPressedDispather) {

            backPressedDispather.addCallback(callBack)
            onDispose {
                callBack.remove()
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Click me")
            
        }
    }

}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConstraintComposeTheme {
        Greeting("Android")
    }
}*/

//SideEffect { }