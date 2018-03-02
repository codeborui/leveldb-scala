package mengbo.borui.leveldb.impl

import java.io._
import java.nio.charset.StandardCharsets

import mengbo.borui.leveldb.util.FileUtils
import mengbo.borui.leveldb.{Options, TDB, TDBFactory}

/**
  * @author mengbo
  * @version 1.0
  */
object DBFactory {
  private val is64bit: Boolean = if (System.getProperty("os.name").contains("Windows")) {
    System.getenv("ProgramFiles(x86)") != null
  }
  else {
    System.getProperty("os.arch").contains("64")
  }

  val CPU_DATA_MODEL: Int = if (is64bit) 64 else 32
  val USE_MMAP: Boolean = System.getProperty("leveldb.mmap", "" + (CPU_DATA_MODEL > 32)).toBoolean

  val VERSION: String = {
    val is: InputStream = classOf[DBFactory].getResourceAsStream("version.txt")
    try {
      new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).readLine()
    } catch {
      case _: Throwable => "unknown"
    } finally {
      try {
        is.close()
      } catch {
        case _: Throwable => None
      }
    }
  }

  val factory : DBFactory = new DBFactory
}

class DBFactory extends TDBFactory {

  @throws(classOf[IOException])
  override def open(path: File, options: Options): TDB = new DB(options, path)

  @throws(classOf[IOException])
  override def destory(path: File, options: Options): Unit = FileUtils.deleteRecursively(path)
}

object Main {
  def main(args: Array[String]): Unit = {
    println(DBFactory.VERSION)
  }
}
