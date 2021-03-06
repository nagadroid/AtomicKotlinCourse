// RobotExplorer1/Room.kt
package robotexplorer1
import robotexplorer1.Player.*

class Room(var player: Any = Void) {
  val doors = Doors()
  fun enter(robot: Robot): Room {
    when (val p = player) {
      // Stay in original room:
      Wall, Void -> return robot.room
      is Teleport -> return p.targetRoom
      EndGame -> return Room(EndGame)
      Food -> {
        robot.energy++ // Eat food
        player = Empty
      }
      Empty -> {}
    }
    return this // Enter new room
  }
}