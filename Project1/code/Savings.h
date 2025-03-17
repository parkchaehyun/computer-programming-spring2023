#ifndef SAVINGS_H
#define SAVINGS_H

#include "BankAccount.h"

class Savings : public BankAccount {
    public:
        Savings(int num=0,float bal=0,float rate=3.5): BankAccount(num,bal) {
            intrate = rate;
        }

        void interest() {
            bal += bal*intrate/(100*12);
        } // add monthly interest to current balance

        int withdraw(float amount){
            if (bal < amount) {
                std::cout << "Cannot withdraw $" << amount << " on account #"
                << acctnum <<  " because the balance is low" << std::endl;
                return 0;
            } else {
                bal -= amount;
                return 1;
            }
        }

        virtual void print(){
            if (bal < 1000) {
                credit = 1;
            } else if (bal < 2000) {
                credit = 2;
            } else {
                credit = 3;
            }

            std::cout << "Savings Account: " << acctnum << std::endl;
            std::cout << "\tBankName: " << bank_name << std::endl;
            std::cout << "\tCredit: " << credit << std::endl;
            std::cout << "\tBalance: " << bal << std::endl;
            std::cout.precision(2);
            std::cout << "\tInterest: " << intrate << "%" << std::endl;
            std::cout << std::endl;
            std::cout.precision(6);
        }
    protected:
        float intrate; //interest rate(이자율)
};

#endif