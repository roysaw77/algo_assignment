#include <bits/stdc++.h>
#include "quick_sort_step.h"
#include "binary_search_step.h"
using namespace std;

ofstream outfile;
int sr, er;

int main(){
     ifstream file("dataset_sample_1000.csv");
    if (!file.is_open()) {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    string line;
    vector<pair<int, string>> data;
    

    cout << "Enter start row and end row (0-based index): ";
    while (true) {
        cin >> sr >> er;
        if (sr >= 0 && er >= sr) break;
        cout << "Invalid input. Please enter valid start and end rows: ";
    }

 
    while (getline(file, line)) {
        stringstream ss(line);
        string num_str, str_val;
        if (getline(ss, num_str, ',') && getline(ss, str_val)) {
            try {
                int num = stoi(num_str);
                data.push_back({num, str_val});
            } catch (...) {}
        }
    }
    file.close();

   
    string filename = "quick_sort_step_" + to_string(sr) + "_" + to_string(er) + ".txt";
    outfile.open(filename);
    if (!outfile.is_open()) {
        cerr << "Error opening output file!" << endl;
        return 1;
    }

    quick_sort_step(data, sr, er);

    outfile.close(); 

    int target;
    cout << "Enter target value to search: ";
    cin >> target;

    string filename2 = "binary_search_step_" + to_string(target) + ".txt";
    outfile.open(filename2);
    if (!outfile.is_open()) {
        cerr << "Error opening output file!" << endl;
        return 1;
    }
    binary_search_step(data, target);
    outfile.close();
    return 0;
}