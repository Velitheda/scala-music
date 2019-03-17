package components.Harmony

import components.{Generator, Melody, MusicConstants, Note}

class Bassline(chordOrder: List[Int], generator: Generator)

object Bassline {

  def getBass(chordOrder: List[Int], previousNote: Int, generator: Generator): Melody = {
    val numBars = chordOrder.size
    (0 until numBars).foldLeft(Melody(Nil, 0)) { case (previousMelody, index) =>
      val chord = chordOrder(index)
      //make chord object

      val chordNotes = getChordNotes(chord, true)

      //transpose up or down depending on what is the closest
      val octavesToTranspose: Int = Math.floor(previousNote / 12).toInt
      val transposeAmount = octavesToTranspose * 12

      //bass pattern over chord
      val numNotesInBar = 4
      val bassNotes = List(
        Note(chordNotes(0) + transposeAmount, 8, 0),
        Note(chordNotes(2) + transposeAmount, 8, 8),
        Note(chordNotes(1) + transposeAmount, 8, 16),
        Note(chordNotes(2) + transposeAmount, 8, 24)
      )

      Melody.combine(List(previousMelody, Melody(bassNotes, 0)))
    }
  }

  //c major/minor only
  def getChordNotes(chord: Int, major: Boolean): List[Int] = {
    val scaleMask = if(major) MusicConstants.majorScaleMask else MusicConstants.minorScaleMask
    val scale = MusicConstants.scaleNotes(scaleMask)
    //todo handle 7ths and 4ths etc
    //todo handle inversions when selecting the bass pattern instead of sorting it here
    List(scale(chord % 7), scale((chord + 2) % 7), scale((chord + 4) % 7)).sorted
  }

}
