#ifndef CHECKING_H
#define CHECKING_H

#include "BankAccount.h"

class Checking : public BankAccount{
    public:
        Checking(int num=0,float bal=0,float min=1000,float chg=2): BankAccount(num,bal){
            minimum = min;
            charge = chg;
        }
        int withdraw(float amount){
            if (bal < amount) {
                std::cout << "Cannot withdraw $" << amount << " on account #"
                          << acctnum <<  " because the balance is low" << std::endl;
                return 0;
            } else {
                if (bal < minimum) {
                    bal -= charge;
                }
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

            std::cout << "Checking Account: " << acctnum << std::endl;
            std::cout << "\tBankName: " << bank_name << std::endl;
            std::cout << "\tCredit: " << credit << std::endl;
            std::cout << "\tBalance: " << bal << std::endl;
            std::cout << "\tMinimum to Avoid Charges: " << minimum << std::endl;
            std::cout << "\tCharge per Check: " << charge << std::endl;
            std::cout << std::endl;
        }
    protected:
        //minimum account balance to keep(유지되야 하는 최소한의 잔고)
        float minimum; 
        // amount of money charged when balance is below minimum(minimum이 유지안되었을 때 발생하는 벌금)
        float charge;
};

#endif