package mengbo.borui.leveldb.util

import java.nio.{ByteBuffer, CharBuffer}
import java.nio.charset._
import java.util

/**
  * @author mengbo
  * @version 1.0
  */
object Slices {

  private val decoders = new ThreadLocal[util.Map[Charset, CharsetDecoder]] {
    override def initialValue(): util.Map[Charset, CharsetDecoder] = {
      new util.IdentityHashMap[Charset, CharsetDecoder]
    }
  }

  val EMPTY_SLICE: Slice = new Slice(0)

  def decodeString(src: ByteBuffer, charset: Charset): String = {
    val decoder: CharsetDecoder = getDecoder(charset)
    val charBuffer: CharBuffer = CharBuffer.allocate(
      (src.remaining().toDouble * decoder.maxCharsPerByte()).toInt)
    try {
      var cr: CoderResult = decoder.decode(src, charBuffer, true)
      if (!cr.isUnderflow) {
        cr.throwException()
      }
      cr = decoder.flush(charBuffer)
      if (!cr.isUnderflow) {
        cr.throwException()
      }
    } catch {
      case e: CharacterCodingException => throw new IllegalStateException(e)
    }
    charBuffer.flip.toString
  }

  def getDecoder(charset: Charset): CharsetDecoder = {
    if (charset == null) {
      throw new NullPointerException
    }
    val map: util.Map[Charset, CharsetDecoder] = decoders.get()
    var decoder: CharsetDecoder = map.get(charset)
    if (decoder != null) {
      decoder.reset()
      decoder.onMalformedInput(CodingErrorAction.REPLACE)
      decoder.onUnmappableCharacter(CodingErrorAction.REPLACE)
    } else {
      decoder = charset.newDecoder
      decoder.onMalformedInput(CodingErrorAction.REPLACE)
      decoder.onUnmappableCharacter(CodingErrorAction.REPLACE)
      map.put(charset, decoder)
    }
    decoder
  }
}
