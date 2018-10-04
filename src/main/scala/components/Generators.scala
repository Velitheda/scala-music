package components

import components.MusicConstants._

object Generators {

  //delete
  def melody(numBars:Int, startingTick: Int, range:Int): Melody = {
    Melody(numBars, startingTick, range)
  }

  def pitches(length: Int): List[Int] = {
    val availableNotes = allNotesInScale(scale(0, majorScaleMask))
    val startingNote = 35

    val starting = List.fill(length)(0)

    val relative = starting.scanRight(startingNote)((previous, note) => previous + randomNote(previous, 3)).reverse.tail.reverse
    println(relative)
    relative.map( note => availableNotes(note + startingNote))
  }

  def scale(key: Int, scale: List[Int]): List[Int] = {
    rotateRight(scale, key)
  }

  def rotateRight[A](list: List[A], i: Int): List[A] = {
    val size = list.size
    list.drop(size - (i % size)) ++ list.take(size - (i % size))
  }

  def allNotesInScale(scale: List[Int]):List[Int] = {
    val scaleNotes = List.range(0, 128).map( value => value * scale(value % scale.size))
    scaleNotes.filter(note => note >= 0)
  }

  def accidentials(length: Int):List[Int] = {
    //return a list of 0's, -1s, and 1s
    Nil
  }

  def dynamics(length: Int):List[Int] = {
    //return a list of numbers correlating to the volume of each note
    Nil
  }

  def randomGaussian(previous: Int, range: Int): Int = {
    val rnd = new scala.util.Random
    val number = (rnd.nextGaussian() * range + previous).toInt % (range + 1)
    number.toInt
  }

  // Remove augmented 4ths.
  def randomNote(prev: Int, interval: Int) = {
    val number = randomGaussian(prev, interval)
    if (number == prev && Math.abs(number - prev) == 6) {
      //try again to cut down repeated notes and augmented 4ths
      randomGaussian(prev, interval)
    } else {
      number
    }
  }

  def randomNumber(lowest: Int, highest: Int): Int = {
    val rnd = new scala.util.Random
    lowest + rnd.nextInt( (highest - lowest) + 1 )
  }

}
