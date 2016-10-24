package piconot.external

import java.io.FileNotFoundException
import picolib.maze.Maze
import picolib.semantics._
import piconot.external.parser.PiconotParser
import piconot.external.semantics._
import scalafx.application.JFXApp

// TODO import internal stuff

object Piconot extends JFXApp {

  implicit def intToState(i: Int): State = State(i.toString)
  implicit def stateToInt(s: State): Int = s.name.toInt
  def anySurr: Surroundings = Surroundings(Anything, Anything, Anything, Anything)

  val args = parameters.raw

  // Error handling: did the user pass two arguments?
  if (args.length != 2) {
    println(usage)
    sys.exit(1)
  }

  // parse the maze file
  val mazeFileName = args(0)
  val maze = Maze(getFileLines(mazeFileName))

  // parse the program file
  val programFilename = args(1)
  val program = PiconotParser(getFileContents(programFilename))

  // process the results of parsing
  program match {
    // Error handling: syntax errors
    case e: PiconotParser.NoSuccess  ⇒ println(e)

    // if parsing succeeded...
    case PiconotParser.Success(t, _) ⇒ {
      println(offsetRules(program.get))
      val bot = new Picobot(maze, offsetRules(program.get)) with TextDisplay with GUIDisplay
      checkErrors(bot)
      bot.run
    }
  }

  def offsetRules(rules: List[List[Rule]]): List[Rule] = {
    val offsets: List[Int] = 0 :: rules.map(_.map(_.endState.name.toInt).max).dropRight(1)

    val adjusted: List[Rule] = adjustingRules(rules, offsets).flatten

    val lastState: Int = adjusted.map(_.endState.name.toInt).max

    // filler state
    adjusted ++ List(Rule(lastState, anySurr, StayHere, lastState))
  }

  def adjustingRules(rules: List[List[Rule]], offsets: List[Int]): List[List[Rule]] = {
    if (rules.isEmpty || offsets.isEmpty) {
      println(s"RULES: $rules")
      return List(List())
    }

    val adjusted = rules.map(_.map(r => r.copy(startState = r.startState + offsets.head, endState = r.endState + offsets.head)))
    adjusted.head :: adjustingRules(adjusted.tail, offsets.tail)
  }

  /** A string that describes how to use the program **/
  def usage = "usage: piconot.external.Piconot <maze-file> <rules-file>"

  /**
    * Given a filename, get a list of the lines in the file
    */
  def getFileLines(filename: String): List[String] =
  try {
    io.Source.fromFile(filename).getLines().toList
  }
  catch { // Error handling: non-existent file
    case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
  }

  /**
    * Given a filename, get the contents of the file
    */
  def getFileContents(filename: String): String =
  try {
    io.Source.fromFile(filename).mkString
  }
  catch { // Error handling: non-existent file
    case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
  }

  /**
    * Check for errors. If there are any print them and exit
    */
  def checkErrors(bot: Picobot): Unit = {
    object checker extends DefaultChecker[Picobot]
      with MoveToWall with BoxedIn with UndefinedStates with UreachableStates
    val errors = checker.check(bot)
    if (!errors.isEmpty) {
      errors foreach println
      sys.exit(1)
    }
  }
}
