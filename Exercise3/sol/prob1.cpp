#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

class myStack {
public:
    myStack(){
    }

    void push(int number) {
        numbers.push_back(number);
    }

    int pop() {
        if(empty() == 0) {
            int last = numbers[numbers.size()-1];
            numbers.pop_back();
            return last;
        } else {
            return -1;
        }
    }

    int size() {
        return numbers.size();
    }

    int empty() {
        if(numbers.empty()) {
            return 1;
        } else {
            return 0;
        }
    }

    int top() {
        if(empty() == 0) {
            int last = numbers[numbers.size()-1];
            return last;
        } else {
            return -1;
        }
    }

private:
    vector<int> numbers;
};

void readInput(ifstream* inputFile, myStack* stack, ofstream* outputFile);
void getOutput(string inputString, myStack* stack, ofstream* outputFile);

void readInput(ifstream* inputFile, myStack* stack, ofstream* outputFile) {
    string tmpString;
    int inputNumber;

    if(getline(*inputFile, tmpString)) {
        inputNumber = stoi(tmpString);
    }

    for (int i = 0; i < inputNumber; i++) {
        getline(*inputFile, tmpString);
        getOutput(tmpString, stack, outputFile);
    }
}

void getOutput(string inputString, myStack* stack, ofstream* outputFile) {
    int output=-2;

    if (inputString.substr(0, 5) == "push ") {
        stack->push(stoi(inputString.substr(5)));
    } else if (inputString == "pop") {
        output=stack->pop();
    } else if (inputString == "size") {
        output = stack->size();
    } else if (inputString == "empty") {
        output = stack->empty();
    } else if (inputString == "top") {
        output=stack->top();
    }

    if (output!=-2) {
        (*outputFile) << output << endl;
    }
}


int main() {
    myStack* stack = new myStack();
    ifstream* inputFile = new ifstream("p1input.txt");
    ofstream* outputFile = new ofstream("p1output.txt");

    readInput(inputFile, stack, outputFile);

    inputFile->close();
    outputFile->close();

    return 0;
}
