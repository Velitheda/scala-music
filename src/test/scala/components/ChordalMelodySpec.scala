package components

import components.Harmony.{Bassline, ChordPattern}
import org.specs2.mutable._

class ChordalMelodySpec extends Specification {

  //Chord
  //phrase
  //rhythm list of bars with tied notes + rests

  //Generate should be a trait

  private val seed = 1000
  private val generator = Generators.getGenerator(seed)


  "Generate melody" should {
    val availableChords: Vector[Int] = Vector(1, 4, 5, 6)

    //hide these two lines
    //make a type for the chord pattern
    val chordRhythm: List[(Int, Boolean)] = ChordRhythm.rhythm(4, generator)
    val chords = ChordPattern.chordOrder(availableChords, chordRhythm.size, generator)
//    //
    //val chordPattern = ChordPattern.applyRhythm(chords, chordRhythm)
//
//    // generate rhythm for each bar/ section the chord rhythm is long for
//    val bassLine: Phrase = PhraseGenerator.generateBass(chordPattern, seed)
//    val melody: Phrase = PhraseGenerator.generateMelody(chordPattern, seed)


//    val voices = List(melody, bassLine)

    ok
  }

  "get chord rhythm" should {
    val numBars = 4
    val result: List[(Int, Boolean)] = ChordRhythm.rhythm(numBars, generator)

    val expected = List((1, true), (1, true), (1, true), (1, true))
    result must beEqualTo(expected)
  }

  "Get chord notes" should {
    val generator = Generators.getGenerator(seed)

    val key = 1
    val availableChords: Vector[Int] = Vector(1, 4, 5, 6)
    val numChords = 4

    val chords = ChordPattern.chordOrder(availableChords, numChords, generator)
    val expected = List(5, 1, 5, 4, 6)
    chords must beEqualTo(expected)
  }

  "Generate bass" should {
    val availableChords: Vector[Int] = Vector(1, 4, 5, 6)

    val chordOrder = List(0, 3)
    val bass: Melody = Bassline.getBass(chordOrder, 10, generator)

    val expected = Melody(List(
      Note(0, 8, 0), Note(7, 8, 8), Note(4, 8, 16), Note(7, 8, 24),
      Note(5, 8, 32), Note(0, 8, 40), Note(9, 8, 48), Note(0, 8, 56)
    ), 0)
    bass must beEqualTo(expected)
  }

  "get notes inside chord" should {
    List(0, 2, 4, 5, 7, 9, 11) must beEqualTo(MusicConstants.scaleNotes(MusicConstants.majorScaleMask))
    List(0, 4, 7) must beEqualTo(Bassline.getChordNotes(0, true))
  }
}
