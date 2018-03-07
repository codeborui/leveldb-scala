package mengbo.borui.leveldb

/**
  * @author mengbo
  * @version 1.0
  */
class WriteOptions {
  private var sync: Boolean = false
  private var snapshot: Boolean = false

  def getSync: Boolean = sync

  def setSync(sync: Boolean): WriteOptions = {
    this.sync = sync
    this
  }

  def getSnapshot: Boolean = snapshot

  def setSnapshot(snapshot: Boolean): WriteOptions = {
    this.snapshot = snapshot
    this
  }
}
