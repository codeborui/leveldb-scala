package mengbo.borui.leveldb

import com.google.common.collect.PeekingIterator

/**
  * @author mengbo
  * @version 1.0
  */
trait SeekingIterator[K, V] extends PeekingIterator[(K, V)] {
  def seekToFirst(): Unit

  def seek(targetKey: K): Unit
}
