#ifndef BANKACCOUNT_H
#define BANKACCOUNT_H
#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>

using namespace std;

class BankAccount{
    public:
        BankAccount(int num,float bal){
            acctnum = num;
            this->bal = bal;
            switch (rand()%3) {
                case 0:
                    bank_name = "하나";
                    break;
                case 1:
                    bank_name = "우리";
                    break;
                case 2:
                    bank_name = "신한";
                    break;
            }
        }
        void deposit(float amount) {
            bal += amount;
        }
        void loan(float amount){
            if (bal < 1000) {
                credit = 1;
                std::cout << "The amount cannot be loaned" << std::endl;
            } else if (bal < 2000) {
                credit = 2;
                if (amount < 100 || amount > 500) {
                    std::cout << "The amount cannot be loaned" << std::endl;
                } else {
                    bal += amount*0.9;
                }
            } else {
                credit = 3;
                if (amount < 100 || amount > 1000) {
                    std::cout << "The amount cannot be loaned" << std::endl;
                } else {
                    bal += amount*0.95;
                }
            }
        }

        virtual int withdraw(float amount){
            bal -= amount;
            return 0;
        }
        int getAcctnum() {
            return acctnum;
        }
        float getBalance() {
            return bal;
        }
        int getCredit(){
            return credit;
        }
        string getBankname(){
            return bank_name;
        }
        virtual void print() = 0;
    protected:
        int acctnum; // account number(계좌번호)
        float bal; //current balance of account(현재 잔고)
        string bank_name;
        int credit;
};

#endif