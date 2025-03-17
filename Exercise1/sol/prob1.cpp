#include <iostream>
#include <string>

using namespace std;

class Equation {
private:
    int quadratic, linear, constant;
public:
    Equation(int c) {
        quadratic = 0;
        linear = 0;
        constant = 0;
        constant = c;
    }

    Equation(int l, int c) {
        linear = l;
        constant = c;
    }

    Equation(int q, int l, int c) {
        quadratic = q;
        linear = l;
        constant = c;
    }

    int getQuadratic() {
        return quadratic;
    }

    int getLinear() {
        return linear;
    }

    int getConstant() {
        return constant;
    }
};

class EquationUtility {
public:
    Equation add(Equation a, Equation b) {
        int q = a.getQuadratic() + b.getQuadratic();
        int l = a.getLinear() + b.getLinear();
        int c = a.getConstant() + b.getConstant();

        return Equation(q, l, c);
    }

    string output(Equation e) {
        string out = "";

        if (e.getQuadratic() != 0) {
            if(e.getQuadratic() != 1) {
                out += to_string(e.getQuadratic());
            }

            out += "x^2";
        }

        if (e.getLinear() != 0) {
            if (e.getLinear() > 0 && e.getQuadratic()!= 0) {
                out += "+";
            }

            if(e.getLinear() != 1) {
                out += to_string(e.getLinear());
            }

            out += "x";
        }

        if (e.getConstant() != 0) {
            if (e.getConstant() > 0 && !(e.getQuadratic() == 0 && e.getLinear() == 0)) {
                out += "+";
            }

            out += to_string(e.getConstant());
        }

        return out;
    }
};

int main() {
    Equation e1(2);
    Equation e2(4, -5);
    EquationUtility a;
    Equation result = a.add(e1, e2);
    cout << a.output(result) << endl;
    Equation e3(3, 0, 5);
    result = a.add(e1, e3);
    cout << a.output(result) << endl;
    return 0;
}
