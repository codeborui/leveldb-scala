package mengbo.borui.leveldb

/**
  * @author mengbo
  * @version 1.0
  */
class DBException(string: String, throwable: Throwable)
  extends RuntimeException(string: String, throwable: Throwable) {

  def this() = {
    this(null, null)
  }

  def this(string: String) = {
    this(string, null)
  }

  def this(throwable: Throwable) = {
    this(null, throwable)
  }
}
