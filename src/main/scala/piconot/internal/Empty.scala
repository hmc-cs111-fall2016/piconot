package piconot.internal 

object Empty extends Internal("resources/empty.txt") {
  If ("N", "Nothing(N)","Go(North)")
  If ("N", "Something(N) and Nothing(W)", "Go(West)")
  If ("N", "Something(N, W)", "Go(East)")
  If ("W", "Nothing(S)", "Go(South)")
  If ("W", "Nothing(S)", "Go(South)")
  If ("S", "Nothing(S)", "Go(South)")
  If ("S", "Something(S)", "Go(North)")
  If ("E", "Nothing(E)", "Go(East)")
  If ("E", "Something(E)", "Go(South)")
  run
}
