package test.scala

import main.scala.components.{BarRhythm, Melody, Note, Transformers}
import org.specs2.mutable._

class MainSpec extends Specification {

  //Need to seed melodies for testing

  "Main" should {
    "play a melody" in {
      ok
    }
  }

  "Notes" should {
    "Create correct midi events" in {
      ok
    }

    "Create the stop" in {
      ok
    }
  }

  "Rhythm generation" should {
    "generate a rhythm" in {
      val rhythm = BarRhythm.rhythm(5)
      ok
    }

    "generate a rhythm in any time signature" in {
      ok
    }

    "add rests" in {
      ok
    }

    "add ties" in {
      ok
    }
  }

  "Pitch generation" should {
    "generate a melody" in {
      ok
    }

    "not create notes out of bound" in {
      ok
    }

    "add ties" in {
      ok
    }
  }

  //Dynamics generation
  //Instrument choice
  //phrasing: slur, legato, staccato

  "Chord generation" should {
    "generate a sequence of chords" in {
      //give a number of bars, key, chord change rate
      //keep them largely on the beat
      ok
    }
    "generate a cadence" in {
      ok
    }
  }

  "Structure generation" should {
    "generate a basic phrase structure" in {
      // give it number of initial phrases
      // length? bar number?
      // it joins them together with lots of repitition
      // chooses which ones to apply a variation
      // chooses number of variations
      ok
    }
  }


  "Transformers" should {
    "transpose a melody to a given key" in {
      ok
    }

    "ShiftWithinScale" in {
      "shift middle C up several scale notes" in {
        val notes = Melody(List(
          Note(0, 1, 0)
        ), 0) // C
        val expected = Melody(List(Note(2, 1, 0)), 0) // E
        val result = Transformers.shiftWithinScale(notes, 3)
        result must beEqualTo(expected)
      }

      "shift a melody up an interval but keep it in the same key" in {
        // start this from 0/1?
        val notes = Melody(List(
          Note(0, 1, 0),
          Note(2, 1, 1),
          Note(4, 1, 2)//,
//          Note(40, 1, 3),
//          Note(42, 1, 4),
//          Note(44, 1, 5),
//          Note(46, 1, 6)//,
          //Note(47, 1, 7)
        ), 0) // C-D-E-F-G-A-B-C
        val expected = Melody(
            List(Note(4, 1, 0),
            Note(5, 1, 1),
            Note(7, 1, 2)), 0) // E-F-G-A-B-C-D-E
        val result = Transformers.shiftWithinScale(notes, 3)
        result must beEqualTo(expected)
      }

      "handle shifting right next to the edge of the range" in {
        ok
      }

      "shift up a minor scale into another minor scale" in {
        ok
      }
    }

    "add ties" in {
      ok
    }
  }

    //bar class?
  //static music concepts library (scales, keys, note lengths, dynamics..)

  //phrase vs melody concept?
  //phrase has no absolute pitch or dynamics or key? Just rhythm and note intervals
  "Melodies" should {
    "stitch more melodies together correctly" in {
      ok
    }

    "combine pitches, lengths rhythms, rests, all together correctly" in {
      ok
    }


  }


}
