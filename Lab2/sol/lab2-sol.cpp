#include <iostream>
#include <fstream>                                                          // - 각종 File IO를 위한 library를 include
#include <vector>                                                          // - vector를 위한 library를 include
#include <string>

using namespace std;

void openGradeFiles(ifstream* filenameInput, vector <ofstream>* nameVector);
void readScores(ifstream* inputFile, vector <int>* scoreVector);
void writeScores(vector <int>* scoreVector, vector <ofstream>* nameVector);

void closeInputFile(ifstream* inputFile);
void closeGradeFiles(vector <ofstream>* nameVector);

void openGradeFiles(ifstream* filenameInput, vector <ofstream>* nameVector) {                // 변수명은 수정하지 않고 진행
    vector <ofstream>::iterator nameItr;                                                        // - nameVector를 위한 iterator 변수
    string tmpString;                                                      // - line read를 위한 string 변수

    for(nameItr = nameVector->begin() ; nameItr != nameVector->end(); ++nameItr) {                          // - iterator를 이용해 nameVector를 처음부터 끝까지 접근
        if(!getline(*filenameInput, tmpString)) {
            printf("Error: getline error\n");
            exit(1);
        }

        nameItr->open(tmpString);                                                       // - iterator와 tmpString을 이용해 file create 및 open
    }
}

void readScores(ifstream* inputFile, vector <int>* scoreVector) {                       // 변수명은 수정하지 않고 진행
    string tmpString;                                                      // - line read를 위한 string 변수

    while(getline(*inputFile, tmpString)) {                                   // - inputFile을 한 줄 씩 읽어옴
        scoreVector->push_back(stoi(tmpString));                                                         // - 읽은 line을 stoi()를 이용해 scoreVector 맨 뒤에 삽입
    }
}

void writeScores(vector <int>* scoreVector, vector <ofstream>* nameVector) {                     // 변수명은 수정하지 않고 진행
    vector <int>::iterator scoreItr;                                                       // - scoreVector를 위한 iterator

    for(scoreItr = scoreVector->begin(); scoreItr != scoreVector->end(); ++scoreItr) {                         // - iterator를 이용해 scoreVector를 처음부터 끝까지 접근
        switch (*scoreItr)                                                    // - scoreVector의 원소 값을 확인
        {                                                     /* !!** case 내의 ...은 범위를 표시하는 것이므로 수정하지 않습니다 **!! */
        case 90 ... 100:
            nameVector->at(0) << *scoreItr << "\n";                                         // - 첫 번째 output file stream ("A_score.txt")에 write
            break;
        case 80 ... 89:
            nameVector->at(1) << *scoreItr << "\n";                                         // - 두 번째 output file stream ("B_score.txt")에 write
            break;
        case 70 ... 79:
            nameVector->at(2) << *scoreItr << "\n";                                         // - 세 번째 output file stream ("C_score.txt")에 write
            break;
        case 60 ... 69:
            nameVector->at(3) << *scoreItr << "\n";                                         // - 네 번째 output file stream ("D_score.txt")에 write
            break;
        default:
            nameVector->at(4) << *scoreItr << "\n";                                         // - 다섯 번째 output file stream ("Fail_score.txt")에 write
            break;
        }
    }   
}

void closeInputFile(ifstream* inputFile) {                                    // 변수명은 수정하지 않고 진행
    inputFile->close();                                                                 // - inputFile을 close
}

void closeGradeFiles(vector <ofstream>* nameVector) {                                  // 변수명은 수정하지 않고 진행
    vector <ofstream>::iterator nameItr;                                                        // - nameVector를 위한 iterator

    for(nameItr = nameVector->begin(); nameItr != nameVector->end(); ++nameItr) {                           // - iterator를 이용해 nameVetor를 처음부터 끝까지 접근
        nameItr->close();                                                             // - iterator를 이용해 output file stream을 close
    }
}

int main() {
    ifstream* tmpfilenameInput = new ifstream("filename.txt");           // for "filename.txt"
    ifstream* tmpscoreInput = new ifstream("score.txt");              // for "score.txt"

    vector <ofstream>* tmpnameVector = new vector<ofstream>(5);               // for output file stream vector
    vector <int>* tmpscoreVector = new vector<int>();                   // for integer vector

    openGradeFiles(tmpfilenameInput, tmpnameVector);                                         // Fuction Call
    readScores(tmpscoreInput, tmpscoreVector);
    writeScores(tmpscoreVector, tmpnameVector);


    closeInputFile(tmpfilenameInput);
    closeInputFile(tmpscoreInput);
    closeGradeFiles(tmpnameVector);

    return 0;
}