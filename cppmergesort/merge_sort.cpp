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

void merge(vector<Data>& arr, int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;

    vector<Data> L(n1), R(n2);

    for (int i = 0; i < n1; i++) L[i] = arr[l + i];
    for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

    int i = 0, j = 0, k = l;

    while (i < n1 && j < n2) {
        if (L[i].number <= R[j].number) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void mergeSort(vector<Data>& arr, int l, int r) {
    if (l < r) {
        int mid = (l + r) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }
}

vector<Data> loadCSV(string fileName) {
    ifstream file(fileName);
    string line;
    vector<Data> result;

    while (getline(file, line)) {
        stringstream ss(line);
        string numStr, text;
        getline(ss, numStr, ',');
        getline(ss, text);

        Data d;
        d.number = stoi(numStr);
        d.text = text;

        result.push_back(d);
    }

    file.close();
    return result;
}

void saveCSV(string fileName, const vector<Data>& arr) {
    ofstream out(fileName);
    for (int i = 0; i < arr.size(); i++) {
        out << arr[i].number << "," << arr[i].text << endl;
    }
    out.close();
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cout << "Usage: ./merge_sort <input_file>" << endl;
        return 1;
    }

    string fileName = argv[1];
    vector<Data> data = loadCSV(fileName);

    chrono::steady_clock::time_point start = chrono::steady_clock::now();
    mergeSort(data, 0, data.size() - 1);
    chrono::steady_clock::time_point end = chrono::steady_clock::now();

    // 改成容易确认的名字
    saveCSV("sorted_confirm.csv", data);

    chrono::duration<double> elapsed = end - start;
    cout << "Sorted output written to sorted_confirm.csv" << endl;
    cout << "Time taken: " << elapsed.count() << "s" << endl;

    return 0;
}

