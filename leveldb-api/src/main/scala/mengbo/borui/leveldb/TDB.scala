package mengbo.borui.leveldb

import java.io.Closeable

/**
  * @author mengbo
  * @version 1.0
  */
trait TDB extends Iterable[(Array[Byte], Array[Byte])] with Closeable{

  @throws(classOf[DBException])
  def put(key : Array[Byte], value: Array[Byte]): Unit

  @throws(classOf[DBException])
  def put(key: Array[Byte], value: Array[Byte], options: WriteOptions): TSnapshot
}
