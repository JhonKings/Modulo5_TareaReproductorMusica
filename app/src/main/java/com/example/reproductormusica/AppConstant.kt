package com.example.reproductormusica

data class Song (
    val title: String,
    val audioResId: Int,
    val imageResId: Int
){}

class AppConstant{
    companion object{

        const val LOG_MAIN_ACTIVITY = "MainActivityReproductor"
        const val MEDIA_PLAYER_POSITION = "mpPosition"
        const val CURRENT_SONG_INDEX = ""

        val songs = listOf(
            Song("Pretty Please - Dua Lippa", R.raw.pp_remix, R.drawable.pretty_please),
            Song("Summertirm Sadness - Lana del Rey", R.raw.lr_ss, R.drawable.lr_ss),
            Song("In the End - Linkin Park", R.raw.lp_in_the_end_remix, R.drawable.lp_in_the_emd_remix)
        )
    }
}