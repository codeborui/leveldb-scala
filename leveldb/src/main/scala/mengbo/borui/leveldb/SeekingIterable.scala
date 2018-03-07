package mengbo.borui.leveldb

/**
  * @author mengbo
  * @version 1.0
  */
trait SeekingIterable[K, V] extends Iterable[(K, V)] {
  override def iterator: SeekingIterator[(K, V)]
}
