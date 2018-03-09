package mengbo.borui.leveldb.impl

import java.nio.charset.StandardCharsets.UTF_8
import java.util.Objects

import com.google.common.base.Preconditions
import mengbo.borui.leveldb.impl.ValueType.ValueType
import mengbo.borui.leveldb.util._

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
  private var hash: Int = 0

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
    val output: SliceOutput = slice.output()
    output.writeBytes(userKey)
    output.writeLong(SeqNumUtils.packSequenceAndValueType(seqNum, valueType))
    slice
  }

  override def hashCode(): Int = {
    if (hash == 0) {
      var result = if (userKey != null) userKey.hashCode() else 0
      result = 31 * result + (seqNum ^ (seqNum >>> 32)).toInt
      result = 31 * result + (if (valueType != null) valueType.hashCode else 0)
      if (result == 0) result = 1
      hash = result
    }
    hash
  }

  override def equals(obj: Any): Boolean = {
    if (this == obj) {
      return true
    }

    if (obj == null || obj.getClass != getClass) {
      return false
    }

    val that: InternalKey = getClass.cast(obj)
    if (seqNum != that.getSeqNum) {
      return false
    }
    if (valueType != that.getValueType) {
      return false
    }
    if (userKey != null) {
      userKey.equals(that.userKey)
    } else {
      that.getUserKey == null
    }
  }

  override def toString: String = {
    val sb = new StringBuilder
    sb.append("InternalKey")
    sb.append("{key=").append(getUserKey.toString(UTF_8)) // todo don't print the real value
    sb.append(", sequenceNumber=").append(getSeqNum)
    sb.append(", valueType=").append(getValueType)
    sb.append('}')
    sb.toString
  }
}
