package mengbo.borui.leveldb.impl

import java.util.Comparator

import mengbo.borui.leveldb.util.Slice

/**
  * @author mengbo
  * @version 1.0
  */
trait UserComparator extends Comparator[Slice] {

  def name: String

  def findShortestSeparator(start: Slice, limit: Slice): Slice

  def findShortSuccessor(key: Slice): Slice
}
