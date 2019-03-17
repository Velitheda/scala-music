package components

import java.io.File

import components.Harmony.Bassline
import javax.sound.midi._

object Main extends App {
  override def main(args: Array[String]): Unit = {
    val sequence: Sequence = createSequence(2)
    play(sequence)
    save(sequence)
  }

  def createSequence(numBars: Int): Sequence = {
    //TODO: Generate structure of melody in code, rather than manually here

    val melody1 = Melody(numBars, 0, 6)
    val melody2 = Melody(numBars, 0, 6)
    val melody3 = Melody(numBars, 0, 6)

    val sequence = new Sequence(javax.sound.midi.Sequence.PPQ, 1)
    val track: Track = sequence.createTrack()

    val harmonyTrack: Track = sequence.createTrack()


    val melody = Melody.combine(List(melody1/*, melody2, melody1, melody2, melody2, melody3, melody2, melody1*/))

//    println("Pitches " + melody.pitches)
//    println("Rhythms " + melody.rhythm)
//    println("Ticks " + melody.ticks)

    //melody.toEvents.foreach(track.add)

    val generator = Generators.getGenerator()
    //                                   I, IV, VI, I, V
    val bassline = Bassline.getBass(List(0, 3, 5, 0, 4), 40, generator)
    println("Pitches " + bassline.pitches)
    println("Rhythms " + bassline.rhythm)
    println("Ticks " + bassline.ticks)

    bassline.toEvents.foreach(harmonyTrack.add)

    track.add(new MidiEvent(new ShortMessage(ShortMessage.STOP), melody.stop))
    harmonyTrack.add(new MidiEvent(new ShortMessage(ShortMessage.STOP), melody.stop))
    sequence
  }

  def play(sequence: Sequence): Unit = {
    try {
      val sequencer = MidiSystem.getSequencer()

      sequencer.open()
      sequencer.setSequence(sequence)
      sequencer.setTempoInBPM(350)
      sequencer.start()

      do {
        try {
          Thread.sleep(1000)
        }
        catch {
          case ie: InterruptedException =>
            ie.printStackTrace()
          case e: Exception =>
            e.printStackTrace()
        }
      } while (sequencer.isRunning)
      sequencer.stop()
      sequencer.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def save(sequence: Sequence): Unit = {
    //TODO: make sure this actually works
    //val file = new File("midifile.mid")
    //MidiSystem.write(sequence, 0, file)
  }

}
