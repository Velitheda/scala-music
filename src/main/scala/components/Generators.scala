package components

import components.MusicConstants._

object Generators {

  def pitches(length: Int): List[Int] = {
    val availableNotes = allNotesInScale(scale(0, majorScaleMask))
    val startingNote = 35

    val starting = List.fill(length)(0)

    val relative = starting.scanRight(startingNote)((previous, note) => previous + randomNote(previous, 3)).dropRight(1)
    relative.map(note => availableNotes(note + startingNote))
  }

  def scale(key: Int, scale: List[Int]): List[Int] = {
    rotateRight(scale, key)
  }

  def rotateRight[A](list: List[A], i: Int): List[A] = {
    val size = list.size
    list.drop(size - (i % size)) ++ list.take(size - (i % size))
  }

  def allNotesInScale(scale: List[Int]): List[Int] = {
    val scaleNotes = List.range(0, 128).map(value => value * scale(value % scale.size))
    scaleNotes.filter(note => note >= 0)
  }

  def accidentals(length: Int): List[Int] = {
    //TODO: return a list of 0's, -1s, and 1s
    Nil
  }

  def dynamics(length: Int): List[Int] = {
    //TODO: return a list of numbers correlating to the volume of each note
    Nil
  }

  def randomGaussian(previous: Int, range: Int): Int = {
    val rnd = new scala.util.Random
    val number = (rnd.nextGaussian() * range + previous).toInt % (range + 1)
    number.toInt
  }

  def randomNote(prev: Int, interval: Int) = {
    val number = randomGaussian(prev, interval)
    // We try again if it is the same note to cut down repeated notes and augmented 4ths. (An augmented 4th has 6 semitones)
    if (number == prev && Math.abs(number - prev) == 6) {
      randomGaussian(prev, interval)
    } else {
      number
    }
  }

  def randomNumber(lowest: Int, highest: Int, seed: Int = -1): Int = {
    val rnd = if(seed >= 0) {
      new scala.util.Random(seed)
    } else {
      new scala.util.Random
    }
    lowest + rnd.nextInt( (highest - lowest) + 1 )
  }

  def getGenerator(seed: Int = -1): Generator = {
    if(seed >= 0) {
      new Generator(new scala.util.Random(seed))
    } else {
      new Generator(new scala.util.Random)
    }
  }
}

class Generator(random: scala.util.Random) {

  def getRandomInt(lowest: Int, highest: Int): Int = {
    lowest + random.nextInt( (highest - lowest) + 1 )
  }

}