package mengbo.borui.leveldb

import java.io.Closeable

/**
  * @author mengbo
  * @version 1.0
  */
trait TWriteBatch extends Closeable {
  def put(key: Array[Byte], value: Array[Byte]): TWriteBatch

  def delete(key: Array[Byte]): TWriteBatch
}
