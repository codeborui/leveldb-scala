package mengbo.borui.leveldb.impl

import java.io.File

import mengbo.borui.leveldb.{Options, TDB}

/**
  * @author mengbo
  * @version 1.0
  */
class DB(options: Options, path: File) extends TDB{
  override def get(key: Array[Byte]): Array[Byte] = ???

  override def iterator: Iterator[(Array[Byte], Array[Byte])] = ???

  override def close(): Unit = ???
}
