## Inheritance & Extensions (#1)

Following `AdjustTemp.kt`, add two extension functions `openWindow()` and
`fan()` as ways of cooling. Add a class `DeltaTemperature2` where the extension
functions are instead member functions, and an overloaded `adjust()` function
which takes a `DeltaTemperature2`. Which approach seems better, or are they
about the same?

```kotlin
// InheritanceExtensions/InhExtensionsEx1.kt
package inheritanceextensionsex1

import atomictest.*

val trace = Trace()

class DeltaTemperature(
  val current: Double,
  val target: Double
)

fun DeltaTemperature.heat() {
  if (current < target)
    trace("heating to $target ")
}

fun DeltaTemperature.cool() {
  if (current > target)
    trace("cooling to $target ")
}

fun DeltaTemperature.openWindow() {
  if (current > target)
    trace("cooling to $target")
}

fun DeltaTemperature.fan() {
  if (current > target)
    trace("cooling to $target ")
}

class DeltaTemperature2(
  val current: Double,
  val target: Double
) {
  fun heat() {
    if (current < target)
      trace("heating to $target ")
  }
  fun cool() {
    if (current > target)
      trace("cooling to $target ")
  }
  fun openWindow() {
    if (current > target)
      trace("cooling to $target")
  }
  fun fan() {
    if (current > target)
      trace("cooling to $target ")
  }
}

fun adjust(deltaT: DeltaTemperature) {
  deltaT.heat()
  deltaT.cool()
  deltaT.openWindow()
  deltaT.fan()
}

fun adjust(deltaT: DeltaTemperature2) {
  deltaT.heat()
  deltaT.cool()
  deltaT.openWindow()
  deltaT.fan()
}

fun main() {
  adjust(DeltaTemperature(60.0, 70.0))
  adjust(DeltaTemperature(80.0, 60.0))
  adjust(DeltaTemperature2(60.0, 70.0))
  adjust(DeltaTemperature2(80.0, 60.0))
  trace eq """
  heating to 70.0
  cooling to 60.0
  cooling to 60.0
  cooling to 60.0
  heating to 70.0
  cooling to 60.0
  cooling to 60.0
  cooling to 60.0
  """
}
```