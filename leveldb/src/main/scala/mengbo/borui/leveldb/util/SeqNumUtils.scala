package mengbo.borui.leveldb.util

import java.util.Objects

import com.google.common.base.Preconditions
import mengbo.borui.leveldb.impl.ValueType

/**
  * @author mengbo
  * @version 1.0
  */
object SeqNumUtils {

  val MAX_SEQ_NUM: Long = (0x1L << 56) - 1

  def unpackSequenceNumber(num: Long): Long = {
    num >>> 8
  }

  def unpackValueType(num: Long): ValueType.ValueType = {
    ValueType.getValueTypeByPersistentId(num.toByte)
  }

  def packSequenceAndValueType(seqNum: Long, valueType: ValueType.ValueType): Long = {
    Preconditions.checkArgument(seqNum <= SeqNumUtils.MAX_SEQ_NUM, "Seq number is greater than MAX_SEQ_NUM")
    Objects.requireNonNull(valueType, "valueType is null")

    seqNum << 8 | valueType.id
  }
}
