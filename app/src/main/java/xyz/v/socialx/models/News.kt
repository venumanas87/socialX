package xyz.v.socialx.models

data class News(
    val status:String="",
    val totalResults:Int=0,
    val articles:List<Article>
)
