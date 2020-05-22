package src.main.scala

object Board{
  var board = Array.ofDim[Int](Constants.MAX_BOARD_WIDTH, Constants.MAX_BOARD_HEIGHT)

  val EMPTY = 0


  private var population = scala.collection.mutable.Map[Snake, (Int, Int)]()

  def generate(): Unit = {
    for (x <- 0 until Constants.MAX_BOARD_WIDTH){
      for (y <- 0 until Constants.MAX_BOARD_HEIGHT){
        board(x)(y) = EMPTY
      }
    }

    for (i <- 1 until Constants.POPULATION_SIZE){
      val position = placeObject(i)
      val snake = new Snake(i, position._1, position._2)
      population(snake) = (position._1, position._2)
    }
  }

  def isOpen(x : Int, y : Int): Boolean = board(x)(y) == 0


  def getObjectAt(x : Int, y : Int) : Snake = population.keys.find(snake => (snake.getUniqueIdentifier() == board(x)(y))).getOrElse(new Snake(-1 , -1, -1))

  def getIdentifierAt(x : Int, y : Int) : Int = board(x)(y)

  def placeObject(id : Int): (Int, Int) ={
    val rand = new scala.util.Random
    var rand_x = rand.nextInt(Constants.MAX_BOARD_WIDTH)
    var rand_y = rand.nextInt(Constants.MAX_BOARD_HEIGHT)
    while(!isOpen(rand_x, rand_y)){
      rand_x = rand.nextInt(Constants.MAX_BOARD_WIDTH)
      rand_y = rand.nextInt(Constants.MAX_BOARD_HEIGHT)
    }
    board(rand_x)(rand_y) = id
    (rand_x, rand_y)
  }

  def updateBoard(identifier : Int, old_pos : (Int, Int), new_pos : (Int, Int)) : Unit = {
    if (old_pos._1 != -1 && old_pos._2 != -1) {
      board(old_pos._1)(old_pos._2) = 0
      board(new_pos._1)(new_pos._2) = identifier
    }
  }

  def updateSnakes(): Unit = {
    population.keys.foreach(key => key.move())
    population.keys.foreach(key => {
      val old_position = population.get(key).getOrElse(-1, -1)
      val new_position = (key.getX(), key.getY())
      population.update(key, new_position)
      val id = key.getUniqueIdentifier()
      updateBoard(id, old_position, new_position)
    })
  }

}
