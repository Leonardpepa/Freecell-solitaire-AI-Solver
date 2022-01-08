// -------------------------------------------------------------
//
// This program creates instances of freecell solitaire problems 
// Instances are created radomly, however a check is performed
// after creation in order to ensure that the instance is solvable.
// Non-solvable instances are ignored.
// Instances are named from <prefix><id1>.txt to <prefix><id2>.txt .
//
// Syntax:
// generator <prefix> <id1> <id2>
//
// Author: Ioannis Refanidis, March 2010
//         
// --------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define N 13
#define HEARTS 0
#define DIAMONDS 1
#define SPADES 2
#define CLUBS 3

struct card
{
	int suit;
	int value;
};

struct card deck[4*N];

void initialize_deck()
{
	int i, j;
	for(i=0;i<4;i++)
		for(j=0;j<N;j++)
		{
			deck[i*N+j].suit=i;
			deck[i*N+j].value=j;
		}
}

// This function creates radomly a new puzzle.
void shuffle_deck(int K)
{
	long i;
	int x, y;
	for(i=0;i<K;i++)
	{
		x = (double) rand() / (RAND_MAX + 1) * (4*N);
		y = (double) rand() / (RAND_MAX + 1) * (4*N);
		{
		int ts=deck[x].suit;
		int tv=deck[x].value;
		deck[x].suit=deck[y].suit;
		deck[x].value=deck[y].value;
		deck[y].suit=ts;
		deck[y].value=tv;
		}
	}
}
		

// Auxiliary function that displays a message in case of wrong input parameters.
void syntax_message()
{
	printf("Use syntax:\n\n");
	printf("\tgenerator <prefix> <id1> <id2>\n\n");
	printf("where:\n ");
	printf("\t<prefix> = the prefix of the filename of the instances to be generated\n");
	printf("\t<id1> = a number indicating the suffix of the first instance.\n");
	printf("\t<id2> = a number indicating the suffix of the last instance.\n\n");
	printf("e.g. the call \n\n\tgenerator test 1 10\n\ngenerates 10 instances with names ranging from test1.txt to test10.txt.\n");
	printf("Constraints: id1>0, id2>0, id1<=id2.");
}


void number2string(int n,char* s)
{
	int i=0,j;
	while (n>0)
	{
		// shift one position to the right
		for(j=i-1;j>=0;j--)
			s[j+1]=s[j];
		s[0]=(char) (n % 10)+'0';
		n=n / 10;
		i++;
	}
	s[i]='\0';
}

// This function writes the puzzle to a file
void write_to_file(int id, char *filename)
{
	int i;
	char *temp=(char*) malloc(10*sizeof(char));
	FILE *fout;
	char s[255];
	strcpy(s,filename);
	
	number2string(id,temp);
	strcat(s,temp);
	strcat(s,".txt");

	fout=fopen(s,"w");
	
	for(i=0;i<4*N;i++)
	{
		switch(deck[i].suit)
		{
		case HEARTS: fprintf(fout,"H");
			break;
		case DIAMONDS: fprintf(fout,"D");
			break;
		case CLUBS: fprintf(fout,"C");
			break;
		case SPADES: fprintf(fout,"S");
			break;
		}

		fprintf(fout,"%d",deck[i].value);

		if ( (N%2) == 0)
		{
			if (i == N/2-1 || i==N-1 || i==3*N/2-1 || i==2*N-1
				|| i==5*N/2-1 || i==3*N-1 || i==7*N/2-1 || i==4*N-1)
				fprintf(fout, "\n");
			else
				fprintf(fout, " ");
		}
		else
		{
			if (i == N/2 || i==N || i==3*N/2+1 || i==2*N+1
				|| i==5*N/2+1 || i==3*N || i==7*N/2 || i==4*N)
				fprintf(fout, "\n");
			else
				fprintf(fout, " ");
		}
	}
	fclose(fout);
}

int main(int argc, char** argv)
{
	long id1,id2, i;
	char* endPtr;

	// Seed the random-number generator with current time so that
	// the numbers will be different every time we run.
	
	srand( (unsigned) time ( NULL ) );
	
	if (argc!=4)
	{
		printf("Wrong number of arguments. Use correct syntax:\n");
		syntax_message();
		return -1;
	}

	id1=strtol(argv[2],&endPtr,10);
	id2=strtol(argv[3],&endPtr,10);
	if (id1<=0 || id2<=0 || id1>id2)
	{
		syntax_message();
		return -1;
	}

	for(i=id1;i<=id2;i++)
	{
		initialize_deck();
		shuffle_deck(400*N);
		write_to_file(i,argv[1]);
	}

	return 0;
}
