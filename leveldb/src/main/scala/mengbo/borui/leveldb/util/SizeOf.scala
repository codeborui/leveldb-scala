package mengbo.borui.leveldb.util

/**
  * @author mengbo
  * @version 1.0
  */
object SizeOf {
  val SIZE_OF_BYTE: Int = 1
  val SIZE_OF_SHORT: Int = 2
  val SIZE_OF_INT: Int = 4
  val SIZE_OF_LONG: Int = 8
}

object Main {
  def main(args: Array[String]): Unit = {
    println(SizeOf.SIZE_OF_BYTE)
  }
}
