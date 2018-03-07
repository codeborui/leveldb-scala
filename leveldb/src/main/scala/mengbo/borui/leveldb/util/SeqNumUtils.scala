package mengbo.borui.leveldb.util

import mengbo.borui.leveldb.impl.ValueType

/**
  * @author mengbo
  * @version 1.0
  */
object SeqNumUtils {

  def unpackSequenceNumber(num: Long): Long = {
    num >>> 8
  }

  def unpackValueType(num: Long): ValueType.ValueType = {
    ValueType.getValueTypeByPersistentId(num.toByte)
  }
}
