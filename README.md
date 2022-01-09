# Freecell-solitaire-AI-Solver

# Project for Artificial Intelligence course from University of Macedonia
# Instructor Ioannis Refanidis

# Purpose
this project is created to solve the well known game Freecell solitaire by using famous path finding algorithms

# Algorthims use

* Breadth First Search
* Depth first Search
* Best Frist Search
* A* Search

# Generator
along with the project a c program in provided by the instuctor to generate random decks of size N

# How to use the Generator
compile: gcc generator.c -o generator
run: * windows: generator.exe <prefix> <id1> <id2>
     * linux: ./generator.exe <prefix> <id1> <id2>
the reuslt will be generated files from id1 to id2 with the name given in prefix
sample execution:
```terminal
  gcc generator.c -o generator
  generator.exe test 1 1
```
sample results:
 ```terminal
D0 D8 H7 H9 D2 H3
S7 C8 C1 S1 C2 S0
H4 D10 C10 H8 S9 S5
D7 D3 H2 C5 C4 S6
S4 D4 H6 H1 C0
D6 D1 H5 D9 S10
H10 S3 H0 C6 D5
C7 C9 S2 C3 S8 
```
