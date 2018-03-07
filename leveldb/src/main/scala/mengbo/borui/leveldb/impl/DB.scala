package mengbo.borui.leveldb.impl

import java.io.File

import mengbo.borui.leveldb._

/**
  * @author mengbo
  * @version 1.0
  */
class DB(options: Options, path: File) extends TDB {

  @throws(classOf[DBException])
  override def put(key: Array[Byte], value: Array[Byte]): Unit = {
    put(key, value, new WriteOptions())
  }

  @throws(classOf[DBException])
  override def put(key: Array[Byte], value: Array[Byte], options: WriteOptions): TSnapshot = ???

  override def iterator: Iterator[(Array[Byte], Array[Byte])] = ???

  override def close(): Unit = ???
}
