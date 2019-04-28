/*
 ============================================================================
 Name        : ListArray.c
 Author      : P‡draig Cunningham
 Version     :
 Copyright   : Your copyright notice
 Description : Some code for handling an array of lists
 ============================================================================
 */
#include <stdio.h>
#include <stdlib.h>

const int nlists = 5;

//structure that is used to build linked lists of integers
 struct node
 {
  int val;
  struct node *next;
} ;

 struct node* heads[5]; // array with the roots of the lists
 struct node* currs[5]; // array holding pointers to current positions in list


 //--------------------------------------
 //Functions for managing an array of lists.
 //Uses global arrays heads[] and currs[] for the list pointers.
 //
 //linking the head and current pointers for n lists in an array.
 void setup_list_array(int n){
 	int i;
 	for(i=0; i<n; i++){
 		heads[i] = NULL;
 		currs[i] = heads[i];
 	}
 }

 //initializing a list once it needs to be used.
 struct node* create_list(int val, int listNo){
     struct node *ptr = (struct node*)malloc(sizeof(struct node));

     ptr->val = val;
     ptr->next = NULL;

     heads[listNo] = currs[listNo] = ptr;
     return ptr;
 }

 //pushing an entry onto one of the lists in the array.
 struct node* add_to_list_in_array(int val, int listNo){
     if(NULL == heads[listNo]){
         return (create_list(val, listNo));
     }

     struct node *ptr = (struct node*)malloc(sizeof(struct node));

     ptr->val = val;
     ptr->next = NULL;
     currs[listNo]->next = ptr;
     currs[listNo] = ptr;

    return ptr;
 }

 // a function that will print one of the lists.
 void print_list(int listNo){
     struct node *ptr = heads[listNo];

     printf("\n -------Printing list No %d Start------- \n",listNo);
     while(ptr != NULL){
         printf("[%d] ",ptr->val);
         ptr = ptr->next;
     }

     return;
 }


int main(void) {
	int i = 0;
	setup_list_array( nlists);

// push some numbers onto the lists
	add_to_list_in_array(459,3);
	add_to_list_in_array(462,2);
	add_to_list_in_array(463,3);
	add_to_list_in_array(480,0);

// print the contents of all the lists to the console
	for (i = 0; i< nlists; i++){
			print_list(i);
		}
	return EXIT_SUCCESS;
}

