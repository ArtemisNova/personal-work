/* ===========================================
Name: Film_Genie.c
Author: Scott Kelly
Version: 1.0
Description: C version of a film guessing game
==============================================
*/


/* This is a C program to simulate a film title guessing game.
 * It uses many functions passing values between them with the use of pointers.
 * It allows the user to guess a character and the total title of the film also.
 * More information on each function will be given below
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <stdbool.h>


void film(char films[50][50], char *stars, char *movie); //function to randomly choose film title and put it in separate array for use

void guess(char *movie, char *stars); //function to guide user to guessing functions, and count no. of guesses

void guesschar(char *movie, char *stars); //function to check if guessed character is in movie title

void guessfilm(char *movie, char *stars, int *guesses); //function to allow user to guess full film title

void changecase(char *string); //function to change movie title and guess into lower case for easier comparison

char playagain(char choice); //function to handle if the user wants to play again


int main(void){ //file reading is dealt with here and puts the file into an array
	
	char films[50][50], stars[50], c;
	char movie[50], choice = 'y';
	int i = 0, j = 0;
	FILE *fp;
	
	
	printf("********* WELCOME TO FILM GENIE!!!!!********\n\n");
	
	
	while(choice != 'n'){ //while the user wants to continue
	
	
		printf("\n\nThe film to guess is:\n");
		
		fp = fopen("filmtitles.txt", "r"); 
		if (fp == NULL){
			exit(EXIT_FAILURE); 
		}
		
		// This places all film titles into a 2D array so that it is easier to use
		while(c != EOF){
			c = getc(fp);
		
			if(c== '\n'){ //each time a film title ends
				films[i][j] = '\0';
				j = 0;
				i += 1;
			}
			else{
				films[i][j] = c;
				j++;
			}
		}
		fclose(fp);
	
		//calling the other main functions
		
		film(films, stars, movie); //random title is chosen
		guess(movie, stars); //guessing is done, and game ends
		choice = playagain(choice); //user is asked to play again, if not, loop ends
	}
	
	
	
	return 0;
}





void film(char films[50][50], char *stars, char *movie){ //function choosing random title
	int i = 0, rng;
	
	srand(time(NULL)); //Seeding RNG
	
	rng = rand() %50; //choosing random row in array
	strcpy(movie, films[rng]);
	
	changecase(movie); //change to lower case
	
	while(movie[i] != '\0'){ // loop creating array of asterix to represent title chosen
	
		if(movie[i] == ' '){
		stars[i] = ' ';
		}
		else{
			stars[i] = '*';
		}
		
		i++;
	}
	stars[i] = '\0';
	
	return;
}





void changecase(char *string){
	
	int i;
	
	while(i <= strlen(string)){
		
		if(string[i] > 64 && string[i] < 91){ //using ASCII numbers to change the case of letters
			string[i] += 32;
		}
		i++;
	}
	
	return;
}






void guess(char *movie, char *stars){
	
	char c;
	bool guess;
	int guesses = 5; //five guesses for movie titles
	
	
	while(guesses > 0){
		
		
		printf("%s", stars);
		
		printf("\n\nType 'c' to guess a character, and 'f' to guess the film!\n");
	
		guess = false; //resetting boolean operator each time
		
		while(!guess){ //while a valid guess has not been made
		
		c = getchar();
		fflush(stdin);
	
		if(c == 'c'){
			guesschar(movie, stars);
			guess = true; //valid guess made, loop ends
		}
		else if(c == 'f'){
				guessmovie(movie, stars, &guesses);
				guess = true; //valid guess made, loop ends	
		}
		else{
				printf("\nInvalid input!! Enter either c or f\n");
		}
		
		}
	}
			
	return;
}






void guesschar(char *movie, char *stars){
	
	int i;
	char c;
	bool guess;
	
	printf("\nPlease enter your character guess!!\n");
	
	c = getchar();
	fflush(stdin);
	
	if(c > 64 && c < 91){ //changing to lower case
		c += 32;
	}

	for(i = 0; i <= strlen(movie); i++){ //loop checking to see if character is in title
		if(movie[i] == c){
			stars[i] = c; //asterix replaced by letter
			guess = true;
		}
	}
	
	if(!guess){ // letter is not in title
		printf("\nLetter not in the film!!\n");
	}
	else{
		printf("\nWell done!!\n");
	}
	
	
	return;
}






void guessmovie(char *movie, char *stars, int *guesses){
	
	int i = 0, check;
	char guess[50], c;
	
	
	printf("\nYou have %d guesses remaining!!\n", *guesses);
	
	printf("\nPlease enter your guess!\n");

	
	while((c = getchar()) != '\n'){ //taking in guess to array
		guess[i] = c;
		i++;
	}
	guess[i] = '\0';
	
	changecase(guess); //changing to lower case
	
	check = strcmp(movie, guess); //strcmp returns 0 if they are the same string
	
	if(check != 0){ //if the strings do not match
		printf("\nIncorrect!! Try again ;) (check spelling :P)\n");
		
		if(*guesses == 1){
			printf("\nYou lost!!!! :(\n");
		}
	}
	if(check == 0){
		printf("\nYou got it!!!\n");
		
		if(*guesses == 5){
			printf("\n1st guess.... You are a film genie!!!\n");
		}
		if(*guesses > 1 && *guesses < 5){
			printf("\nWell done!!\n");
		}
		if(*guesses == 1){
			printf("\nLast guess... that was close!!\n");
		}
		
		*guesses = 1;
	}
	
	*guesses -= 1;
	
	return;
}





char playagain(char choice){
	
	printf("\nWould you like to play again? (y/n)\n");
	
	while(choice != 'y' || choice != 'n'){ //while a valid choice has not been made
		
		choice = getchar();
		fflush(stdin);
		
		
		if(choice == 'y'){ //user wants to continue
			system("cls");
			return choice;
		}
		else if(choice == 'n'){ //user doesn't want to continue
			printf("\nThank you for playing!!!\n");
			return choice; // choice = n; will cause loop in main to end
		}
		else{
			printf("\nInvalid input!! Enter y or n\n");
		}
	}
	
	return choice;	
}








