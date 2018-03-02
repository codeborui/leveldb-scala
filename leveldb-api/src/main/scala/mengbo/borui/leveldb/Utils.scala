package mengbo.borui.leveldb

import java.nio.charset.StandardCharsets

/**
  * @author mengbo
  * @version 1.0
  */
object Utils {
  def checkArgNotNull(value: Any, name: String): Unit = {
    if (value == null) throw new IllegalArgumentException("The " + name + " argument cannot be null")
  }

  def bytes(value: String): Array[Byte] = if (value == null) null else value.getBytes(StandardCharsets.UTF_8)

  def asString(value: Array[Byte]): String = if (value == null) null else new String(value)
}
