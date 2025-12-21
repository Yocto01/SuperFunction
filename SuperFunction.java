import java.math.BigInteger;

/* 任意の有限数列から一般項を求めるクラス */
/*
 * 仕組み:
 * 与えられた数列 {a1, a2, a3, ..., an} に対して、
 * 一般項を多項式の形で表すと仮定する。
 * つまり、一般項 a_n を以下のように表す:
 * a_n = A*n^(n-1) + B*n^(n-2) + ... + Z
 * ここで、A, B, ..., Z は定数であり、n は項の番号である。 
 * この仮定に基づいて、与えられた数列の各項に対して連立方程式を立てる。
 * 例：数列 {3,1,4} の場合
 * 3 = a1 = A(1)^2 + B(1) + C
 * 1 = a2 = A(2)^2 + B(2) + C
 * 4 = a3 = A(3)^2 + B(3) + C
 * これを連立方程式として解く
*/
public class SuperFunction{
    // 与えられた数列から一般項を求める
    public Rational[] solve(BigInteger[] an){
        Rational equation[],solution[];     // 連立方程式と解
        int n = an.length;                  // 数列の項数

        equation = set(an); // セッティング(連立方程式の生成)
        solution = SimultaneousEquations(equation,n);   // 連立方程式を解く

        return solution;
    }

    // 連立方程式の生成
    public Rational[] set(BigInteger[] an){
        Rational equation[];
        int n = an.length;
        equation = new Rational[n*(n+1)];   // 連立方程式の配列(n行(n+1)列)
        // 連立方程式の生成
        for(int i=0; i<n; i++){
            BigInteger x = new BigInteger(String.valueOf(i+1)); // 項番号
            for(int j=0; j<n; j++){
                equation[i*(n+1)+j] = new Rational(x.pow(n-j-1),BigInteger.valueOf(1)); // x^(n-j-1)
            }
            equation[i*(n+1)+n] = new Rational(an[i],BigInteger.valueOf(1));    // 等式右辺
        }
        return equation;
    }

    // 連立方程式を解く
    /*
     * ガウスの消去法(掃き出し法)を用いて連立方程式を解く
     */
    public Rational[] SimultaneousEquations(Rational[] e,int n){
        for (int i=0; i<n; i++){
            Rational div = e[i*(n+1)+i];    // ピボット要素で割る
            for(int j=0; j<n+1; j++){
                e[i*(n+1)+j] = e[i*(n+1)+j].divide(div);    // ピボット要素を1にする(行全体をピボット要素で割る)
            }
            // 他の行をピボット要素で消去する
            for(int x=0; x<n; x++){
                if (x == i){ continue; }    // ピボット行はスキップ
                Rational mul = e[x*(n+1)+i];    // 消去に使う要素
                for(int y=0; y<n+1; y++){
                    e[x*(n+1)+y] = e[x*(n+1)+y].minus(e[i*(n+1)+y].multiply(mul));  // 他の行からピボット行を引く
                }
            }
        }
        Rational solution[];    // 解の配列
        solution = new Rational[n];
        // 解を取り出す
        for(int i=0; i<n; i++){
            solution[i] = e[i*(n+1)+n];
        }
        return solution;
    }
}