package com.rockthejvm.part1recap

object Variance:
  class Animal 
  class Dog(name: String) extends Animal 

  // for List: A <:< B => List[A] <:< List[B]
  val lassie = new Dog("Lassie")
  val anAnimal: Animal = lassie 
  val hachi = Dog("Hachi")

  val someAnimals: List[Animal] = List(lassie, hachi)

  class MyList[+A] // MyList is covariant in A 
  val myAnimalList: MyList[Animal] = new MyList[Dog]

  trait Semigroup[A] { 
    def combine(x: A, y: A): A 
  }

  // ALL generics in Java are INVARIANT 

  // HELL NO - CONTRAVARIANCE
  trait Vet[-A] { 
    def heal(animal: A): Boolean
  }

  val myVet: Vet[Dog] = new Vet[Animal] {
    override def heal(animal: Animal): Boolean = {
      println("Here you go...")
      true
    }
  }

  myVet.heal(lassie)

/*   class MyList2[+A] {
    def add(a: A): MyList2[A]
  }   */

  class MyList2[+A]:
    def add[B >: A](b: B): MyList2[B] = ??? 


end Variance