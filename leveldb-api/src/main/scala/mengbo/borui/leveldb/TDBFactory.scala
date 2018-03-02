package mengbo.borui.leveldb

import java.io.{File, IOException}

/**
  * @author mengbo
  * @version 1.0
  */
trait TDBFactory {

  @throws(classOf[IOException])
  def open(path: File, options: Options): TDB

  @throws(classOf[IOException])
  def destory(path: File, options: Options): Unit
}
