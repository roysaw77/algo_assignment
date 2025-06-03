#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>
#include <chrono>
using namespace std;

struct Data {
    int number;
    string text;
};

vector<Data> readData(string fileName) {
    ifstream file(fileName);
    vector<Data> list;
    string line;
    while (getline(file, line)) {
        stringstream ss(line);
        string numStr, txt;
        getline(ss, numStr, ',');
        getline(ss, txt);
        Data temp;
        temp.number = stoi(numStr);
        temp.text = txt;
        list.push_back(temp);
    }
    return list;
}

int binarySearch(const vector<Data>& arr, int target) {
    int left = 0, right = arr.size() - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid].number == target)
            return mid;
        else if (arr[mid].number < target)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return -1;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cout << "Usage: ./binary_search <input_file.csv>" << endl;
        return 1;
    }

    string inputFile = argv[1];
    vector<Data> dataset = readData(inputFile);
    int total = dataset.size();

    int bestCase = dataset[total / 2].number;
    int averageCase = dataset[total / 4].number;
    int worstCase = 999999999; // 不存在的数

    chrono::high_resolution_clock::time_point start, end;
    chrono::duration<double> tBest, tAvg, tWorst;

    start = chrono::high_resolution_clock::now();
    binarySearch(dataset, bestCase);
    end = chrono::high_resolution_clock::now();
    tBest = end - start;

    start = chrono::high_resolution_clock::now();
    binarySearch(dataset, averageCase);
    end = chrono::high_resolution_clock::now();
    tAvg = end - start;

    start = chrono::high_resolution_clock::now();
    binarySearch(dataset, worstCase);
    end = chrono::high_resolution_clock::now();
    tWorst = end - start;

    string outFile = "binary_search_" + to_string(total) + ".txt";
    ofstream out(outFile);
    out << "Best case search (middle): " << bestCase << endl;
    out << "Time: " << tBest.count() << " seconds" << endl;

    out << "\nAverage case search (quarter): " << averageCase << endl;
    out << "Time: " << tAvg.count() << " seconds" << endl;

    out << "\nWorst case search (not found): " << worstCase << endl;
    out << "Time: " << tWorst.count() << " seconds" << endl;

    out.close();
    cout << "Results written to: " << outFile << endl;
    return 0;
}
