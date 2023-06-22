package galaxyraiders.core.game

import com.google.gson.Gson
import java.io.File

const val MIN_SCORE = 3

data class Score (
  var score: Double,
  var asteroidsDestroyed: Int,
  var date: String,
  var path: String
) {

  private var firstTime: Boolean = true
  private var leadSize: Int = 0
  // var asteroidsDestroyed: Int = asteroidsDestroyed

  fun addToScoreboard() {
    val file = File(this.path+"/Scoreboard.json")
    file.createNewFile()
    val jsonContent = file.readText()
    val gson = Gson()
    var existingData: MutableList<Score> = gson.fromJson(jsonContent, Array<Score>::class.java)?.toMutableList() ?: mutableListOf<Score>()
    
    var updatedData = existingData
    if (firstTime) {
      firstTime = false
    }
    else if (updatedData.size > 0) {
      updatedData.removeLast()
    }
    updatedData.add(this)
    val updatedJsonContent = gson.toJson(updatedData)
    file.writeText(updatedJsonContent)
  }

  fun addToLeaderboard() {
    val file: File = File(this.path+"/Leaderboard.json")
    file.createNewFile()
    val jsonContent = file.readText()
    val gson = Gson()
    val existingData: List<Score> = gson.fromJson(jsonContent, Array<Score>::class.java)?.toList() ?: emptyList()
  
    var resultData: List<Score> = emptyList()
    var updatedData = existingData + this
    if (updatedData.size > MIN_SCORE) {
      var lowerScore: Double = Double.POSITIVE_INFINITY
      var lowerObj: Score? = null
      for (score in updatedData) { 
        if (score.score < lowerScore) {
          lowerScore = score.score
          lowerObj = score
        }
      }
      for (score in updatedData) {
        if (score != lowerObj) {
          resultData = resultData+score
        }
      }
    }
    else {
      resultData = updatedData
    }
    resultData = resultData.sortedByDescending { it.score }

    val updatedJsonContent = gson.toJson(resultData)
    file.writeText(updatedJsonContent)
  }
}
