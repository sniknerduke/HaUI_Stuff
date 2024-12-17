#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
using namespace std;

// Thuật toán tham lam để tìm số lượng phần tử nhiều nhất trong mảng a sao cho tổng <= C
void greedySelection(const vector<double>& a, double C, vector<double>& selected, double& totalSum, int& D) {
    totalSum = 0;
    D = 0;
    for (double val : a) {
        if (totalSum + val <= C) {
            selected.push_back(val);
            totalSum += val;
            D++;
        } else {
            break;
        }
    }
}

// Cài đặt thuật toán Boyer-Moore-Horspool
unordered_map<char, int> createShiftTable(const string& pattern) {
    int m = pattern.size();
    unordered_map<char, int> shiftTable;
    for (int i = 0; i < 256; i++) {
        shiftTable[i] = m; // Mặc định dịch chuyển toàn bộ
    }
    for (int i = 0; i < m - 1; i++) {
        shiftTable[pattern[i]] = m - 1 - i; // Cập nhật bảng dịch chuyển
    }
    return shiftTable;
}

bool boyerMooreHorspool(const string& P, const string& Q) {
    int n = P.size();
    int m = Q.size();
    if (m > n) return false;

    unordered_map<char, int> shiftTable = createShiftTable(Q);
    int i = m - 1; // Bắt đầu từ cuối chuỗi mẫu Q
    int j = m - 1; // Bắt đầu từ cuối chuỗi P

    while (i < n) {
        if (P[j] == Q[i]) {
            if (j == 0) return true; // Nếu khớp hết chuỗi Q, trả về true
            j--; i--;
        } else {
            int shift = shiftTable[P[i]]; // Dịch chuyển theo bảng dịch chuyển
            i = i + m - 1 < n ? i + shift : n;
            j = m - 1;
        }
    }
    return false;
}

int main() {
    int n;
    cout << "Nhập số phần tử của mảng (n >= 8): ";
    cin >> n;

    if (n < 8) {
        cout << "Số phần tử phải lớn hơn hoặc bằng 8!" << endl;
        return 1;
    }

    vector<double> a(n);
    cout << "Nhập các phần tử của mảng a theo chiều tăng dần: " << endl;
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    double C;
    cout << "Nhập giá trị C: ";
    cin >> C;

    // 1. Thuật toán tham lam để tính số lượng phần tử và tổng không vượt quá C
    vector<double> selected;
    double totalSum;
    int D;
    greedySelection(a, C, selected, totalSum, D);

    cout << "Số lượng phần tử được chọn: " << D << endl;
    cout << "Tổng các phần tử được chọn: " << totalSum << endl;
    cout << "Các phần tử đã chọn trong mảng a: ";
    for (double val : selected) {
        cout << val << " ";
    }
    cout << endl;

    // 2. Thuật toán Boyer-Moore-Horspool để kiểm tra Q có là chuỗi con của P không
    string P, Q;
    cout << "Nhập chuỗi P: ";
    cin.ignore();  // Để bỏ qua ký tự newline còn lại trong buffer
    getline(cin, P);
    cout << "Nhập chuỗi Q: ";
    getline(cin, Q);

    bool isSubstring = boyerMooreHorspool(P, Q);
    if (isSubstring) {
        cout << "Q là chuỗi con của P." << endl;
    } else {
        cout << "Q không phải là chuỗi con của P." << endl;
    }

    return 0;
}