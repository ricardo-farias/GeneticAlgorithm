import scalafx.animation.AnimationTimer
import scalafx.application
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.input.{MouseEvent}
import scalafx.scene.layout.{BorderPane, GridPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import src.main.scala.{Board, Constants}

object Main {


  def initializeScene(grid : GridPane): Unit ={
//    grid.setHgap(.5)
//    grid.setVgap(.5)
    for (x <- 0 until Constants.MAX_BOARD_HEIGHT) {
      for (y <- 0 until Constants.MAX_BOARD_WIDTH) {
        val rect = new Rectangle()
        rect.setHeight(Constants.SCALING_VALUE)
        rect.setWidth(Constants.SCALING_VALUE)
        Board.board(x)(y) match {
          case 0 => {
            rect.setFill(Color.LightGrey)
            grid.add(rect, x, y)
          }
          case -1 => {
            rect.setFill(Color.Blue)
            grid.add(rect, x, y)
          }
          case _ => {
            rect.setFill(Board.getObjectAt(x, y).getColor)
            grid.add(rect, x, y)
          }
        }
      }
    }
  }

  def updateScene(grid: GridPane): Unit ={
    initializeScene(grid)
  }


  def simulation(grid: GridPane): Unit ={
    Board.updateSnakes()
    updateScene(grid)
  }



  def main(args : Array[String]): Unit ={

    val app = new JFXApp{
      stage = new application.JFXApp.PrimaryStage{
        title = "Snake Simulation"
        scene = new Scene(Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT){
          val border = new BorderPane()
          border.padding = Insets(25)
          val grid = new GridPane()
          val startButton = new Button("Start Simulation")
          val stopButton = new Button("Stop Simulation")


          val text = new Label("Score: ")
          var score = new Label("0")
          var continue = true


          val buttonLayout = new HBox{
            children = List(startButton, stopButton, text, score)
          }

          border.top = buttonLayout

          border.center = grid
          Board.generate()

          initializeScene(grid)
          root = border


          var status = true
          var lastTime = 0L

          val timer = AnimationTimer {time =>
            if (lastTime > 0) {
              simulation(grid)
              Thread.sleep(100)
            }
            lastTime = time
          }

          startButton.onMouseClicked = (me : MouseEvent) => {
            print("Simulation has Started!!")
            timer.start()
          }
          stopButton.onMouseClicked = (me : MouseEvent) => {
            print("Simulation has Ended!!")
            timer.stop()
          }

        }
      }
    }

    app.main(args)
  }

}
