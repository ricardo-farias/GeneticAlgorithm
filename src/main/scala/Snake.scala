package src.main.scala

import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

import scala.util.Random

class Snake(identifier : Int, x : Int, y : Int) extends Rectangle{

  var x_pos: Int = x
  var y_pos: Int = y

  var timesEaten : Int = 0
  var vision : Int = 1
  var health : Double = 100
  var hunger : Double = 0
  var numberOfMoves : Int = 1

  def fitness : Double = {
    Math.abs(timesEaten * health * hunger)/numberOfMoves
  }
  var chromosome : List[Double] = List(health, hunger, vision)


  def getColor: Color = {
    if (hunger <= .25) Color.Green
    else if (hunger > .25 && hunger < .75) Color.Yellow
    else if (hunger > .75 && hunger < 1) Color.Orange
    else Color.Red
  }

  def getUniqueIdentifier: Int = identifier

  def dead(): Boolean = (hasFallen || health == 0)

  def getX: Int = x_pos

  def getY: Int = y_pos

  def hasFallen: Boolean = (x_pos < 0 || x_pos > Constants.MAX_BOARD_WIDTH) || (y_pos < 0 || y_pos > Constants.MAX_BOARD_HEIGHT)

  def eat(): Unit = {
    timesEaten += 1
    health += 20
    hunger -= 40
  }


  def move(): Unit = {
    val surroundings = lookAround()
    val decision = makeDecision(surroundings)
    if ((decision._1 != x_pos || decision._2 != y_pos) && hunger < 1) {
      hunger = hunger + Constants.HUNGER_GROWTH
    }
    else if ((decision._1 != x_pos || decision._2 != y_pos) && hunger >= 1) {
      health = health - Constants.HUNGER_DAMAGE
    }
    x_pos = decision._1
    y_pos = decision._2
    numberOfMoves += 1
    println(f"Snake Health: ${health}, Hunger: ${hunger}, Move X: ${x_pos}, Move Y: ${y_pos}, Fitness: ${fitness}")
  }

  def makeDecision(surroundings : scala.collection.mutable.Map[Int, List[(Int, Int)]]) : (Int, Int) = {
    val ran = new Random
    if (surroundings.keys.exists(k => k == -1)){
      val decision = surroundings.get(-1).get(0)
      eat()
      decision
    }else {
      val length = surroundings.get(0).size
      val index = ran.nextInt(length+1)
      val decision = surroundings(0)(index)
      decision
    }
  }

  def lookAround() : scala.collection.mutable.Map[Int, List[(Int, Int)]] = {
    val surroundings = scala.collection.mutable.Map[Int, List[(Int, Int)]]()
    for (i <- 1 to vision){
      if (x_pos + i < Constants.MAX_BOARD_WIDTH){                         // Right
        val id = Board.getIdentifierAt(x_pos + i, y_pos)
        surroundings(id) = surroundings.getOrElse(id, List()).::((x_pos + i, y_pos))
      }
      if (x_pos - i >= 0){                                            // Left
        val id = Board.getIdentifierAt(x_pos - i, y_pos)
        surroundings(id) = surroundings.getOrElse(id, List()).::((x_pos - i, y_pos))
      }
      if (y_pos + i < Constants.MAX_BOARD_HEIGHT){                   // Up
        val id = Board.getIdentifierAt(x_pos, y_pos + i)
        surroundings(id) = surroundings.getOrElse(id, List()).::((x_pos, y_pos + i))
      }
      if (y_pos - i >= 0) {                                          // Down
        val id = Board.getIdentifierAt(x_pos, y_pos - i)
        surroundings(id) = surroundings.getOrElse(id, List()).::((x_pos, y_pos - i))
      }
    }
    surroundings
  }




}
