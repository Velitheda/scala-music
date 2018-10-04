package main.scala.components

import java.awt.event.{ActionEvent, ActionListener}
import java.io.File
import javax.sound.midi._
import javax.swing.{JButton, JFrame, JPanel}

//import Transformers


object Main extends App {
  override def main(args: Array[String]): Unit = {

    val synth = MidiSystem.getSynthesizer
    val instr = synth.getDefaultSoundbank.getInstruments
    synth.loadInstrument(instr(80))
    synth.open()
    val mc: Array[MidiChannel] = synth.getChannels
    //mc(0).noteOn(60, 100)

    val sequence: Sequence = createSequence(2)

    play(sequence)

    save(sequence)
  }

  def noteButton = {
    val synth = MidiSystem.getSynthesizer
    synth.open()
    val mc: Array[MidiChannel] = synth.getChannels
    val instr = synth.getDefaultSoundbank.getInstruments
    synth.loadInstrument(instr(90))

    val frame = new JFrame("Sound")
    val pane = new JPanel()
    val button1 = new JButton("Play!")
    frame.getContentPane.add(pane)
    pane.add(button1)
    frame.pack()
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setVisible(true)
    button1.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent):Unit = {
        mc(0).noteOn(Generators.randomNote(60, 4), 600)
      }})
  }


  def createSequence(numBars: Int): Sequence = {
    val melody1 = Generators.melody(numBars, 0, 6)
    val melody2 = Generators.melody(numBars, 0, 6)
    val melody3 = Generators.melody(numBars, 0, 6)

    val sequence = new Sequence(javax.sound.midi.Sequence.PPQ, 1)
    //    val m = new MetaMessage()
    //    track.add(new MidiEvent(ShortMessage.PROGRAM_CHANGE, 10))

    val track:Track = sequence.createTrack()

    val melody = Melody.combine(List(melody1, melody2, melody1, melody2, melody2, melody3, melody2, melody1))
    println("Pitches " + melody.pitches)
    println("Rhythms " + melody.rhythm)
    println("Ticks " + melody.ticks)
    println("Stop: " + melody.stop)
    melody.toEvents.foreach(track.add)

    track.add(new MidiEvent(new ShortMessage(ShortMessage.STOP), melody.stop))
    sequence
  }

  def noteEvent(note: Int, tick: Int)(message: Int = ShortMessage.NOTE_ON, velocity:Int = 100):MidiEvent = {
    //message type, note pitch, attack velocity
    val shortMessage = new ShortMessage(message, note, velocity)
    //message, starting time in track
    new MidiEvent(shortMessage, tick)
  }

  def noteOn(note: Int, tick: Int): MidiEvent ={
    noteEvent(note, tick)(ShortMessage.NOTE_ON)
  }

  def noteOff(note: Int, tick: Int): MidiEvent ={
    noteEvent(note, tick)(ShortMessage.NOTE_OFF)
  }
//
//  def setInstrument(instrument: Int): MidiEvent = {
//    new ShortMessage(ShortMessage.PROGRAM_CHANGE)
//  }

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
    val file = new File("midifile.mid")
    MidiSystem.write(sequence, 0, file)
  }

}
