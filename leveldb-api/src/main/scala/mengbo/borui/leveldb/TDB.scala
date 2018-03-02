package mengbo.borui.leveldb

import java.io.Closeable

/**
  * @author mengbo
  * @version 1.0
  */
trait TDB extends Iterable[(Array[Byte], Array[Byte])] with Closeable{

  def get(key : Array[Byte]) : Array[Byte]
}
