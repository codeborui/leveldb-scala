package mengbo.borui.leveldb.impl

import java.util.Objects

import scala.collection.mutable
import mengbo.borui.leveldb.TWriteBatch
import mengbo.borui.leveldb.util.{Slice, Slices}

/**
  * @author mengbo
  * @version 1.0
  */
class WriteBatch extends TWriteBatch {
  private val batch: mutable.Buffer[(Slice, Slice)] = new mutable.ArrayBuffer[(Slice, Slice)]()
  private var approximateSize: Int = 0

  def getApproximateSize: Int = approximateSize

  def getSize: Int = batch.size

  override def put(key: Array[Byte], value: Array[Byte]): TWriteBatch = {
    Objects.requireNonNull(key, "key is null")
    Objects.requireNonNull(value, "value is null")
    this.batch.append((Slices.wrappedBuffer(key), Slices.wrappedBuffer(value)))
    approximateSize += 12 + key.length + value.length
    this
  }

  def put(key: Slice, value: Slice): WriteBatch = {
    Objects.requireNonNull(key, "key is null")
    Objects.requireNonNull(value, "value is null")
    this.batch.append((key, value))
    approximateSize += 12 + key.getLength + value.getLength
    this
  }

  override def delete(key: Array[Byte]): TWriteBatch = ???

  override def close(): Unit = ???
}
