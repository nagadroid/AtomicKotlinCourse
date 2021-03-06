package secondaryConstructorsExercise1

import org.junit.Test
import util.loadMemberProperty
import kotlin.test.assertEquals

class TestSecondaryConstructorsExercise1 {
    @Test fun testSolution() {
        val flowerClass = Flower::class
        val typeProp = loadMemberProperty(flowerClass, "type")
        val constructor = flowerClass.constructors.find { it.parameters.isEmpty() }
                ?: throw AssertionError("Can't find constructor without parameters in the class 'Flower'")
        val flowerInstance = constructor.call()
        val typeValue = typeProp.getter.call(flowerInstance)

        assertEquals("Daisy", typeValue, "After calling a constructor without parameters the value of 'type' should be 'Daisy'")
    }
}