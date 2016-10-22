package piconot.internal

import scala.language.implicitConversions
import picolib.maze.Maze
import scala.collection.mutable.MutableList
import scalafx.application.JFXApp
import util.control.Breaks._
import picolib.semantics._

class PicoPrgrm(val mazeFilename: String) extends JFXApp {
  class Rules {}
  case class Label(name: String)(val rules: List[Rules]) extends Rules  {}
  case class Face(direction: Direction) extends Rules 
  case class Go(surrounding: Surrounding) extends Rules 
  // object Go {  
  //     def until(surrounding: Surrounding) = surrounding
  // }
  
  case class Jump(label: String) extends Rules

  class Surrounding extends Rules {}
  class Direction extends Rules { }
  object NORTH extends Direction {}
  object EAST extends Direction {}
  object SOUTH extends Direction {}
  object WEST extends Direction {}
  

  object RIGHT_WALL extends Surrounding {}
  object LEFT_WALL extends Surrounding {}
  object FRONT_WALL extends Surrounding {}
  object RIGHT_LEFT_WALL extends Surrounding {}
  object RIGHT_FRONT_WALL extends Surrounding {}
  object RIGHT_LEFT_FRONT_WALL extends Surrounding {}
  object LEFT_FRONT_WALL extends Surrounding {}
  object BACK_OPENING extends Surrounding {}
  object RIGHT_OPENING extends Surrounding {}
  object LEFT_OPENING extends Surrounding {}
  object RIGHT_LEFT_OPENING extends Surrounding {}
  object RIGHT_BACK_OPENING extends Surrounding {}
  object RIGHT_LEFT_BACK_OPENING extends Surrounding {}
  object LEFT_BACK_OPENING extends Surrounding {}
  

  var labelStates = Map.empty[String, Int]
  var direction: Direction = NORTH
  var stCounter = 0


  def process(labels: List[Label]) = {
    var counter = 1

    // Create a map that goes from top-level label to it's starting state
    labels.foreach((label: Label) => {
      breakable {
        if (labelStates == Map.empty){
          labelStates + (label.name -> 0)
          break
        }
        label.rules.foreach((rule: Rules) => {
            rule match {
              case Face(dir) => counter = counter + 1
              case _ => 0 
            }
          })

        labelStates + (label.name -> counter)
    }
    })
  }

  def buildrules(rules: List[Label]) = {
    var builtRules: List[picolib.semantics.Rule] = List()

    rules.foreach((label: Label) => {

      label.rules.foreach((rule: Rules) => {
        rule match {
          case Face(dir) => builtRules = builtRules ++ List(evalFace(dir))
          case Go(sur) => builtRules = builtRules ++ List(evalGo(sur))
          case Jump(lbl) => builtRules = builtRules ++ List(evalJump(lbl))
        }

        })
    })
  }

  def evalFace(dir: Direction): Rule = {
    direction = dir
    stCounter += 1
    Rule(State(stCounter - 1 + ""), Surroundings(Anything, Anything, Anything, Anything), StayHere, State(stCounter + ""))
  }

  def evalGo(sur: Surrounding): Rule = {
    var surRule = Surroundings(Anything, Anything, Anything, Anything)
    var dirRule: MoveDirection  = North
    if(direction == NORTH){
       dirRule = North
      if(sur == RIGHT_WALL){
        surRule = Surroundings(Open, Open, Anything, Anything)
      }
      else if(sur == LEFT_WALL){
        surRule = Surroundings(Open, Anything, Open, Anything)
      }
      else if(sur == FRONT_WALL){
        surRule = Surroundings(Open, Anything, Anything, Anything)
      }
      else if(sur == RIGHT_LEFT_WALL){
        surRule = Surroundings(Open, Open, Open, Anything)
      }
      else if(sur == RIGHT_FRONT_WALL){
        surRule = Surroundings(Open, Open, Anything, Anything)
      }
      else if(sur == RIGHT_LEFT_FRONT_WALL){
        surRule = Surroundings(Open, Open, Open, Anything)
      }
      else if(sur == LEFT_FRONT_WALL){
        surRule = Surroundings(Open, Anything, Open, Anything)
      }
      else if(sur == RIGHT_OPENING){
        surRule = Surroundings(Open, Blocked, Anything, Anything)
      }
      else if(sur == LEFT_OPENING){
        surRule = Surroundings(Open, Anything, Blocked, Anything)
      }
      else if(sur == RIGHT_LEFT_OPENING){
        surRule = Surroundings(Open, Blocked, Blocked, Anything)
      }
      else if(sur == RIGHT_BACK_OPENING){
        surRule = Surroundings(Open, Blocked, Anything, Blocked)
      }
      else if(sur == RIGHT_LEFT_BACK_OPENING){
        surRule = Surroundings(Open, Blocked, Blocked, Blocked)
      }
      else if(sur == LEFT_BACK_OPENING){
        surRule = Surroundings(Open, Anything, Blocked, Blocked)
      }
      else if(sur == BACK_OPENING){
        surRule = Surroundings(Open, Anything, Anything, Blocked)
      }

    }
    else if(direction == EAST){
       dirRule = East
      if(sur == RIGHT_WALL){
        surRule = Surroundings(Anything, Open, Anything, Open)
      }
      else if(sur == LEFT_WALL){
        surRule = Surroundings(Open, Open, Anything, Anything)
      }
      else if(sur == FRONT_WALL){
        surRule = Surroundings(Anything, Open, Anything, Anything)
      }
      else if(sur == RIGHT_LEFT_WALL){
        surRule = Surroundings(Open, Open, Anything, Open)
      }
      else if(sur == RIGHT_FRONT_WALL){
        surRule = Surroundings(Anything, Open, Anything, Open)
      }
      else if(sur == RIGHT_LEFT_FRONT_WALL){
        surRule = Surroundings(Open, Open, Anything, Open)
      }
      else if(sur == LEFT_FRONT_WALL){
        surRule = Surroundings(Open, Open, Anything, Anything)
      }
      else if(sur == RIGHT_OPENING){
        surRule = Surroundings(Anything, Open, Anything, Blocked)
      }
      else if(sur == LEFT_OPENING){
        surRule = Surroundings(Blocked, Open, Anything, Anything)
      }
      else if(sur == RIGHT_LEFT_OPENING){
        surRule = Surroundings(Blocked, Open, Anything, Blocked)
      }
      else if(sur == RIGHT_BACK_OPENING){
        surRule = Surroundings(Anything, Open, Blocked, Blocked)
      }
      else if(sur == RIGHT_LEFT_BACK_OPENING){
        surRule = Surroundings(Blocked, Open, Blocked, Blocked)
      }
      else if(sur == LEFT_BACK_OPENING){
        surRule = Surroundings(Blocked, Open, Blocked, Anything)
      }
      else if(sur == BACK_OPENING){
        surRule = Surroundings(Anything, Open, Blocked, Anything)
      }      
    }
    else if(direction == SOUTH){
       dirRule = South
      if(sur == RIGHT_WALL){
        surRule = Surroundings(Anything, Anything, Open, Open)
      }
      else if(sur == LEFT_WALL){
        surRule = Surroundings(Anything, Open, Anything, Open)
      }
      else if(sur == FRONT_WALL){
        surRule = Surroundings(Anything, Anything, Anything, Open)
      }
      else if(sur == RIGHT_LEFT_WALL){
        surRule = Surroundings(Anything, Open, Open, Open)
      }
      else if(sur == RIGHT_FRONT_WALL){
        surRule = Surroundings(Anything, Anything, Open, Open)
      }
      else if(sur == RIGHT_LEFT_FRONT_WALL){
        surRule = Surroundings(Anything, Open, Open, Open)
      }
      else if(sur == LEFT_FRONT_WALL){
        surRule = Surroundings(Anything, Open, Anything, Open)
      }
      else if(sur == RIGHT_OPENING){
        surRule = Surroundings(Anything, Anything, Blocked, Open)
      }
      else if(sur == LEFT_OPENING){
        surRule = Surroundings(Anything, Blocked, Anything, Open)
      }
      else if(sur == RIGHT_LEFT_OPENING){
        surRule = Surroundings(Anything, Blocked, Blocked, Open)
      }
      else if(sur == RIGHT_BACK_OPENING){
        surRule = Surroundings(Blocked, Anything, Blocked, Open)
      }
      else if(sur == RIGHT_LEFT_BACK_OPENING){
        surRule = Surroundings(Blocked, Blocked, Blocked, Open)
      }
      else if(sur == LEFT_BACK_OPENING){
        surRule = Surroundings(Blocked, Blocked, Anything, Open)
      }
      else if(sur == BACK_OPENING){
        surRule = Surroundings(Blocked, Anything, Anything, Open)
      }
    }
    else if(direction == WEST){
      dirRule = West
     if(sur == RIGHT_WALL){
        surRule = Surroundings(Open, Anything, Open, Anything)
      }
      else if(sur == LEFT_WALL){
        surRule = Surroundings(Anything, Anything, Open, Open)
      }
      else if(sur == FRONT_WALL){
        surRule = Surroundings(Anything, Anything, Open, Anything)
      }
      else if(sur == RIGHT_LEFT_WALL){
        surRule = Surroundings(Open, Anything, Open, Open)
      }
      else if(sur == RIGHT_FRONT_WALL){
        surRule = Surroundings(Open, Anything, Open, Anything)
      }
      else if(sur == RIGHT_LEFT_FRONT_WALL){
        surRule = Surroundings(Open, Anything, Open, Open)
      }
      else if(sur == LEFT_FRONT_WALL){
        surRule = Surroundings(Anything, Anything, Open, Open)
      }
      else if(sur == RIGHT_OPENING){
        surRule = Surroundings(Blocked, Anything, Open, Anything)
      }
      else if(sur == LEFT_OPENING){
        surRule = Surroundings(Anything, Anything, Open, Blocked)
      }
      else if(sur == RIGHT_LEFT_OPENING){
        surRule = Surroundings(Blocked, Anything, Open, Blocked)
      }
      else if(sur == RIGHT_BACK_OPENING){
        surRule = Surroundings(Blocked, Blocked, Open, Anything)
      }
      else if(sur == RIGHT_LEFT_BACK_OPENING){
        surRule = Surroundings(Blocked, Blocked, Open, Blocked)
      }
      else if(sur == LEFT_BACK_OPENING){
        surRule = Surroundings(Anything, Blocked, Open, Anything)
      }
      else if(sur == BACK_OPENING){
        surRule = Surroundings(Anything, Blocked, Open, Anything)
      }
    }
    else{
    }
    Rule(State(stCounter + ""), surRule, dirRule, State(stCounter + ""))
  }

  def evalJump(lbl: String) = {
    var maybeState = labelStates.get(lbl)
    var newState = ""
    maybeState match {
      case Some(lbl) => newState = lbl + ""
      case _ => null
    }
    Rule(State(stCounter + ""), Surroundings(Anything, Anything, Anything, Anything), StayHere, State(newState))
  }

//   // This part of the implementation was copied from the sample 
//   // the list of rules, which is built up as the Picobor program executes
//   private val rules = MutableList.empty[Rule]  

//   def addRule(rule: Rule) = rules += rule

//   def run() = {
//     val maze = Maze(mazeFilename)
//     val picobot = 
//       new Picobot(maze, rules.toList) with TextDisplay with GUIDisplay
//     stage = picobot.mainStage
//     picobot.run()
//   }


// ////////////////////// OUR STUFF /////////////////////////////////////////



//   case class Label(label: String)(val rules: List[Rule]) extends Rule {

//   }
  
  

  



//   case class Jump(label: Label) extends Rule


// class Rule {}
// class Direction extends Rule{ }

// object NORTH extends Direction {}
// object EAST extends Direction {}
// object SOUTH extends Direction {}
// object WEST extends Direction {}

// object BACK_WALL extends Surrounding {}
// object RIGHT_WALL extends Surrounding {}
// object LEFT_WALL extends Surrounding {}
// object FRONT_WALL extends Surrounding {}

// object RIGHT_LEFT_WALL extends Surrounding {}
// object RIGHT_FRONT_WALL extends Surrounding {}
// object RIGHT_BACK_WALL extends Surrounding {}

// object RIGHT_LEFT_FRONT_WALL extends Surrounding {}
// object RIGHT_LEFT_BACK_WALL extends Surrounding {}
// object RIGHT_FRONT_BACK_WALL extends Surrounding {}

// object LEFT_FRONT_WALL extends Surrounding {}
// object LEFT_BACK_WALL extends Surrounding {}

// object LEFT_FRONT_BACK_WALL extends Surrounding {}

// object FRONT_BACK_WALL extends Surrounding {}  


}



