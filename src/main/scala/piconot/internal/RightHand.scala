package piconot.internal 

import scala.language.postfixOps

object RightHand extends Internal("maze.txt") {
  If ("N", "Nothing(N) and Something(E)","Go(North)")
  If ("N", "Nothing(E)", "Go(East)")
  If ("N", "Something(N, E, W)", "Go(South)")
  If ("N", "Something(N, E)", "Go(West)")
  
  If ("W", "Nothing(W, N)", "Go(West)")
  If ("W", "Nothing(N)", "Go(North)")
  If ("W", "Something(N, E, S)", "Go(East)")
  If ("W", "Something(W, N)", "Go(South)")

  If ("S", "Nothing(S) and Something(W)", "Go(South)")
  If ("S", "Nothing(W)", "Go(West)")
  If ("S", "Something(S, W, E)", "Go(North)")
  If ("S", "Something(S, W)", "Go(East)")

  If ("E", "Nothing(E) and Something(S)", "Go(East)")
  If ("E", "Nothing(S)", "Go(South)")
  If ("E", "Something(E, S, N)", "Go(West)")
  If ("E", "Something(E, S)", "Go(North)")
  run()
}
