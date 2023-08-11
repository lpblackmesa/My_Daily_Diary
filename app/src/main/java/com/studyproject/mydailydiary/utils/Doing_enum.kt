package com.studyproject.mydailydiary.utils

import com.studyproject.mydailydiary.R

enum class Doing (val iconRes : Int, val description : Int) {

    FAMILY(R.drawable.family,R.string.family),
    FRIENDS(R.drawable.friends,R.string.friends),
    DATING(R.drawable.dating,R.string.dating),
    PARTY(R.drawable.party,R.string.party),
    CINEMA(R.drawable.cinema,R.string.cinema),
    READING(R.drawable.reading,R.string.reading),
    GAME(R.drawable.game,R.string.game),
    SPORT(R.drawable.sport,R.string.sport),
    CHILLING(R.drawable.chilling,R.string.chilling),
    SHOPPING(R.drawable.shopping,R.string.shopping),
    CLEANING(R.drawable.cleaning,R.string.cleaning),
    STUDY(R.drawable.studying,R.string.study)

}