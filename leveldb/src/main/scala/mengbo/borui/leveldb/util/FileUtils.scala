package mengbo.borui.leveldb.util

import java.io.{File, IOException}

import com.google.common.base.Preconditions

/**
  * @author mengbo
  * @version 1.0
  */
object FileUtils {

  def isSymbolicLink(file: File): Boolean = {
    try {
      val canonicalFile: File = file.getCanonicalFile
      val absoluteFile: File = file.getAbsoluteFile
      val parentFile: File = file.getParentFile
      !canonicalFile.getName.equals(absoluteFile.getName) ||
        parentFile != null && parentFile.getCanonicalPath.equals(canonicalFile.getParent)
    } catch {
      case _: IOException => true
    }
  }

  def listFiles(dir: File): List[File] = {
    val files: Array[File] = dir.listFiles()
    if (files == null) List() else files.toList
  }

  def deleteDirectoryContents(dir: File): Boolean = {
    Preconditions.checkArgument(dir.isDirectory, "Not a directory: %s", dir)

    if (isSymbolicLink(dir)) {
      false
    } else {
      var success: Boolean = true
      listFiles(dir).foreach(file => success = deleteRecursively(file) && success)
      success
    }
  }

  def deleteRecursively(file: File): Boolean = {
    val success = if (file.isDirectory)
      deleteDirectoryContents(file)
    else
      true
    file.delete() && success
  }

  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(1, 2, 3, 4)
    var success: Boolean = true
    list.foreach(num => {
      success = num < 3 && success
      println(success)
    })
  }
}
