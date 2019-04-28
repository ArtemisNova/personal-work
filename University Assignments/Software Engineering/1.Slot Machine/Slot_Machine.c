/*
 ============================================================================
 Name        : Slot_Machine.c
 Author      : Scott Kelly
 Version     : 1.0
 Copyright   : Your copyright notice
 Description : A program to simulate the actions of a slot machine
 ============================================================================
 */

/* Program written by Scott Kelly
 * Function to simulate the actions of a slot machine
 * it uses four functions; "main" to handle shifting values around in the functions, "bets" to take in the bet value, and check that it's valid
 * "slotprint", which handles printing out each fruit slot, using switch statements, and "calc", which keeps track of your total winnings,
 * and also how many credits you have. all of these take in and return "money" structures, because only the value of the bet and the 
 * current credits and net winnings has to be passed between functions. For more detail see specific comments below
 */

#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <stdlib.h>
#include <conio.h>


typedef struct {
	int fruit; // holds value of the RNG (Apple = 0, Orange = 1, Pear = 2)
} column;

typedef struct{ // used to hold the fruit outputted for each 
	column one;
	column two;
	column three;
} slot;

typedef struct{ // used to handle the money aspect of the game
	int bet;
	int credits;
	int net_winnings;
} money;


money slotprint(money z); // function for printing out the fruit in each column, line 150

money bets(money y); // function for taking in and handling the user's bet, line 118

money calc(money p, slot play); //function for adding or deducting credits depending on if the user lost or won, line 211



int main(void){
	
	money x; // struct which will be passed through the functions
	char c; // used to store Y or N when the user is asked
	bool input = true, play = true; // boolean operators to only allow loops to continue in certain situations
	
	printf("\n\n");
	printf("********** Welcome to the Fantastic **********\n");
	printf("**********       Spectacular        **********\n");
	printf("**********     Slot Machine!!!!!    **********\n"); // fabulous printfs
	
	x.credits = 10; // starting money
	x.net_winnings = 0; // initialising to zero so it can be used later without error
	
	printf("\n\nYou have %d credits to start!!\n\n", x.credits);
	
	while(play){ // loop stops if there's not enough money or the user ends it
	
		if(input){ // only run if the user wants it to; not run if wrong character inputted below
			x = bets(x);
			x = slotprint(x); 
		}
	
		printf("You have %d credits.\n", x.credits);
	
		printf("Do you want to play again? Y/N\n");
		c = getch(); // used instead of getchar() so that enter does not have to be pressed after entering character
		
	
		if(c == 'y'){ // user wants to continue
			if(x.credits < 2){ // not enough credits to continue
			
				printf("\n\nNot enough credits to play again!!!!!\n");
				printf("total lost = %d\n\n", x.net_winnings);
				printf("**********BYE!!!!!**********\n");
				play = false; // ends loop
			}
			else{
				input = true; // allows other functions to be called again
			}
		}
		
			
		if(c == 'n'){ // user wants to exit
			
			if(x.net_winnings < 0)
				printf("\n\nTotal lost = %d\n", x.net_winnings);
			if(x.net_winnings > 0)
				printf("\n\nTotal won = %d\n", x.net_winnings);
				
				
			printf("\n**********Thank you for playing!!!!**********\n");
			play = false; // ends loop
		}
		
		if(c != 'y' && c != 'n'){ // wrong character inputted
				printf("\nInvalid input!!! please enter either Y or N.\n");
				input= false; // stops functions above being called again if wrong character entered
		}
	}
	
	return 0;
}




money bets(money y){
	
	bool bet;

	printf("\nHow much would you like to bet?\n");
	
	while(!bet){ // loop only ends when valid bet is entered
	
	   /* Here, scanf = 1 when it succeeds, so if it doesn't,
		* it returns 0; for example, if a character is entered in 
		* then scanf fails and it loops back to the inputting
		*/	
		
		while((scanf("%d", &y.bet)) != 1){
			while(getchar() != '\n')
				printf("Please only enter numbers into this program.\n");
		}
	
	
		if(y.bet < 2 || y.bet > y.credits){
			printf("\nInvalid input!!\n");
			printf("Please enter a value greater than 2 and less than your credits.\n");
		}
		else
			bet = true;
	}	
	return y;
} 




money slotprint(money z){
	
	slot play;
	
	srand(time(0)); // seeding the RNG in relation to time
	
	play.one.fruit = rand() %3;   
	play.two.fruit = rand() %3;
	play.three.fruit = rand() %3;
	
	printf("\n\n");
	printf("||");
	
	/* Here switch statements are used to print out the appropriate fruit depending on the RNG
	 * apple = 0; orange = 1; pear = 2;
	 */
	switch(play.one.fruit){ // first column
		case 0:
			printf(" APPLE ||");
			break;
		case 1:
			printf(" ORANGE ||");
			break;
		case 2:
			printf(" PEAR ||");
			break;
	}
		
	switch(play.two.fruit){ // second column
		case 0:
			printf(" APPLE ||");
			break;
		case 1:
			printf(" ORANGE ||");
			break;
		case 2:
			printf(" PEAR ||");
			break;
	}
	
	switch(play.three.fruit){ //third column
		case 0:
			printf(" APPLE ||");
			break;
		case 1:
			printf(" ORANGE ||");
			break;
		case 2:
			printf(" PEAR ||");
			break;
	}
	
	z = calc(z, play); // loss or win is put into z
	
	return z; // new credit values returned to struct x in main
	
}




money calc(money p, slot play){
	
	int a, b, c; // used to replace long variables in if statements, tidies it up!
	
	a = play.one.fruit;   // assigning values from struct here
	b = play.two.fruit;   
	c = play.three.fruit;
	
	if(a == b && a == c){ // if each of the RNG's results are the same
	
		printf("\n\nFull house!!!!\n");
		printf("\nYou win %d credits!!!!\n", p.bet);
		p.credits += p.bet;
		p.net_winnings += p.bet;
	}
	
	else if(a != b && b != c && a != c){ //if none are the same
	
			printf("\n\nEmpty house :(\n");
			printf("You lose %d credits...\n", p.bet);
			p.credits -= p.bet;
			p.net_winnings -= p.bet;
	}
	else{ // the last possibility; any variation of an empty house
	
		printf("\n\nHalf House.\n");
		printf("You win %d credits.\n", p.bet / 2);
		p.credits += (p.bet / 2);
		p.net_winnings += (p.bet / 2);
	}
		
	return p; // returning new credit values to z
}
