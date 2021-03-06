## Polymorphism (#2)

Add `Wizard`, which implements `Magician`, and `Goblin` which implements
`Fighter`, to `FantasyGame.kt`. In `main()`, create and test a `Wizard` named
"Max" and a `Goblin` named "Gorbag."

```kotlin
// Polymorphism/PolyExercise2.kt
package polymorphismex2
import atomictest.*

private val trace = Trace()

interface Character {
  val name: String
  val type: String
  fun skills(): String
  fun play() = "$name $type: ${skills()}"
}

interface Magician {
  fun skills() = "Magic"
}

interface Fighter {
  fun skills() = "Fighting"
}

interface Flyer {
  fun skills() = "Flying"
}

open class Elf(override val name: String) :
  Character, Magician, Flyer {
  override val type = "Elf"
  override fun skills() =
    super<Magician>.skills() + ", " +
      super<Flyer>.skills()
}

class FightingElf(name: String) :
  Elf(name), Fighter {
  override val type = "FightingElf"
  override fun skills() =
    super<Elf>.skills() + ", " +
      super<Fighter>.skills()
}

class Warrior(override val name: String) :
  Character, Fighter {
  override val type = "Warrior"
  override fun skills() = super.skills()
}

class Dragon(override val name: String) :
  Character, Magician, Flyer {
  override val type = "Dragon"
  override fun skills() =
    super<Magician>.skills() + ", " +
      super<Flyer>.skills()
}

class Wizard(override val name: String) :
  Character, Magician {
  override val type = "Wizard"
  override fun skills() = super.skills()
}

class Goblin(override val name: String) :
  Character, Fighter {
  override val type = "Goblin"
  override fun skills() = super.skills()
}

fun match(c1: Character, c2: Character) =
  trace("${c1.play()} -> ${c2.play()}")

fun main() {
  val characters: List<Character> = listOf(
    Elf("Titania"),
    FightingElf("Legolas"),
    Warrior("Conan"),
    Dragon("Puff"),
    Wizard("Max"),
    Goblin("Gorbag")
  )
  characters.forEach { c1 ->
    characters.filter { it != c1 }
      .forEach { c2 -> match(c1, c2) }
  }
  trace eq """
  Titania Elf: Magic, Flying ->
    Legolas FightingElf:
      Magic, Flying, Fighting
  Titania Elf: Magic, Flying ->
    Conan Warrior: Fighting
  Titania Elf: Magic, Flying ->
    Puff Dragon: Magic, Flying
  Titania Elf: Magic, Flying ->
    Max Wizard: Magic
  Titania Elf: Magic, Flying ->
    Gorbag Goblin: Fighting
  Legolas FightingElf:
      Magic, Flying, Fighting ->
    Titania Elf: Magic, Flying
  Legolas FightingElf:
      Magic, Flying, Fighting ->
    Conan Warrior: Fighting
  Legolas FightingElf:
      Magic, Flying, Fighting ->
    Puff Dragon: Magic, Flying
  Legolas FightingElf:
      Magic, Flying, Fighting ->
    Max Wizard: Magic
  Legolas FightingElf:
      Magic, Flying, Fighting ->
    Gorbag Goblin: Fighting
  Conan Warrior: Fighting ->
    Titania Elf: Magic, Flying
  Conan Warrior: Fighting ->
    Legolas FightingElf:
      Magic, Flying, Fighting
  Conan Warrior: Fighting ->
    Puff Dragon: Magic, Flying
  Conan Warrior: Fighting ->
    Max Wizard: Magic
  Conan Warrior: Fighting ->
    Gorbag Goblin: Fighting
  Puff Dragon: Magic, Flying ->
    Titania Elf: Magic, Flying
  Puff Dragon: Magic, Flying ->
    Legolas FightingElf:
      Magic, Flying, Fighting
  Puff Dragon: Magic, Flying ->
    Conan Warrior: Fighting
  Puff Dragon: Magic, Flying ->
    Max Wizard: Magic
  Puff Dragon: Magic, Flying ->
    Gorbag Goblin: Fighting
  Max Wizard: Magic ->
    Titania Elf: Magic, Flying
  Max Wizard: Magic ->
    Legolas FightingElf:
      Magic, Flying, Fighting
  Max Wizard: Magic ->
    Conan Warrior: Fighting
  Max Wizard: Magic ->
    Puff Dragon: Magic, Flying
  Max Wizard: Magic ->
    Gorbag Goblin: Fighting
  Gorbag Goblin: Fighting ->
    Titania Elf: Magic, Flying
  Gorbag Goblin: Fighting ->
    Legolas FightingElf:
      Magic, Flying, Fighting
  Gorbag Goblin: Fighting ->
    Conan Warrior: Fighting
  Gorbag Goblin: Fighting ->
    Puff Dragon: Magic, Flying
  Gorbag Goblin: Fighting ->
    Max Wizard: Magic
  """
}
```