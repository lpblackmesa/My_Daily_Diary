package com.studyproject.mydailydiary.data

import com.studyproject.mydailydiary.R

enum class Mood(val iconRes: Int, val description: Int, val color:Int) {
    EXCELLENT(R.drawable.happy, R.string.excellent_mood, R.color.green),
    GOOD(R.drawable.smile, R.string.good_mood, R.color.green),
    LAUGHING(R.drawable.laughing, R.string.laughing_mood, R.color.green),
    LOVE(R.drawable.icon_love, R.string.love_mood, R.color.green),
    NORMAL(R.drawable.neutral, R.string.normal_mood, R.color.yellow),
    DEMON(R.drawable.demon, R.string.demon_mood, R.color.yellow),
    THINKING(R.drawable.thinking, R.string.thinking_mood, R.color.yellow),
    SAD(R.drawable.sad, R.string.sad_mood, R.color.red),
    AWFUL(R.drawable.crying, R.string.awful_mood, R.color.red),
    SICK(R.drawable.sick, R.string.sick_mood, R.color.red),
    ANGRY(R.drawable.angry, R.string.angry_mood, R.color.red)
}