#ifndef BANKSYSTEM_H
#define BANKSYSTEM_H

#include "BankAccount.h"
#include <cstring>

class BankSystem {
    public:
        void transaction(BankAccount* from, BankAccount* to, float amount){
            int transaction_fee = 0;
            if(strcmp(from->getBankname().c_str(), to->getBankname().c_str()) != 0){
                transaction_fee = 5;
            }
            if(from->withdraw(amount + transaction_fee)==1){
                to->deposit(amount);
            }
        } //electronic money transaction; there are sender and receiver
        void deposit(BankAccount* b, float amount){
            b->deposit(amount);
        } //person puts cash into his or her account
        void withdraw(BankAccount* b, float amount){
            b->withdraw(amount);
        } //person takes cash out of his or her account
        void loan(BankAccount* b, float amount){
            b->loan(amount);
        }//person takes out a loan
};

#endif