package mengbo.borui.leveldb.util

import java.io.{IOException, InputStream}
import java.nio.ByteBuffer
import java.nio.channels.{FileChannel, ScatteringByteChannel}
import java.nio.charset.Charset

/**
  * Created by borui on 2018/3/3.
  */
class BasicSliceOutput(slice: Slice) extends SliceOutput {
  private var size: Int = 0

  override def reset(): Unit = {
    size = 0
  }

  override def getSize(): Int = size

  override def writableBytes: Int = {
    slice.getLength - size
  }

  override def isWritable: Boolean = {
    writableBytes > 0
  }

  override def writeByte(v: Int): Unit = {
    slice.setByte(size, v)
    size += 1
  }

  override def writeShort(v: Int): Unit = {
    slice.setShort(size, v)
    size += 2
  }

  override def writeInt(v: Int): Unit = {
    slice.setInt(size, v)
    size += 4
  }

  override def writeLong(v: Long): Unit = {
    slice.setLong(size, v)
    size += 8
  }

  override def writeBytes(slice: Slice): Unit = {
    writeBytes(slice, 0, slice.getLength)
  }

  override def writeBytes(slice: Slice, sourceIndex: Int, length: Int): Unit = {
    slice.setBytes(size, slice, sourceIndex, length)
    size += length
  }

  override def writeBytes(input: SliceInput, length: Int): Unit = {
    if (length > input.available()) {
      throw new IndexOutOfBoundsException
    }
  }

  override def writeBytes(source: Array[Byte]): Unit = {
    writeBytes(source, 0, source.length)
  }

  override def writeBytes(b: Array[Byte], off: Int, len: Int): Unit = {
    slice.setBytes(size, b, off, len)
    size += len
  }

  override def writeBytes(byteBuffer: ByteBuffer): Unit = ???

  @throws(classOf[IOException])
  override def writeBytes(in: InputStream, length: Int): Unit = ???

  @throws(classOf[IOException])
  override def writeBytes(in: ScatteringByteChannel, length: Int): Unit = ???

  @throws(classOf[IOException])
  override def writeBytes(in: FileChannel, position: Int, length: Int): Unit = ???

  override def writeZero(length: Int): Unit = ???

  override def getSlice(): Slice = slice

  override def toString(charset: Charset): String = ???
}
