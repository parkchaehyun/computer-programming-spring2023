#ifndef INTERESTCHECKING_H
#define INTERESTCHECKING_H

#include "BankAccount.h"
#include "Checking.h"

class InterestChecking : public Checking {
    public:
        InterestChecking(int num=0,float bal=0,float cmin=1000,float imin=2500,float chg=2,float rate=2.5,float monchg=10)
        : Checking(num,bal,cmin,chg) {
            minint = imin;
            intrate = rate;
            moncharge = monchg;
        }
        void interest(){
            if (bal >= minint) {
                bal += bal*intrate/(100*12);
            } else {
                bal -= moncharge;
            }
        } // add monthly interest to current balance
        virtual void print(){
            if (bal < 1000) {
                credit = 1;
            } else if (bal < 2000) {
                credit = 2;
            } else {
                credit = 3;
            }

            std::cout << "Interest Checking Account: " << acctnum << std::endl;
            std::cout << "\tBankName: " << bank_name << std::endl;
            std::cout << "\tCredit: " << credit << std::endl;
            std::cout << "\tBalance: " << bal << std::endl;
            std::cout << "\tMinimum to Avoid Charges: " << minimum << std::endl;
            std::cout << "\tCharge per Check: " << charge << std::endl;
            std::cout << "\tMinimum balance for getting interest"
                      << " and No Monthly Fee: "<< minint << std::endl;
            std::cout.precision(2);
            std::cout << "\tInterest: " << intrate << "%" << std::endl;
            std::cout << "\tMonthly Fee: " << moncharge << std::endl;
            std::cout << std::endl;
            std::cout.precision(6);
        }
    protected:
        float intrate; // interest rate(이자율)
        float minint; // minimum checking balance to get interest(이자를 받는데 있어서 요구되는 최소한의 잔고)
        float moncharge; //monthly charge when balance is below minint(매월 이자 발생 시 최소한의 잔고가 없을 때 부과되는 벌금)
};

#endif