package com.patlejch.timetables.model.global

import com.patlejch.timetables.R
import com.patlejch.timetables.model.entity.ui.TableParams
import com.skoumal.teanity.extensions.toPx
import kotlin.random.Random

object Static {

    // todo consider generating values based on font size settings
    val tableParams: TableParams by lazy {
        TableParams(48.toPx(), 80.toPx(), 9, 9)
    }

    private val itemColors = listOf(
        R.color.purpleBg,
        R.color.blueLightBg,
        R.color.blueBg,
        R.color.cyanBg,
        R.color.indigoBg,
        R.color.purpleDeepBg,
        R.color.tealBg,
        R.color.greenBg,
        R.color.greenLightBg,
        R.color.limeBg,
        R.color.yellowBg,
        R.color.amberBg,
        R.color.orangeBg,
        R.color.orangeDeepBg,
        R.color.redBg,
        R.color.pinkBg
    )

    fun randomItemColor(textSeed: String) = itemColors[Random(textSeed.hashCode())
        .nextInt(0, itemColors.count())]
}
