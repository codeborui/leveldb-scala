package mengbo.borui.leveldb

/**
  * @author mengbo
  * @version 1.0
  */
object Test {
  def main(args: Array[String]): Unit = {
    val input: Array[Int] = Array(1, 2, 3, 3, 3, 4, 5)
    println(binarySearch(input, 4))
  }

  def binarySearch(input: Array[Int], target: Int): Int = {
    if (input == null || input.length == 0
      || target < input(0) || target > input(input.length - 1)) {
      return -1
    }
    var start: Int = 0
    var end: Int = input.length - 1
    while (start <= end) {
      val mid = start + ((end - start) >> 1)
      if (input(mid) == target) {
        end = mid
      } else if (input(mid) < target) {
        start = mid + 1
      } else {
        end = mid - 1
      }
    }
    -1
  }
}
