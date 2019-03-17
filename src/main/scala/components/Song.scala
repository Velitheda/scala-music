package components

// Movement? Section? Piece
abstract class Song {

  val tempo: Int

  // stitch things together with different time signatures later, but one per song for now
  val timeSignature: Int

  // When changing time signature or key, create a new one of these and stitch it to something bigger
  val key: Int

  val numBars: Int

  def transposeKey()
  def changeTempo()
  //actual notes, rhythms, and chords, probably in a smaller object
  //list of phrases?
  //one phrase inside?

}


