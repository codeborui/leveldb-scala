package mengbo.borui.leveldb.impl

import java.util.Comparator

/**
  * @author mengbo
  * @version 1.0
  */
class InternalKeyComparator(userComparator: UserComparator) extends Comparator[InternalKey] {

  def getUserComparator: UserComparator = userComparator

  def name: String = userComparator.name

  override def compare(o1: InternalKey, o2: InternalKey): Int = {
    val rel: Int = userComparator.compare(o1.getUserKey, o2.getUserKey)
    if (rel != 0) {
      return rel
    }
    o2.getSeqNum compareTo o1.getSeqNum
  }

  def isOrdered(keys: Iterable[InternalKey]): Boolean = {

  }
}
