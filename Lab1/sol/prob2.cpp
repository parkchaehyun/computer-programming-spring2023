#include <iostream>
#include <string>

using namespace std;

class Salad {
protected:
    string meat;
    int BasePrice, ToppingPrice, cheese, avocado ;
public:
    Salad(string _meat) {
        BasePrice = 0;
        ToppingPrice = 0;
        cheese = 0;
        avocado = 0;
        meat = _meat;
    }

    void addSomething(string sth) {
        if (sth.compare("avocado") == 0) {
            avocado += 1;
        } else if (sth.compare("cheese") == 0) {
            cheese += 1;
        }
    }

    void showPrice() {
        if (meat.compare("chicken") == 0) {
            BasePrice = 8500;
        } else if (meat.compare("turkey") == 0) {
            BasePrice = 9000;
        }

        ToppingPrice = 2000 * avocado + 1000 * cheese;

        cout << "price : " << BasePrice + ToppingPrice << endl;
    }
};

class Sandwich: public Salad {
private:
    int length;
public:
    Sandwich(int _length, string _meat): Salad(_meat) {
        length = _length;
    }

    void showPrice() {
        if (meat.compare("chicken") == 0) {
            BasePrice = 7500 * (length / 30);
        } else if (meat.compare("turkey") == 0) {
            BasePrice = 8000 * (length / 30);
        }

        ToppingPrice = 2000 * avocado + 1000 * cheese;

        cout << "price : " << BasePrice + ToppingPrice << endl;
    }
};

int main() {
    int num;
    cout << "샐러드 주문은 1, 샌드위치 주문은 2" << endl;
    cin >> num;
    if(num == 1){
        Salad salad1("chicken");
        salad1.addSomething("cheese");
        salad1.showPrice();
    }
    else if(num == 2){
        Sandwich sandwich1(30, "turkey");
        sandwich1.addSomething("avocado");
        sandwich1.showPrice();
    }
}