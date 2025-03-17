//
// Created by Chaehyun Park on 2023/05/04.
//
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

class Node {
public:
    int data;
    Node* next;
    Node* prev;

    Node(int val) {
        data = val;
        next = NULL;
        prev = NULL;
    }
};

class CircularList {
private:
    Node* head;

    void addNodeAfter(int val, Node* node) {
        if (node == NULL) {
            return; // cannot add node after NULL
        }
        Node* newNode = new Node(val);
        newNode->prev = node;
        newNode->next = node->next;
        node->next->prev = newNode;
        node->next = newNode;
    }

    void deleteNode(Node* node) {
        if (node == NULL) {
            return;
        }
        if (node == head) {
            head = node->next;
        }

        node->prev->next = node->next;
        node->next->prev = node->prev;

        delete node;
    }

    Node* findNode(int data) {
        if (head == NULL) {
            return NULL; // list is empty, value not found
        }
        Node* temp = head;
        do {
            if (temp->data == data) {
                return temp;
            }
            temp = temp->next;
        } while (temp != head);

        return NULL; // value not found in list
    }
public:
    CircularList() {
        head = NULL;
    }

    void addNode(int val) {
        Node* newNode = new Node(val);

        if (head == NULL) {
            head = newNode;
            head->next = head;
            head->prev = head;
        }
        else {
            Node* last = head->prev;
            last->next = newNode;
            newNode->prev = last;
            newNode->next = head;
            head->prev = newNode;
        }
    }

    int insertAfter(int findData, int insertData) {
        Node* target = findNode(findData);
        int afterData = target->next->data;

        addNodeAfter(insertData, target);

        return afterData;
    }

    int insertBefore(int findData, int insertData) {
        Node* target = findNode(findData);
        int beforeData = target->prev->data;

        addNodeAfter(insertData, target->prev);

        return beforeData;
    }

    int deleteAfter(int data) {
        Node* target = findNode(data);
        int deleteData = target->next->data;
        deleteNode(target->next);

        return deleteData;
    }

    int deleteBefore(int data) {
        Node* target = findNode(data);
        int deleteData = target->prev->data;
        deleteNode(target->prev);

        return deleteData;
    }

    void printList() {
        if (head == NULL) {
            cout << "List is empty" << endl;
        }
        else {
            Node* temp = head;
            do {
                cout << temp->data << " ";
                temp = temp->next;
            } while (temp != head);
            cout << endl;
        }
    }
};
void readInput(ifstream* inputFile, CircularList* list, ofstream* outputFile);
void getOutput(string inputString, CircularList* list, ofstream* outputFile);

void readInput(ifstream* inputFile, CircularList* list, ofstream* outputFile) {
    string tmpString;
    int stationNumber;
    int inputNumber;

    if(getline(*inputFile, tmpString)) {
        istringstream iss(tmpString);  // create a stringstream from the line
        iss >> stationNumber >> inputNumber;
    }

    if(getline(*inputFile, tmpString)) {
        istringstream iss(tmpString);
        for(int i = 0; i < stationNumber; i++) {
            int num;
            iss >> num;
            list->addNode(num);
        }
    }

    for (int i = 0; i < inputNumber; i++) {
        getline(*inputFile, tmpString);
        getOutput(tmpString, list, outputFile);
    }
}

void getOutput(string inputString, CircularList* list, ofstream* outputFile) {
    int output;

    if (inputString.substr(0, 3) == "BN ") {
        istringstream iss(inputString.substr(3));
        int i, j;
        iss >> i >> j;
        output=list->insertAfter(i, j);
    } else if (inputString.substr(0, 3) == "BP ") {
        istringstream iss(inputString.substr(3));
        int i, j;
        iss >> i >> j;
        output=list->insertBefore(i, j);
    } else if (inputString.substr(0,3) == "CN ") {
        output=list->deleteAfter(stoi(inputString.substr(3)));
    } else if (inputString.substr(0,3) == "CP ") {
        output=list->deleteBefore(stoi(inputString.substr(3)));
    }

    (*outputFile) << output << endl;
}


int main() {
    CircularList* list = new CircularList();

    ifstream* inputFile = new ifstream("p2input.txt");
    ofstream* outputFile = new ofstream("p2output.txt");
    readInput(inputFile, list, outputFile);

    inputFile->close();
    outputFile->close();

    return 0;
}