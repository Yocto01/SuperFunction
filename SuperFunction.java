import java.math.BigInteger;

public class SuperFunction{
    
    public Rational[] solve(int[] an){
        Rational equation[],solution[];
        int n = an.length;

        equation = set(an);
        solution = SimultaneousEquations(equation,n);

        return solution;
    }

    public Rational[] set(int[] an){
        Rational equation[];
        int n = an.length;
        equation = new Rational[n*(n+1)];
        for(int i=0; i<n; i++){
            BigInteger x = new BigInteger(String.valueOf(i+1));
            for(int j=0; j<n; j++){
                equation[i*(n+1)+j] = new Rational(x.pow(n-j-1),BigInteger.valueOf(1));
            }
            equation[i*(n+1)+n] = new Rational(an[i],1);
        }
        return equation;
    }

    public Rational[] SimultaneousEquations(Rational[] e,int n){
        for (int i=0; i<n; i++){
            Rational div = e[i*(n+1)+i];
            for(int j=0; j<n+1; j++){
                e[i*(n+1)+j] = e[i*(n+1)+j].divide(div);
            }
            for(int x=0; x<n; x++){
                if (x == i){ continue; }
                Rational mul = e[x*(n+1)+i];
                for(int y=0; y<n+1; y++){
                    e[x*(n+1)+y] = e[x*(n+1)+y].minus(e[i*(n+1)+y].multiply(mul));
                }
            }
        }
        Rational solution[];
        solution = new Rational[n];
        for(int i=0; i<n; i++){
            solution[i] = e[i*(n+1)+n];
        }
        return solution;
    }
}