package mengbo.borui.leveldb.util

import java.io.{DataInput, InputStream}
import java.nio.charset.Charset

/**
  * Created by borui on 2018/3/2.
  */
final class SliceInput(slice: Slice) extends InputStream with DataInput {

  private var position: Int = 0

  def getPosition: Int = position

  def setPosition(position: Int): Unit = {
    if (position < 0 || position > slice.getLength) {
      throw new IndexOutOfBoundsException
    }
    this.position = position
  }

  def isReadable: Boolean = {
    available() > 0
  }

  override def available(): Int = {
    slice.getLength - position
  }

  override def read(): Int = readByte()

  override def readBoolean(): Boolean = readByte() != 0

  override def readByte(): Byte = {
    val value: Byte = slice.getByte(position)
    position += 1
    value
  }

  override def readUnsignedByte(): Int = {
    val value: Int = slice.getUnsignedByte(position)
    position += 1
    value
  }

  override def readShort(): Short = {
    val value: Short = slice.getShort(position)
    position += 2
    value
  }

  override def readUnsignedShort(): Int = {
    readShort() & 0xFF
  }

  override def readInt(): Int = {
    val value: Int = slice.getInt(position)
    position += 4
    value
  }

  def readUnsignedInt(): Long = {
    readInt() & 0xFFFFFFFFl
  }

  override def readLong(): Long = {
    val value: Long = slice.getLong(position)
    position += 8
    value
  }

  override def skipBytes(n: Int): Int = {
    val skipNum: Int = math.min(n, available())
    position += skipNum
    skipNum
  }

  def readBytes(length: Int): Slice = {
    if (length == 0) {
      Slices.EMPTY_SLICE
    } else {
      val newSlice: Slice = slice.copySlice(position, length)
      position += length
      newSlice
    }
  }

  def readSlice(length: Int): Slice = {
    val newSlice: Slice = slice.copySlice(position, length)
    position += length
    newSlice
  }

  override def readFully(b: Array[Byte]): Unit = {
    readBytes(b)
  }

  override def readFully(b: Array[Byte], off: Int, len: Int): Unit = {
    readBytes(b, off, len)
  }

  def readBytes(b: Array[Byte]): Unit = {
    readBytes(b, 0, b.length)
  }

  def readBytes(b: Array[Byte], offset: Int, length: Int): Unit = {
    slice.setBytes(position, b, offset, length)
    position += length
  }

  def readBytes(slice: Slice, length: Int): Unit = {
    if (length > slice.getLength) {
      IndexOutOfBoundsException
    }
    readBytes(slice, 0, length)
  }

  def readBytes(slice: Slice, sliceIndex: Int, length: Int): Unit = {
    slice.setBytes(position, slice, sliceIndex, length)
    position += length
  }

  def emptySlice(): Slice = {
    slice.slice(position, available())
  }

  def toString(charSet: Charset): String = {
    slice.toString(position, available(), charSet)
  }

  override def toString: String = {
    getClass.getSimpleName + "(ridx" + position + ", cap=" + slice.getLength + ")"
  }

  override def readChar(): Char = throw new UnsupportedOperationException

  override def readUTF(): String = throw new UnsupportedOperationException

  override def readLine(): String = throw new UnsupportedOperationException

  override def readFloat(): Float = throw new UnsupportedOperationException

  override def readDouble(): Double = throw new UnsupportedOperationException
}
