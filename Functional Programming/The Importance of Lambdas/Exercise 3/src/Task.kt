package theImportanceofLambdas3

fun other(s: String): String =
  s.filterIndexed { index, _ -> index % 2 == 0 }

fun main() {
  println(other("cement"))
}
/* Expected output:
cmn
*/