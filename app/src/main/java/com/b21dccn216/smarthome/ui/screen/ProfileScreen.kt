package com.b21dccn216.smarthome.ui.screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.b21dccn216.smarthome.R


@Composable
fun ProfileScreen(modifier: Modifier){
    
    Column(
        modifier = modifier.fillMaxSize()
//            .background(color = Color.Gray.copy(alpha = 0.3f))
    ) {
        ImageProfile(modifier = Modifier)
    }
}


@Composable
private fun ImageProfile(
    modifier: Modifier
){
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (backImg, profileImg, whiteBack) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ocean),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(backImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.matchParent
                }
        )
        val horizontalGuiline = createTopBarrier(profileImg, margin = 65.dp)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(15))
                .clip(RoundedCornerShape(15))
//                .background(color = Color.White, shape = RoundedCornerShape(15))
                .constrainAs(whiteBack) {
                    top.linkTo(horizontalGuiline)
                },
            shadowElevation = 5.dp
        ){
            Column(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(text = "Nguyen Tran Dat",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Ullum noster eu vis. Ius malorum feugait conclusionemque ex, mea an vocent tamquam vivendum. Vis amet vocent ne, no vix vidisse facilis. Te eam erant nominavi eloquentiam.",
                    modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 8.dp),
                    textAlign = TextAlign.Center)
            }
        }
        CreateImageProfile(
            modifier = Modifier.constrainAs(profileImg){
                bottom.linkTo(backImg.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}


@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(5.dp, Color.White),
        shadowElevation = 5.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_img),
            contentDescription = "Profile Image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    ProfileScreen(Modifier)
}