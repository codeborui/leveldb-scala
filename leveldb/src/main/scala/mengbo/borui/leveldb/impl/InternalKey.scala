package mengbo.borui.leveldb.impl

import java.util.Objects

import com.google.common.base.Preconditions
import mengbo.borui.leveldb.impl.ValueType.ValueType
import mengbo.borui.leveldb.util.{SeqNumUtils, SizeOf, Slice, Slices}

/**
  * @author mengbo
  * @version 1.0
  */
object InternalKey {
  private def getUserKey(data: Slice): Slice = {
    data.slice(0, data.getLength - SizeOf.SIZE_OF_LONG)
  }
}

class InternalKey() {
  private var userKey: Slice = _
  private var seqNum: Long = -1
  private var valueType: ValueType = ValueType.DELETION

  def this(userKey: Slice, seqNum: Long, valueType: ValueType) = {
    this()
    Objects.requireNonNull(userKey, "userKey is null")
    Preconditions.checkArgument(seqNum >= 0, "sequenceNumber is negative")
    Objects.requireNonNull(valueType, "valueType is null")
    this.userKey = userKey
    this.seqNum = seqNum
    this.valueType = valueType
  }

  def this(data: Slice) = {
    this()
    Objects.requireNonNull(data, "data is null")
    Preconditions.checkArgument(data.getLength >= SizeOf.SIZE_OF_LONG,
      "data must be at least %s bytes", SizeOf.SIZE_OF_LONG)
    this.userKey = InternalKey.getUserKey(data)
    val packedSequenceAndType: Long = data.getLong(data.getLength - SizeOf.SIZE_OF_LONG)
    this.seqNum = SeqNumUtils.unpackSequenceNumber(packedSequenceAndType)
    this.valueType = SeqNumUtils.unpackValueType(packedSequenceAndType)
  }

  def this(data: Array[Byte]) = {
    this(Slices.wrappedBuffer(data))
  }

  def getUserKey: Slice = this.userKey

  def getSeqNum: Long = this.seqNum

  def getValueType: ValueType = this.valueType

  def encode: Slice = {
    val slice: Slice = Slices.allocate(this.userKey.getLength + SizeOf.SIZE_OF_LONG)
  }
}
