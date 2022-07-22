package com.rockthejvm.part1recap

object ContextualAbstractions:
  given defaultAmount: Int = 10
  def increment(x: Int)(using amount: Int): Int = x + amount

  trait Combiner[A] {
    def combine(x: A, y: A): A
    def empty: A
  }

  def combineAll[A](values: List[A])(using combiner: Combiner[A]): A =
    values.foldLeft(combiner.empty)(combiner.combine)

  given intCombiner: Combiner[Int] with {
    override def combine(x: Int, y: Int): Int = x + y
    override def empty: Int = 0
  }

  given stringCombiner: Combiner[String] with {
    override def combine(x: String, y: String): String = x ++ y
    override def empty: String = " "
  }

  // deriving more givens
  given optionCombiner[T](using combiner: Combiner[T]): Combiner[Option[T]]
    with {
    override def combine(x: Option[T], y: Option[T]): Option[T] =
      for {
        x_ <- x
        y_ <- y
      } yield combiner.combine(x_, y_)
    override def empty: Option[T] = Some(combiner.empty)
  }

  val numbers = (1 to 10).toList
  val numberOptions: List[Option[Int]] = List(Some(1), Some(2), Some(3), None)

  @main def contextualAbstractions() =
    println(increment(20))
    println(combineAll(numbers)) // intCombiner
    println(combineAll(List("cats", "scala")))
    println(combineAll(numberOptions))
