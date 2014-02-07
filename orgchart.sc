/*
 * Many functional languages, especially those in the ML family, include
 * algebraic data types (discriminated union types), which can be
 * recursive. These types provide an effective, light-weight mechanism
 * for creating tree-like structures of arbitrary depth. Behavior can be
 * added easily in the form of recursive functions that use pattern
 * matching for branching on the structure of the argument. These types
 * are closed in the sense that it is not possible to add new variants to
 * the type.

 * By contrast, in mainstream object-oriented languages, one would
 * typically create an interface to represent the type and a class for
 * each variant of the type, using the Composite and Decorator patterns
 * for structure and, sometimes, the Visitor pattern for
 * behavior. Without the visitor, it is very easy to add more variants;
 * with the visitor, this is still possible but harder.
 *
 * This discussion provides more background on this trade-off.
 *
 * Organizational charts (org charts) are a simple, well understood
 * example of a hierarchical structure. The following example explores
 * how we can represent and work with org charts in F#.
 *
 * In this example, an org chart is either a person (a leaf in the
 * resulting tree) or an organizational unit (an interior node), which
 * has zero or more org charts as children.
 *
 * F# version at http://laufer.cs.luc.edu/teaching/372/handouts/orgchart
 */

/**
 * data Node = P(name: String) | OU(name: String, children: List[Node])
 */
sealed trait Node
case class P(name: String) extends Node
case class OU(name: String, children: Node*) extends Node

val p = P("George")
assert { p.name == "George" }

val cs =   OU("CS",   P("Sekharan"), P("Rom"), P("Thiruvathukal"))
val math = OU("Math", P("Jensen"), P("Doty"), P("Giaquinto"))
val cas =  OU("CAS",  P("Andress"), P("Andrade"), cs, math )
val luc =  OU("luc",  cas)
/*
 * Now we will define a size function on org charts. If the org chart is
 * a person, then the size is one. Else we compute the size recursively
 * from the list of children, orgs. (Each item in this list can be a
 * person or OU, but this distinction will be made in the recursive
 * application of the function to each child.) Now we apply the size
 * function recursively to each child of orgs to find out the size of
 * each child. Instead of explicit loops, we use List.map to apply a
 * function to each item in a list and List.fold for adding up the
 * results. (These functions are discussed in a separate handout in more
 * detail.)
 */

def size(o: Node): Int = o match {
  case P(_) => 1
  case OU(_, children @ _*) => children.map(size).sum
}

assert { size(p) == 1 }
assert { size(cs) == 3 }
assert { size(luc) == 8 }

def depth(o: Node): Int = o match {
  case P(_) => 1
  case OU(_, children) => ??? // TODO
}

assert { depth(p) == 1 }
assert { depth(cs) == 2 }
assert { depth(luc) == 4 }

// TODO convert these functions into methods
