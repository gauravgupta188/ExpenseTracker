package com.app.expensetracker.core.navigation


import androidx.compose.animation.*
import androidx.compose.animation.core.tween

private const val ANIM_DURATION = 300

val slideInFromRight: EnterTransition =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(ANIM_DURATION)
    )

val slideOutToLeft: ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(ANIM_DURATION)
    )

val slideInFromLeft: EnterTransition =
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(ANIM_DURATION)
    )

val slideOutToRight: ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(ANIM_DURATION)
    )

val fadeInFast: EnterTransition =
    fadeIn(animationSpec = tween(ANIM_DURATION))

val fadeOutFast: ExitTransition =
    fadeOut(animationSpec = tween(ANIM_DURATION))
