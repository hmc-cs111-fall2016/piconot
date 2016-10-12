package piconot.internal

import picolib.semantics._

import scala.language.postfixOps

/**
  * Created by tmf on 10/9/16.
  */
object EmptyBot extends PicoWhile("resources" / "empty.txt") {

  WhileOuter (free(West)) {
    List(
      move(West)
    )
  }

  WhileOuter (free(North)) {
    List(
      move(North)
    )
  }

  printRules()

  WhileOuter (free(East)) {
    List(
      WhileInner (free(South)) {
        move(South)
      },
      move(East),
      WhileInner (free(North)) {
        move(North)
      },
      move(East)
    )
  }

  WhileOuter(free(South)) {
    List(
      move(South)
    )
  }

  run
}
