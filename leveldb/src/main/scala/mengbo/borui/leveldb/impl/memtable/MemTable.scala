package mengbo.borui.leveldb.impl.memtable

import java.util.Objects
import java.util.concurrent.ConcurrentSkipListMap
import java.util.concurrent.atomic.AtomicLong

import mengbo.borui.leveldb.SeekingIterable
import mengbo.borui.leveldb.impl.ValueType.ValueType
import mengbo.borui.leveldb.impl.{InternalKey, InternalKeyComparator}
import mengbo.borui.leveldb.util.Slice

/**
  * @author mengbo
  * @version 1.0
  */
object MemTable extends SeekingIterable[(InternalKey, Slice)]{
  private var table: ConcurrentSkipListMap[InternalKey, Slice] = _
  private val memoryUsage: AtomicLong = new AtomicLong()
}

class MemTable(internalKeyComparator: InternalKeyComparator)
  extends SeekingIterable[(InternalKey, Slice)] {
  MemTable.table = new ConcurrentSkipListMap[InternalKey, Slice]()

  override def isEmpty: Boolean = MemTable.table.isEmpty

  def getMemoryUsage: Long = MemTable.memoryUsage.get()

  def add(seqNum: Long, valueType: ValueType, key: Slice, value: Slice): Unit = {
    Objects.requireNonNull(valueType, "valueType is null")
    Objects.requireNonNull(key, "key is null")
    Objects.requireNonNull(value, "value is null")

    val internalKey：InternalKey = new InternalKey(key, seqNum, valueType)
  }
}
