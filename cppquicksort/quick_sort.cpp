#include <bits/stdc++.h>
using namespace std;
using namespace chrono;

int sr, er;
ofstream outfile;

void print_array(const vector<pair<int, string>>& vec) {
    outfile << "[";
    for (int i = sr; i <= er; ++i) {
        if (i != sr) outfile << ", ";
        outfile << vec[i].first << "/" << vec[i].second;
    }
    outfile << "]" << endl;
}

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

void quick_sort(vector<pair<int, string>>& vec, int low, int high) {
    if (low < high) {
        int pi = partition(vec, low, high);
        quick_sort(vec, low, pi - 1);
        quick_sort(vec, pi + 1, high);
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

    string filename = "quick_sort" + to_string(sr) + "_" + to_string(er) + ".txt";
    outfile.open(filename);
    if (!outfile.is_open()) {
        cerr << "Error opening output file!" << endl;
        return 1;
    }

    // Start timing
    auto start = high_resolution_clock::now();

    quick_sort(data, sr, er);

    // End timing
    auto end = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(end - start);

    print_array(data);
    outfile << "Runtime: " << duration.count() << " microseconds" << endl;

    outfile.close();
    return 0;
}
