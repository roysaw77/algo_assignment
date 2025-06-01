#include <bits/stdc++.h>
using namespace std;

extern int sr, er;
extern ofstream outfile;



void binary_search_step(vector<pair<int, string>>& vec, int target) {
    int l = sr, r = er;
    while (l <= r) {
        int mid = l + (r - l) / 2;
        outfile << mid << ":" << vec[mid].first << "/" << vec[mid].second << endl;
        if (vec[mid].first == target) {
            return;
        } else if (vec[mid].first < target) {
            l = mid + 1;
        } else {
            r = mid - 1;
        }
    }
    outfile << -1 << endl;
}