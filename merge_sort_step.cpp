#include <bits/stdc++.h>
using namespace std;


int main() {
    ifstream file("dataset_sample_1000 (1).csv"); // Replace with your CSV filename
    string line;

    while (getline(file, line)) {
        stringstream ss(line);
        string cell;
        vector<string> row;

        while (getline(ss, cell, ',')) {
            row.push_back(cell);
        }
        sort(row.begin(), row.end()); 
        // Print the row for demonstration
        for (const string& value : row) {
            cout << value << " ";
        }
        cout << endl;
    }

    file.close();
    return 0;
}

