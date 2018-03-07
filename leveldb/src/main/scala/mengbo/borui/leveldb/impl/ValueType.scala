package mengbo.borui.leveldb.impl

/**
  * @author mengbo
  * @version 1.0
  */
object ValueType extends Enumeration {
  type ValueType = Value
  val DELETION = Value(0x00)
  val VALUES = Value(0x01)

  def getValueTypeByPersistentId(persistentId: Int): ValueType = {
    persistentId match {
      case 0 => DELETION
      case 1 => VALUES
      case _ => throw new IllegalArgumentException("Unknown persistentId " + persistentId)
    }
  }
}
