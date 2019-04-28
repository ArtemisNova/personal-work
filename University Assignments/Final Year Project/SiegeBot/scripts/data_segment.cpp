#include <iostream>
#include <string>
#include <fstream>

using namespace std;

bool verify_string(string line, string filter, int index);

/* Function to filter the data file by specified string input
 * @param: file; pointer to file, filter; string to filter by
 * index; index of the parameter to filter by each row
 */
void get_data_segment(ifstream& file, string filter, int index){
	string line;
	string output_name = "../src/Resources/" + filter + "_output.csv";
	ofstream output (output_name.c_str(), ofstream::out);
	bool match = false;
	int numlines = 10000000; // sample size
	int count = 0;
	
	while(file.peek() != EOF && numlines != count){
		getline(file, line);
		match = verify_string(line, filter, index);
		
		if(match){
			output << line;
			count++;
		}
	}
	
	cout << "Gathering finished! Number of lines written to file: " << count << endl;
	output.close();
	return;
}

/* Function to verify if a line from the file matches the filter
 * indexes are different separations of the string, eg index 0 id the date
 */
bool verify_string(string line, string filter, int index){
	string delimiter = ";";
	string current = "";
	int i = 0;
	int delim_index;
	
	// finds delimiter in string and gets the indexed statement
	do{
		delim_index = line.find(delimiter) + 1;
		current = line.substr(0, delim_index);
		line = line.erase(0, delim_index);
	}while(i++ < index);
	
	// remove delimiter from phrase
	current = current.erase(current.length()-1, current.length()); 
	
	if(current == filter){
		return true;
	}
	else{
		return false;
	}
} 

int main(void){
	string data_file;
	string filter;
	int index = -1;
	string line;
	bool file_found = false;
	bool filter_chosen = false;
	bool filter_entered = false;
	ifstream file;
	
	//printf("Please enter the data file name, located in the resources folder: ");
	printf("Please enter the data file path: ");
	while(!file_found){
		cin >> data_file;
		data_file = /*"../src/Resources/" + */  data_file;
		new (&file) ifstream(data_file.c_str(), ifstream::in);
		
		if(file == NULL){
			printf("File not Found, please try again\n");
		}
		else{
			file_found = true;
		}
	}
	
	printf("Please choose which field to filter by: Enter a number\n"
		"0. Date\n"
		"1. Platform\n"
		"2. Game Mode\n"
		"3. Map\n"
		"4. Objective Location\n"
		"5. Skill Rank\n");
		
	while(!filter_chosen){
		cin >> index;
		if(index == -1){
			printf("Invalid input, please try again\n");
			cin.clear();
		}
		else if(index < 0 || index > 5){
			printf("Index out of range; Please try again\n");
		}
		else {
			filter_chosen = true;
		}
		
	}
	
	cin.clear();
	cin.ignore();
	cin.sync();
	
	printf("Please enter the text you want to filter by: ");
	getline(cin, filter);
	
	// remove column headers
	getline(file, line);
	cout << filter << endl;
	
	get_data_segment(file, filter, index);
	
	file.close();
	return 0;
}
