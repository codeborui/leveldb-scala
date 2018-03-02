package mengbo.borui.leveldb.util

import java.io.{IOException, InputStream, OutputStream}
import java.nio.ByteBuffer
import java.nio.channels.{ClosedChannelException, FileChannel, GatheringByteChannel, ScatteringByteChannel}

import com.google.common.base.Preconditions

import scala.util.control.Breaks

/**
  * @author mengbo
  * @version 1.0
  */
class Slice(val data: Array[Byte], val offset: Int, val length: Int) extends Comparable[Slice] {

  def this(data: Array[Byte]) = {
    this(data, 0, data.length)
  }

  def this(length: Int) = {
    this(Array.emptyByteArray, 0, length)
  }

  def getRawArray: Array[Byte] = {
    data
  }

  def getRawOffset: Int = {
    offset
  }

  def getLength: Int = {
    length
  }

  def getByte(index: Int): Byte = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_BYTE, length)
    data(offset + index)
  }

  def getUnsignedByte(index: Int): Short = {
    (getByte(index) & 0XFF).toShort
  }

  def getShort(index: Int): Short = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_SHORT, length)
    val realIndex: Int = offset + index
    ((data(realIndex) & 0XFF) | (data(realIndex + 1) << 8)).toShort
  }

  def getInt(index: Int): Int = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_INT, length)
    val realIndex: Int = offset + index
    // Byte进行左移时,会将Byte转变成Int类型,然后再操作
    (data(realIndex) & 0XFF) |
      ((data(realIndex + 1) & 0XFF) << 8) |
      ((data(realIndex + 2) & 0XFF) << 16) |
      ((data(realIndex + 3) & 0XFF) << 24)
  }

  def getLong(index: Int): Long = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_LONG, length)
    val realIndex: Int = offset + index
    (data(realIndex) & 0XFF).toLong |
      (data(realIndex + 1) & 0XFF).toLong << 8 |
      (data(realIndex + 2) & 0XFF).toLong << 16 |
      (data(realIndex + 3) & 0XFF).toLong << 24 |
      (data(realIndex + 4) & 0XFF).toLong << 32 |
      (data(realIndex + 5) & 0XFF).toLong << 40 |
      (data(realIndex + 6) & 0XFF).toLong << 48 |
      (data(realIndex + 7) & 0XFF).toLong << 56
  }

  def getBytes(index: Int, length: Int): Array[Byte] = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val realIndex: Int = offset + index
    val value: Array[Byte] = new Array[Byte](length)
    Array.copy(data, realIndex, value, 0, length)
    value
  }

  def getBytes: Array[Byte] = {
    getBytes(0, this.length)
  }

  def fillBytes(index: Int, dest: Array[Byte], destIndex: Int, length: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    Preconditions.checkPositionIndexes(destIndex, destIndex + length, dest.length)
    val realIndex: Int = offset + index
    Array.copy(data, realIndex, dest, destIndex, length)
  }

  def fillBytes(index: Int, dest: Slice, destIndex: Int, length: Int): Unit = {
    fillBytes(index, dest.data, destIndex, length)
  }

  def fillBytes(index: Int, dest: OutputStream, length: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val realIndex: Int = offset + index
    dest.write(data, realIndex, length)
  }

  def fillBytes(index: Int, dest: GatheringByteChannel, length: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val realIndex: Int = offset + index
    dest.write(ByteBuffer.wrap(data, realIndex, length))
  }

  def fillBytes(index: Int, dest: ByteBuffer): Int = {
    Preconditions.checkPositionIndex(index, this.length)
    val realIndex: Int = offset + index
    val ops: Int = math.min(this.length, dest.remaining())
    dest.put(data, realIndex, ops)
    ops
  }

  def setByte(index: Int, value: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_SHORT, this.length)
    val realIndex: Int = offset + index
    data(realIndex) = value.toByte
  }

  def setShort(index: Int, value: Short): Unit = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_SHORT, this.length)
    val realIndex: Int = offset + index
    data(realIndex) = value.toByte
    data(realIndex + 1) = (value >>> 8).toByte
  }

  def setInt(index: Int, value: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_INT, this.length)
    val realIndex: Int = offset + index
    data(realIndex) = value.toByte
    data(realIndex + 1) = (value >>> 8).toByte
    data(realIndex + 2) = (value >>> 16).toByte
    data(realIndex + 3) = (value >>> 24).toByte
  }

  def setLong(index: Int, value: Long): Unit = {
    Preconditions.checkPositionIndexes(index, index + SizeOf.SIZE_OF_LONG, this.length)
    val realIndex: Int = offset + index
    data(realIndex) = value.toByte
    data(realIndex + 1) = (value >>> 8).toByte
    data(realIndex + 2) = (value >>> 16).toByte
    data(realIndex + 3) = (value >>> 24).toByte
    data(realIndex + 4) = (value >>> 32).toByte
    data(realIndex + 5) = (value >>> 40).toByte
    data(realIndex + 6) = (value >>> 48).toByte
    data(realIndex + 7) = (value >>> 56).toByte
  }

  def setBytes(index: Int, src: Array[Byte], srcIndex: Int, length: Int): Unit = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    Preconditions.checkPositionIndexes(srcIndex, srcIndex + length, src.length)
    Array.copy(src, srcIndex, data, offset + index, length)
  }

  def setBytes(index: Int, slice: Slice, sliceIndex: Int, length: Int): Unit = {
    setBytes(index, slice.data, sliceIndex, length)
  }

  def setBytes(index: Int, byteBuffer: ByteBuffer): Unit = {
    Preconditions.checkPositionIndexes(index, index + byteBuffer.remaining(), this.length)
    byteBuffer.get(data, offset + index, byteBuffer.remaining())
  }

  @throws(classOf[IOException])
  def setBytes(index: Int, in: InputStream, length: Int): Int = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    var realIndex: Int = offset + index
    var readBytes: Int = 0
    var leftLen: Int = length
    val loop = new Breaks
    loop.breakable(
      do {
        val localReadBytes: Int = in.read(data, realIndex, leftLen)
        if (localReadBytes < 0) {
          if (readBytes == 0) {
            readBytes = -1
          }
          loop.break
        }
        readBytes += localReadBytes
        realIndex += localReadBytes
        leftLen -= localReadBytes
      } while (leftLen > 0)
    )
    readBytes
  }

  @throws(classOf[IOException])
  def setBytes(index: Int, in: ScatteringByteChannel, length: Int): Int = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val realIndex: Int = offset + index
    val buf: ByteBuffer = ByteBuffer.wrap(data, realIndex, length)
    var readBytes: Int = 0
    val loop = new Breaks
    loop.breakable(
      do {
        val localReadBytes: Int = try {
          in.read(buf)
        } catch {
          case _: ClosedChannelException => -1
        }
        if (localReadBytes < 0) {
          if (readBytes == 0) {
            readBytes = -1
          }
          loop.break
        } else if (localReadBytes == 0) {
          loop.break
        }
        readBytes += localReadBytes
      } while (readBytes < length)
    )
    readBytes
  }

  def setBytes(index: Int, in: FileChannel, postion: Int, length: Int): Int = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val buffer: ByteBuffer = ByteBuffer.wrap(data, offset + index, length)
    val loop: Breaks = new Breaks
    var readBytes: Int = 0
    loop.breakable(
      do {
        val localReadBytes: Int = try {
          in.read(buffer, postion + readBytes)
        } catch {
          case _: ClosedChannelException => -1
        }
        if (localReadBytes < 0) {
          if (readBytes == 0) {
            readBytes = -1
          }
          loop.break
        } else if (localReadBytes == 0) {
          loop.break
        }
        readBytes += localReadBytes
      } while (readBytes < length)
    )
    readBytes
  }

  def copySlice(): Slice = {
    copySlice(0, length)
  }

  def copySlice(index: Int, length: Int): Slice = {
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    val realIndex = offset + index
    val data: Array[Byte] = new Array[Byte](length)
    Array.copy(this.data, realIndex, data, 0, length)
    new Slice(data)
  }

  def slice(): Slice = {

  }

  def slice(index: Int, length: Int): Slice = {
    if (index == 0 && length == this.length) {
      this
    }
    if (index >= 0 && length == 0) {
      Slices.EMPTY_SLICE
    }
    Preconditions.checkPositionIndexes(index, index + length, this.length)
    new Slice(data, offset + index, length)
  }

  override def compareTo(o: Slice): Int = {
    1
  }
}

object SliceMain {
  def main(args: Array[String]): Unit = {
    val byte: Byte = -24
    println(byte)
    println(byte.toLong << 32)
    println((byte & 0XFF).toLong << 32)
  }
}
