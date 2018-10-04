package components

import javax.sound.midi.{MidiEvent, ShortMessage}

case class Note(pitch: Int, length: Int, tick: Int, isRest: Boolean = false)

object Note {
  def events(note: Note): List[MidiEvent] = {
    if(note.isRest) {
      Nil
    } else {
      val noteOnEvent = noteOn(note.pitch, note.tick)
      val noteOffEvent = noteOff(note.pitch, note.tick + note.length)
      List(noteOnEvent, noteOffEvent)
    }
  }

  def noteEvent(note: Int, tick: Int)(message: Int = ShortMessage.NOTE_ON, velocity:Int = 100): MidiEvent = {
    //message type, note pitch, attack velocity
    val shortMessage = new ShortMessage(message, note, velocity)
    //message, starting time in track
    new MidiEvent(shortMessage, tick)
  }

  private def noteOn(note: Int, tick: Int): MidiEvent = {
    noteEvent(note, tick)(ShortMessage.NOTE_ON)
  }

  private def noteOff(note: Int, tick: Int): MidiEvent = {
    noteEvent(note, tick)(ShortMessage.NOTE_OFF)
  }

  def transpose(note: Note, interval: Int): Note = {
    note.copy(pitch = note.pitch + interval)
  }
}

// Make pitch an option to represent isRest? Or use this?
case class Rest(length: Int, tick: Int)

