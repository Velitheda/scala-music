package components

import javax.sound.midi.MidiEvent


case class Melody(notes: List[Note], startingTick: Int) {

  def pitches: List[Int] = notes.map(_.pitch)
  def rhythm: List[Int] = notes.map(_.length)

  def ticks: List[Int] = rhythm.scanLeft(startingTick)(_ + _)
  def stop: Int = rhythm.sum + 4

  def toEvents:List[MidiEvent] = {
    notes.flatMap(Note.events)
  }
}

object Melody {

  def apply(numBars: Int, startingTick: Int, range:Int): Melody = {
    val rhythms = BarRhythm.rhythm(numBars)
    val rhythmWithoutRests = rhythms.map(_._1)
    println(rhythms)
    val numNotes = rhythmWithoutRests.size
    val pitches = Generators.pitches(numNotes)
    //take the cumulative sum of rhythms so we know where to start each note
    val timeSpots = rhythmWithoutRests.scanLeft(startingTick)(_ + _)
    val ticks = timeSpots.dropRight(0)

    println(s"Pitches: $pitches")
    println(s"Rhythm: $rhythms")
    println(s"Note start times: $ticks")

    //take the pitches, rhythms, and ticks lists, iterate over them together with the same index
    val notes:List[Note] = (pitches, rhythms, ticks).zipped.toList.map{ case(pitch, length, tick) =>
      Note(pitch, length._1, tick, length._2)
    }
    Melody(notes, startingTick)
  }

  def combine(melodies: List[Melody]): Melody = {
    Melody(melodies.foldLeft(List[Note]()){ (acc, melody) =>
      acc ::: Transformers.shiftInTime(melody, acc.lastOption.map(n => n.tick + n.length).getOrElse(0)).notes},
      melodies.head.startingTick
    )
  }
}
