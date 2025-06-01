#include <bits/stdc++.h>
using namespace std;


extern int sr, er;


extern ofstream outfile;

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


void quick_sort_step(vector<pair<int, string>>& vec, int low, int high) {
    if (low < high) {
        int pi = partition(vec, low, high);
        if(pi!=er){
        outfile << "pi=" << pi << " ";
        }
        print_array(vec);
        quick_sort_step(vec, low, pi - 1);
        quick_sort_step(vec, pi + 1, high);
    }
}


