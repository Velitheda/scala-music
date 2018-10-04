package components

object Transformers {

  def transpose(melody: Melody, interval: Int): Melody = {
    Melody(melody.notes.map(note =>
      note.copy(pitch = note.pitch + interval)),
      melody.startingTick
    )
  }

  def shiftWithinScale(melody: Melody, scaleInterval: Int): Melody = {
    Melody(melody.notes.map(note =>
      note.copy(pitch = note.pitch + calculateInterval(note.pitch % 12, scaleInterval, 0))),
      melody.startingTick
    )
  }

  private def calculateInterval(note: Int, scaleInterval: Int, scale: Int): Int = {
    def majorScale: List[(Int, Int)] = List(
      (0, 1),
      (1, -1),
      (2, 1),
      (3, -1),
      (4, 1),
      (5, 1),
      (6, -1),
      (7, 1),
      (8, -1),
      (9, 1),
      (10, -1),
      (11, 1)
    )

    // need the index in the mask of the nth +1 along,
    println(note)
    println(majorScale.drop(note))
    val maskIndex = majorScale.drop(note).foldRight(scaleInterval){(next, keyNoteCountDown) =>
      if(next._2 == 1) {
        if(keyNoteCountDown == 0) {
          next._1
        }
        else {
          keyNoteCountDown - 1
        }
      } else {
        keyNoteCountDown
      }
    }
    println(maskIndex)
    // starting at the current note,
    // wrapping around if the number is bigger than 7.
    maskIndex - note
  }

//  def invert(melody: Melody): Melody = {
//    melody.notes.scanRight(0)(note => )
//
//    // take first note, don't transform
//    // take second note
//    // compare interval
//    // lower pitch
//    // take third note
//    // compare old interval with second note
//  }

  def reverse(melody: Melody, startingTick: Int): Melody = {
    Melody(melody.notes.reverse, startingTick)
  }

  //belong in melody class
  def shiftInTime(melody: Melody, delta: Int): Melody = {
    Melody(melody.notes.map(n => Note(n.pitch, n.length, n.tick + delta, n.isRest)), delta)
  }

  def combineTwo(firstMelody: Melody, secondMelody: Melody, startingTick: Int): Melody = {
    Melody(firstMelody.notes.take(firstMelody.notes.length / 2) :::
      secondMelody.notes.drop(secondMelody.notes.length / 2), startingTick)
  }

}
