package mengbo.borui.leveldb.util

import java.io.{DataOutput, IOException, InputStream, OutputStream}
import java.nio.ByteBuffer
import java.nio.channels.{FileChannel, ScatteringByteChannel}
import java.nio.charset.Charset

/**
  * Created by borui on 2018/3/3.
  */
abstract class SliceOutput extends OutputStream with DataOutput{

  def reset() : Unit

  def getSize(): Int

  def writableBytes: Int

  def isWritable: Boolean

  override def writeBoolean(v: Boolean): Unit = {
    writeByte(if(v) 1 else 0)
  }

  override def write(b: Int): Unit = writeByte(b)

  def writeBytes(slice: Slice): Unit

  def writeBytes(input: SliceInput, length: Int): Unit

  def writeBytes(slice: Slice, sourceIndex: Int, length: Int): Unit

  @throws(classOf[IOException])
  override final def write(source: Array[Byte]): Unit = {
    writeBytes(source)
  }

  def writeBytes(source: Array[Byte]): Unit

  override final def write(b: Array[Byte], off: Int, len: Int): Unit = {
    writeBytes(b, off, len)
  }

  def writeBytes(b: Array[Byte], off: Int, len: Int): Unit

  def writeBytes(byteBuffer: ByteBuffer): Unit

  @throws(classOf[IOException])
  def writeBytes(in: InputStream, length: Int): Unit

  @throws(classOf[IOException])
  def writeBytes(in: ScatteringByteChannel, length: Int): Unit

  @throws(classOf[IOException])
  def writeBytes(in: FileChannel, position: Int, length: Int): Unit

  def writeZero(length: Int): Unit

  def getSlice(): Slice

  def toString(charset: Charset): String

  override def writeChar(v: Int): Unit = UnsupportedOperationException

  override def writeChars(s: String): Unit = UnsupportedOperationException

  override def writeUTF(s: String): Unit = UnsupportedOperationException

  override def writeFloat(v: Float): Unit = UnsupportedOperationException

  override def writeDouble(v: Double): Unit = UnsupportedOperationException

  override def writeBytes(s: String): Unit = UnsupportedOperationException
}
