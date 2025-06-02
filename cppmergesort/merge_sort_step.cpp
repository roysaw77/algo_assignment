#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

struct Data {
    int number;
    string text;
};

vector<string> stepList;

void recordStep(const vector<Data>& arr) {
    string line;
    for (int i = 0; i < arr.size(); ++i) {
        line += to_string(arr[i].number) + "/" + arr[i].text;
        if (i != arr.size() - 1) line += ", ";
    }
    stepList.push_back(line);
}

void merge(vector<Data>& arr, int l, int m, int r) {
    int len1 = m - l + 1;
    int len2 = r - m;
    vector<Data> L(len1), R(len2);

    for (int i = 0; i < len1; i++) L[i] = arr[l + i];
    for (int i = 0; i < len2; i++) R[i] = arr[m + 1 + i];

    int i = 0, j = 0, k = l;
    while (i < len1 && j < len2) {
        if (L[i].number <= R[j].number) {
            arr[k++] = L[i++];
        } else {
            arr[k++] = R[j++];
        }
    }

    while (i < len1) arr[k++] = L[i++];
    while (j < len2) arr[k++] = R[j++];

    recordStep(arr); // after merge
}

void mergeSort(vector<Data>& arr, int l, int r) {
    if (l < r) {
        int mid = (l + r) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }
}

vector<Data> readSelectedRows(string fileName, int from, int to) {
    ifstream file(fileName);
    vector<Data> list;
    string line;
    int count = 0;

    while (getline(file, line)) {
        if (count + 1 >= from && count + 1 <= to) {
            stringstream ss(line);
            string numStr, txt;
            getline(ss, numStr, ',');
            getline(ss, txt);
            Data row;
            row.number = stoi(numStr);
            row.text = txt;
            list.push_back(row);
        }
        count++;
        if (count >= to) break;
    }

    file.close();
    return list;
}

int main(int argc, char* argv[]) {
    if (argc != 4) {
        cout << "Usage: ./merge_sort_step <input_file> <start_row> <end_row>" << endl;
        return 1;
    }

    string inputFile = argv[1];
    int startRow = stoi(argv[2]);
    int endRow = stoi(argv[3]);

    vector<Data> records = readSelectedRows(inputFile, startRow, endRow);

    recordStep(records); // log the initial state
    mergeSort(records, 0, records.size() - 1);

    ofstream out("merge_step_output.txt");
    for (int i = 0; i < stepList.size(); i++) {
        out << stepList[i] << endl;
    }
    out.close();

    cout << "Steps written to merge_step_output.txt" << endl;
    return 0;
}


// g++ merge_sort_step.cpp -o merge_sort_step
// ./merge_sort_step dataset_sample_1000.csv 1 7