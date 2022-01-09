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
compile: gcc generator.c -o generator <br/> 
run:
* windows generator.exe prefix id1 id2
* linux ./generator.exe prefix id1 id2
 
<br /> the reuslt will be generated files from id1 to id2 with the name given in prefix

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
# Instalation
```termianl
git clone https://github.com/Leonardpepa/Freecell-solitaire-AI-Solver.git
cd Freecell-solitaire-AI-Solver 
```
# Execution
run: java -jar method(breadth/depth/best/astar) inputfile outputfile
<br/>
reuslt: will generate a file with the number of moves needed to solve the problem and each move to new line
<br />
sample execution:
```terminal
java -jar astar test1.txt solution.txt
```
sample result: 
```terminal
94
source S0
freecell C6
freecell D6
source S1
freecell S12
source S2
freecell S4
source D0
stack C6 H7
freecell S9
source H0
stack D11 C12
stack S11 H12
stack H9 C10
source D1
source C0
source D2
stack D5 C6
source H1
stack H4 C5
newstack S11
stack S4 D5
freecell H12
stack H10 S11
source S3
source S4
stack S9 H10
freecell H6
newstack H12
freecell S6
stack C11 H12
stack D10 C11
source D3
newstack C8
stack S8 H9
source D4
source D5
source D6
freecell S7
source C1
source D7
stack H5 C6
newstack S12
stack D11 S12
freecell C12
source S5
source C2
source S6
source S7
source S8
source S9
stack C9 D10
freecell H11
source H2
newstack H4
freecell C5
stack C8 H9
newstack D9
source D8
source H3
source H4
source H5
source H6
source D9
newstack C6
source H7
newstack C7
source S10
stack C9 H10
source D10
newstack C8
source D11
newstack H9
freecell C10
source H8
source H9
newstack C9
source H10
source S11
source H11
source D12
newstack C4
source C3
source C4
source C5
source C6
source C7
source C8
source C9
source C10
source C11
source S12
source H12
source C12


```


