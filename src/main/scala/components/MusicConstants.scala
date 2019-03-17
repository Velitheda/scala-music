package components

/**
  * This is an object with all the static music constants shared across all the files
  */
object MusicConstants {

  //1's are semitones included in the scale, -1's are not
  val majorScaleMask = List(1,-1,1,-1,1,1,-1,1,-1,1,-1,1)
  val minorScaleMask = List(1,-1,1,1,-1,1,-1,1,1,-1,1,-1)//aoilean

  //val lowestMidiNote
  //val highestMidiNote

  //val middle C


  def scaleNotes(scale: List[Int]): List[Int] = {
    val scaleNotes = List.range(0, 12).map(value => value * scale(value % scale.size))
    scaleNotes.filter(note => note >= 0)
  }
}
