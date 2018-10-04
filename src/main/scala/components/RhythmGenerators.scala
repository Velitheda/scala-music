package components

import components.Generators.randomNumber

/**
  * Created by jasminevickery on 24/06/18.
  */
trait RhythmGenerator {
  //semibreve, dotted minim, minim, crotchet, dotted crotchet, quaver, semiquaver
  val noteLengths = List(1, 2, 4, 6, 8, 12, 16)

  def rhythm(numberOfBars: Int): List[(Int, Boolean)]

  def randomLength = {
    noteLengths(randomNumber(0, noteLengths.size - 1))
  }

  def randomLength(lengths: List[Int]) = {
    lengths(randomNumber(0, lengths.size - 1))
  }
}


object BasicRhythm extends RhythmGenerator {
  //wrong, should take number of notes
  def rhythm(numberOfBars: Int): List[(Int, Boolean)] = {
    List.fill(numberOfBars)((randomLength, false))
  }
}

object BarRhythm extends RhythmGenerator {
  //time signatures all have '4' on the bottom, currently
  def rhythm(numberOfBars: Int): List[(Int, Boolean)] = {
    //beat
    //smallest note

    val semiBreve = 16
    val splitUpNoteProbability = 99
    List.fill(numberOfBars)(randomLength)
    //create rests in here?

    //recursion. Create rhythm within bars, obeying beats, then choose beats, split them up and make them obey rules?
    //what about notes that are longer than a beat?

    //fill all the bars with notes that take up all the bars
    val initialLengths = List.fill(numberOfBars)(semiBreve)

    val rhythm = splitUpRhythmByTree(initialLengths, splitUpNoteProbability)
    val restProbability = 10
    chooseRests(rhythm, restProbability)
    //add rests
    //add ties
  }

  //fill the whole phrase with semibreves.  Then loop through it recursively, deciding whether each note should be split into a dotted
  //note, in half, or not at all.

  //make it also likely to split it up into 4 if it can be when choosing to put a dot on or split it in half.
  private def splitUpRhythmByTree(notes: List[Int], splitUpProb: Int): List[Int] = {
    notes.flatMap { note =>
      if(randomNumber(1, 100) < (splitUpProb - splitUpProb / 4) && note >= 2) {
        if(note % 3 == 0) {
          splitUpRhythmByTree(List(note - note / 3, note / 3), splitUpProb - 40)
        } else {
          splitUpRhythmByTree(List(note / 2, note / 2), splitUpProb - 15)
        }
      } else if(randomNumber(1, 100) < splitUpProb && note >= 4 && note % 3 != 0) {
        splitUpRhythmByTree(List(note / 2 + note / 4, note / 4), splitUpProb - 20)
      } else {
        List(note)
      }
    }
  }

  private def splitUpRhythm(notes: List[Int], splitUpProbability: Int): List[Int] = {
    notes.flatMap { note =>
      if(randomNumber(1, 100) < splitUpProbability && note > 2) {
        splitUp(List(), note, splitUpProbability - 40)
      } else {
        List(note)
      }
    }
  }

  private def splitUp(notes: List[Int], length: Int, splitUpProbability: Int): List[Int] = {
    val noteLengthIndex = noteLengths.indexOf(length)
    val startOfList = if(noteLengthIndex - 2 < 0) {
      0
    } else {
      noteLengthIndex - 2
    }
    val beatNotes = noteLengths.take(noteLengthIndex)
    val randomNote = randomLength(beatNotes)
    if(notes.sum == length) {
      splitUpRhythm(notes, splitUpProbability)
    } else if(notes.sum + randomNote <= length) {
      val set: List[Int] = randomNote :: notes
      splitUp(set, length, splitUpProbability)
    } else {
      splitUp(notes, length, splitUpProbability)
    }
  }

  private def chooseRests(notes: List[Int], restProbability: Int): List[(Int, Boolean)] = {
    notes.map{note =>
      if(randomNumber(1, 100) < restProbability) {
        (note, true)
      } else {
        (note, false)
      }
    }
  }
}
