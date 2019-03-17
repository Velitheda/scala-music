package components

trait SongPattern {


  val possiblePhrases: List[Song]//or phrase

 // val phraseTransformations: List[Transformations]
//  val songTransfomations: List[SongTransformations]

  val numPhrases: Int

  def generateSongPattern(seed: Int): Unit = {
    val pattern = (0 to numPhrases).map { index =>
      //choose a possible phrase
      //choose a transformation (none is always added at the start)
      //whack them together and then append them to the list
    }
  }

}
