package components.Harmony

import components.Generator

class ChordPattern {

}

object ChordPattern {
  def chordOrder(availableChords: Vector[Int], numChords: Int, generator: Generator): List[Int] = {
    (0 to numChords).map { i =>
      val index: Int = generator.getRandomInt(0, availableChords.size - 1)
      availableChords(index)
    }.toList
  }

  def applyRhythm(chordOrder: List[Int], chordRyhthm: List[(Int, Boolean)]): Unit = {

  }
}