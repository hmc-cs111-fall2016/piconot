package piconot.external
import java.io.FileNotFoundException
import picolib.maze.Maze
import picolib.semantics._
import scalafx.application.JFXApp

/*
 * Run Piconot using a set of rules written in the external DSL
 * created by Anna & Sophia!
 */
object Piconot extends JFXApp { 
  
  val args = parameters.raw
  
  // Check if 2 arguments were passed in
  if (args.length != 2) {
    println(usage)
    sys.exit(1)
  }
  
  // Parse maze files
  val mazeFileName = args(0)
  val maze = Maze (
    try {
      io.Source.fromFile(mazeFileName).getLines().toList
    }
    catch { // error
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }
  )

  // Parse program file
  val programFilename = args(1)
  val program = Parser (
    try {
      io.Source.fromFile(programFilename).mkString
    }
    catch { // Error handling: non-existent file
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }
  )
  
  // Results of parsing
  program match {      
  // Check syntax errors
  case e: Parser.NoSuccess  ⇒ println(e)
  
  // Successful parse!
  case Parser.Success(t, _) ⇒ {
    val bot = new Picobot(maze, program.get) with TextDisplay with GUIDisplay    
    bot.run
  }
  }
  
  // A string that describes how to use the program 
  def usage = "usage: piconot.external.Piconot <maze-file> <rules-file>"
}