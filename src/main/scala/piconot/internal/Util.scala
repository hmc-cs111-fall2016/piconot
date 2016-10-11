package piconot.internal

import PiconotTypes._
import scala.language.implicitConversions

class ProgramRunner(val myProgram: Program) {
    // todo: implement run

    def run() = {}
}

/** Companion object to convert a program into a ProgramRunner */
object ProgramRunner {

    implicit def program2ProgramRunner(myProgram: Program) = new ProgramRunner(myProgram)
}

object Piconot {

    //def state(name: StateName, state: State)

}
