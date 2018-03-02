package mengbo.borui.leveldb

import java.io.Closeable

/**
  * @author mengbo
  * @version 1.0
  */
trait TDBIterator extends Iterator[(Array[Byte], Array[Byte])] with Closeable {

  def seek(key : Array[Byte]): Unit

  def seekToFirst() : Unit

  def peekNext() : (Array[Byte], Array[Byte])
}
