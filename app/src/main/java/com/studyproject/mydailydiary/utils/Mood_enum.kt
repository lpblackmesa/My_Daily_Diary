package com.studyproject.mydailydiary.utils

import com.studyproject.mydailydiary.R

enum class Mood(val iconRes : Int, val description : Int) {
    EXCELLENT(R.drawable.happy, R.string.excellent_mood) ,
    GOOD(R.drawable.smile,R.string.good_mood),
    NORMAL(R.drawable.neutral, R.string.normal_mood),
    SAD(R.drawable.sad,R.string.sad_mood),
    AWFUL(R.drawable.crying,R.string.awful_mood),
    SICK(R.drawable.sick, R.string.sick_mood),
    ANGRY(R.drawable.angry, R.string.angry_mood),
    DEMON(R.drawable.demon, R.string.demon_mood),
    LAUGHING(R.drawable.laughing, R.string.laughing_mood),
    LOVE(R.drawable.icon_love, R.string.love_mood),
    THINKING(R.drawable.thinking, R.string.thinking_mood)
}