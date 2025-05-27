#include <bits/stdc++.h>
using namespace std;

// Global start and end row
int sr, er;

// Output file stream
ofstream outfile;

// Print array from sr to er to file
void print_array(const vector<pair<int, string>>& vec) {
    outfile << "[";
    for (int i = sr; i <= er; ++i) {
        if (i != sr) outfile << ", ";
        outfile << vec[i].first << "/" << vec[i].second;
    }
    outfile << "]" << endl;
}

// Partition function
int partition(vector<pair<int, string>>& vec, int low, int high) {
    int pivot = vec[high].first;
    int i = (low - 1);
    for (int j = low; j <= high - 1; j++) {
        if (vec[j].first <= pivot) {
            i++;
            swap(vec[i], vec[j]);
        }
    }
    swap(vec[i + 1], vec[high]);
    return i + 1;
}

// Quick sort function (unchanged signature)
void quick_sort_step(vector<pair<int, string>>& vec, int low, int high) {
    if (low < high) {
        int pi = partition(vec, low, high);
        outfile << "pi=" << pi << " ";
        print_array(vec);
        quick_sort_step(vec, low, pi - 1);
        quick_sort_step(vec, pi + 1, high);
    }
}

int main() {
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

    // Parse CSV
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

  
    print_array(data);

  
    quick_sort_step(data, sr, er);

    outfile.close();
    return 0;
}
