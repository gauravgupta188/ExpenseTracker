package com.app.expensetracker.feature.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.expensetracker.R


@Composable
fun AuthLogo(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable. ic_splash_logo),
        contentDescription = stringResource(id = R.string.logo_content_description),
        modifier = modifier.padding(vertical =16.dp)
    )
}