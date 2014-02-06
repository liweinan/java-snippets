/**
 * Anagram Algorithm is a tree
 */
class Anagram {
  var arr = Array('a', 'b', 'c', 'd')

  def anagram: Unit = anagram(arr.length)

  def anagram(size: Int): Unit = {
    if (size > 1) {
      for (i <- 1 to size) {
        anagram(size - 1)
        //        print(size + "-" + i + ": ")
        if (size == 2)
          printArray
        rotate(size)
      }
    }
  }

  def rotate(size: Int): Unit = {
    val pos = arr.length - size
    val left = arr.slice(0, pos)
    val tmp = arr(pos)
    val right = arr.slice(pos + 1, arr.length)
    arr = left ++ right ++ Array(tmp)
  }

  def printArray = {
    for (s <- arr) {
      print(s)
    }
    println("")
  }
}

object Anagram {
  def main(args: Array[String]): Unit = {
    val anagram = new Anagram()
    anagram.anagram
  }
}

//          4-1           4-2 4-3 4-4...
//           |
//   +-------+-------+
//   |       |       |
//  3-1     3-2     3-3
//  / \     / \     / \
//2-1 2-2 2-1 2-2 2-1 2-2

/*
2-1: abcd -> ab...
2-2: abdc
3-1: abcd -> ac...
2-1: acdb
2-2: acbd
3-2: acdb -> ad...
2-1: adbc
2-2: adcb
3-3: adbc
4-1: abcd --> b...
2-1: bcda --> bc...
2-2: bcad
3-1: bcda --> bd...
2-1: bdac
2-2: bdca
3-2: bdac --> ba...
2-1: bacd
2-2: badc
3-3: bacd
4-2: bcda --> c...
2-1: cdab --> cd...
2-2: cdba
3-1: cdab
2-1: cabd
2-2: cadb
3-2: cabd
2-1: cbda
2-2: cbad
3-3: cbda
4-3: cdab
2-1: dabc
2-2: dacb
3-1: dabc
2-1: dbca
2-2: dbac
3-2: dbca
2-1: dcab
2-2: dcba
3-3: dcab
4-4: dabc*/
