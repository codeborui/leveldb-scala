package mengbo.borui.leveldb.impl.memtable

import java.util.concurrent.ConcurrentSkipListMap

import mengbo.borui.leveldb.SeekingIterable
import mengbo.borui.leveldb.impl.InternalKey
import mengbo.borui.leveldb.util.Slice

/**
  * @author mengbo
  * @version 1.0
  */
object MemTable extends SeekingIterable[(InternalKey, Slice)]{
  private var table: ConcurrentSkipListMap[(InternalKey, Slice)] = _
}

class MemTable extends SeekingIterable[(InternalKey, Slice)] {

}
