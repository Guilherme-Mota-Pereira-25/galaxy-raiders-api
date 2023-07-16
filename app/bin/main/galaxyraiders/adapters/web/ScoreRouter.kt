package galaxyraiders.adapters.web

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.http.HttpCode
import galaxyraiders.core.game.Score
import java.nio.file.Paths
import java.io.File
import com.google.gson.Gson

class ScoreRouter : Router {
  var leaderboard: List<Score> = emptyList()
    private set

  override val path = "/score"

  override val endpoints = EndpointGroup {
    get("/",::getScore)
  }

  private fun getScore(ctx: Context) {
    var file = File(Paths.get("").toAbsolutePath().toString() + "/src/main/kotlin/galaxyraiders/core/score/Leaderboard.json")
    file.createNewFile()
    val jsonContent = file.readText()
    val gson = Gson()
    leaderboard = gson.fromJson(jsonContent, Array<Score>::class.java)?.toList() ?: emptyList()
    ctx.json(leaderboard ?: "{}")
  }


}
