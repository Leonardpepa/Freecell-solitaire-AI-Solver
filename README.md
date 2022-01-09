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
run:
* windows: generator.exe <prefix> <id1> <id2>
* linux: ./generator.exe <prefix> <id1> <id2>
the reuslt will be generated files from id1 to id2 with the name given in prefix
sample execution:
```terminal
  gcc generator.c -o generator
  generator.exe test 1 1
```
sample results: test1.txt
 ```terminal
S8 H11 D12 D5 S1 D9 C3
D11 H1 H8 D6 S3 H6 S10
C8 C10 S5 H9 C0 D10 H5
S4 C2 S6 S2 D1 S9 D8
H0 H12 S12 D7 C11 D2
S11 C6 C1 H2 H7 C9
S7 D0 D4 C4 S0 H4
C7 C12 D3 H3 C5 H10 
```
