# Cribbage
This is an implementation of the game of cribbage in Java. It is intended to be extensible and easy to maintain. It is currently being used as the backend for [Cribbage_Android](https://github.com/ryanramsdell27/Cribbage_Android), an app for Android.

## Project Structure
- A game object has two basic methods: `step` and  `isDone`. A game loop runs the step function until `isDone` returns true.
- The cribbage step function shuffles, deals, asks players to discard, then does the pegging phase in accordance to the standard rules of cribbage.
- Cards and decks are managed in the `Card` and `Deck` classes, which should be easy to reuse for other projects. 
- The `Hand` class is specific to the game of cribbage and includes the scoring mechanism for a hand via the `scoreHand` method. 
- Players must implement the abstract `Player` class or extend its derivatives, and are responsible for discarding and via `discard` and pegging via `peg`. 
## AI Players
There are currently three algorithms for artificial intelligence. They rely on the the `PowerSetIterator` and `CombinationIterator` for calculating possible scores
1. `CPUPlayerMax`: This style chooses the hand that is capable of producing the maximum score possible by considering all available starter cards and discards in the crib
2. `CPUPlayerMin`: This style discards cards into the crib based on what will give the guaranteed highest hand in the worst case scenario for the starter card
3. `CPUPlayerAvg`: This style choosing the optimal hand by considering every possible hand and picking what has the highest expected outcome statistically
