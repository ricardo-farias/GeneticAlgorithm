package src.main.scala

import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

import scala.util.Random

class Snake(identifier : Int, x : Int, y : Int) extends Rectangle{

  var x_pos = x
  var y_pos = y

  var vision : Int = 1
  var health : Double = 100
  var hunger : Double = 0


  def getColor() : Color = {
    if (health == 100) Color.Green
    else if (health < 50 && health > 25) Color.Orange
    else Color.Red
  }

  def getUniqueIdentifier(): Int = identifier

  def dead(): Boolean = hasFallen() || health == 0

  def getX(): Int = x_pos

  def getY(): Int = y_pos

  def hasFallen(): Boolean = (x_pos < 0 || x_pos > Constants.MAX_BOARD_WIDTH-1) || (y_pos < 0 || y_pos > Constants.MAX_BOARD_HEIGHT-1)

  def eat(food: Food): Unit = ???


  def move(): Unit = {
    val surroundings = lookAround()
    val decision = makeDecision(surroundings)
    if (decision._1 != x_pos || decision._2 != y_pos && hunger < 1) hunger += .09
    else health -= 5
    x_pos = decision._1
    y_pos = decision._2
    println(f"Snake Health: ${health}, Hunger: ${hunger}, Move X: ${x_pos}, Move Y: ${y_pos}")
  }

  def makeDecision(surroundings : scala.collection.mutable.Map[Int, List[(Int, Int)]]) : (Int, Int) = {
    val ran = new Random
    if (surroundings.get(-1).nonEmpty || (health * hunger) > .5){
        surroundings.get(-1).getOrElse(List((x_pos, y_pos)))(0)
    }else {
      val length = surroundings.get(0).get.size
      surroundings.get(0).getOrElse(List((x_pos, y_pos)))(ran.nextInt(length))
    }
  }

  def lookAround() : scala.collection.mutable.Map[Int, List[(Int, Int)]] = {
    val surroundings = scala.collection.mutable.Map[Int, List[(Int, Int)]]()
    for (i <- 1 to vision){
      if (x_pos + i < Constants.MAX_BOARD_WIDTH){                         // Right
        val id = Board.getIdentifierAt(x_pos + i, y_pos)
        surroundings(id) = surroundings.get(id).getOrElse(List()).::((x_pos + i, y_pos))
      }
      if (x_pos - i > 0){                                            // Left
        val id = Board.getIdentifierAt(x_pos - i, y_pos)
        surroundings(id) = surroundings.get(id).getOrElse(List()).::((x_pos - i, y_pos))
      }
      if (y_pos + i < Constants.MAX_BOARD_HEIGHT){                   // Up
        val id = Board.getIdentifierAt(x_pos, y_pos + i)
        surroundings(id) = surroundings.get(id).getOrElse(List()).::((x_pos, y_pos + i))
      }
      if (y_pos - i > 0) {                                          // Down
        val id = Board.getIdentifierAt(x_pos, y_pos - i)
        surroundings(id) = surroundings.get(id).getOrElse(List()).::((x_pos, y_pos - i))
      }
    }
    surroundings
  }




}
