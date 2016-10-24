package piconot.external

import java.io.File
import scalafx.application.JFXApp

import picolib.maze.Maze
import picolib.semantics._

object Piconot extends JFXApp {

  val args = parameters.unnamed

  require(args.size == 2)

  val mazeFileName = args(0)
  val srcFileName = args(1)

  val maze = Maze(mazeFileName)

  // parse file
  val sourceFile = io.Source.fromFile(srcFileName)
  val source = try sourceFile.mkString finally sourceFile.close()

  val parseResult = PiconotParser(source)
  require(parseResult.successful)

  val ast = parseResult.get
  val rules = PiconotRunner.run(ast)

  object Bot extends Picobot(maze, rules)
    with TextDisplay with GUIDisplay

  stage = Bot.mainStage

  Bot.run()
}
