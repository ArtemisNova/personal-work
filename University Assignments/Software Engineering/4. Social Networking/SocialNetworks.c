/*
===================================================
Name: SocialNetwork.c
Author: Scott Kelly
Version: 1.0
Description

===================================================
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// structure to hold each character's
// name, and what lines their name
// occurs on

struct NameList {
	char name[50];
	int line_nos[100000];
	int counter;
	struct NameList * next;
} *head, *curr; // two struct pointers to navigate through the linked list

typedef struct NameList NameList;


const char * NAME_FILE_PATH = "./inputFiles/Les-Mis-Names.txt"; // pointing at the list of names

const char * TEXT_FILE_PATH ="./inputFiles/Les-Mis-full-text.txt"; // pointing at the full text of the book
//const char * TEXT_FILE_PATH ="./inputFiles/Les-Mis-Vol-1.txt";
//const char * TEXT_FILE_PATH ="./inputFiles/Les-Mis-4lines.txt";

const char * OUTPUT = "./outputFiles/output.csv"; // pointing at the file to print to for gephi

void setup_list(void);

void create_list(void);

void add_to_list(char * temp);

void check_line(char string[10000], int line);

void get_names(void);

void set_counter(void);

void get_occurence(void);

void find_links(void);


// main is only used to call other functions
int main(void){
	
	setup_list();
	get_names();
	get_occurence();
	find_links();
	
	return 0;
}



// function that starts the list
void setup_list(void){
	
	head = NULL;
	
	create_list();
	
	return;
}



// this function creates "head" and sets up curr for use later 
void create_list(void){
	
	head = (NameList *) malloc(sizeof(NameList));
	if(head == NULL){
		printf("head creation failed (%s)\n", __func__);
		exit(EXIT_FAILURE);
	}
	
	head->next = NULL;
	curr = head;
	
	return;
}



// function takes in the names and places them in their separate linked lists 
// it calls add_to_list to add a new node for each name
void get_names(void){
	
	FILE *fp = fopen(NAME_FILE_PATH, "r"); // the list of names
	
	if(fp == NULL){
		printf("File Not found (%s)\n", __func__);
		exit(EXIT_FAILURE);
	}
	char temp[50], ch = 0;
	int i = 0, j = 0;
	
		
	while(ch != EOF){
		
		ch = getc(fp);
		
		if(ch == '\n'){ // end of a name is reached 
		
			temp[i] = '\0';
			add_to_list(temp);
			i = 0;
			fflush(stdin);
		}
		else{
			temp[i++] = ch;
		}
	}
	set_counter();

	return;
}



// function that sets up counter in each node so that they can be used later
// counter counts the number of occurrences for a particular name
void set_counter(void){
	
	curr = head;
	
	while(curr != NULL){
		curr->counter = 0;
		curr = curr->next;
	}
	return;
}



// function that adds new nodes to the linked list when needed
// it also puts the name into the new node, as the only reason
// a new node is created is for a new name
void add_to_list(char * temp){
	
	strcpy(curr->name, temp);
	
	curr->next = (NameList *) malloc(sizeof(NameList));
	
	if(curr->next == NULL){
		printf("Node creation failed (%s)\n", __func__);
		exit(EXIT_FAILURE);
	}
	
	curr = curr->next;
	curr->next = NULL;
	
	return;
}



// function which takes in each line of the text
// so that it can be scanned through to see if there
// are any names in the line
void get_occurence(void){
	
	FILE * fp;
	char textLine[10000], ch = 0;
	int i = 0;
	int j = 1; // starts at 1 so that it is the 1st line
	
	fp = fopen(TEXT_FILE_PATH, "r");
	if(fp == NULL){
		printf("File Not found (%s)\n", __func__);
		exit(EXIT_FAILURE);
	}
	
	while(ch != EOF){
		
		ch = getc(fp); // taking in character from file
		
		if(ch == '\n'){ // line of text is finished
			
			textLine[i] = '\0';
			check_line(textLine, j); // passed to here so it can be checked for names
			j++;
			i = 0;
		}
		else{
			textLine[i++] = ch;
		}
	}
	
	return;
}



// function which checks a given line to see
// if any of the names occurs in the line
void check_line(char string[10000], int line){
	
	curr = head;
	char * check;
	
	while(curr != NULL){

		check = NULL;
		check = strstr(string, curr->name); // returns a null pointer if there is no occurence
		
		if(check != NULL){ // if a name does occur
			
			curr->line_nos[curr->counter++] = line;
		}
		
		curr = curr->next; // running through the list
	}
	
	return;
}



// function that compares line numbers between characters to 
// see if they occur within five lines of each other. If they
// do, a link is  found and it prints it to the csv file
// it doesn't compare character names with ones above it 
// because that would double count a link
void find_links(void){
	
	FILE * fp;
	int i, j, x;
	NameList * ptr; // pointer used to run through the list and compare 
	
	fp = fopen(OUTPUT, "w");
	if(fp == NULL){
		printf("error finding file!\n");
		exit(EXIT_FAILURE);
	}
	
	curr = head;
	
	while(curr->next != NULL){ // while the end of the list is not reached

		ptr = curr->next; // set to the next node so it can be compared
		
		for(i = 0; i < curr->counter; i++){ // running through each occurence of the first character
			
			while(ptr->next != NULL){ // while there are still characters to compare 
			
				for(j = 0; j < ptr->counter; j++){ // running through each occurence of the second character
				
					x = curr->line_nos[i] - ptr->line_nos[j]; // comparing the two lines
					
					if(x >= -5 && x <= 5){ // if they are within five lines of each other
						fprintf(fp, "%s, %s\n", curr->name, ptr->name); // print to file
					}
				}
			
				ptr = ptr->next;
			}
		
			ptr = curr->next; // resetting pointer to the "start" of the list
		}
		
		curr = curr->next; // running through the list after one node has been fully checked
	}	
	
	return;
}




