import java.math.BigInteger;

/* 有理数クラス */
public class Rational {
    private BigInteger numerator;   // 分子
    private BigInteger denominator; // 分母

    // 分子、分母両方与えた場合のコンストラクタ(BigInteger型)
    public Rational(BigInteger numerator, BigInteger denominator) {
		if (denominator == null) {
			throw new IllegalArgumentException("分母はnullにできません.");
		}
		if (numerator == null) {
			throw new IllegalArgumentException("分子はnullにできません.");
		}
		if (denominator == new BigInteger("0")) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}

    // 分子のみ与えられた場合のコンストラクタ(BigInteger型)
    public Rational(BigInteger numerator){
        this.numerator = numerator;
        this.denominator = BigInteger.ONE;
    }

    // 分子のみ与えられた場合のコンストラクタ(int型)
    public Rational(int numerator){
        Integer n = Integer.valueOf(numerator);
        this.numerator = new BigInteger(n.toString());
        this.denominator = BigInteger.ONE;
    }

    // 分子、分母両方与えた場合のコンストラクタ(int型)
    public Rational(int numerator, int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
        Integer n = Integer.valueOf(numerator);
        Integer d = Integer.valueOf(denominator);
		this.numerator = new BigInteger(n.toString());
		this.denominator = new BigInteger(d.toString());
	}

    // 引数を指定しない場合のコンストラクタ
    public Rational(){
        this.numerator = null;
        this.denominator = null;
    }

    // ゲッター・セッター
    // 分子を返す
    public BigInteger getNumerator(){
        return this.numerator;
    }

    // 分母を返す
    public BigInteger getDenominator(){
        return this.denominator;
    }

    // 自身を返す
    public Rational getRational(){
        return this;
    }

    // 自身の情報を文字列に変換して返す
    public String getRationalToString(){
        return this.numerator.toString() + "/" + this.denominator.toString();
    }

    // BigInteger版セッター（分子のみ）
    public void setNumerator(BigInteger numerator){
        if (numerator == null) {
			throw new IllegalArgumentException("分子はnullにできません.");
		}
        this.numerator = numerator;
    }

    // Int版セッター（分子のみ）
    public void setNumerator(int numerator){
        Integer n = Integer.valueOf(numerator);
        this.numerator = new BigInteger(n.toString());
    }

    // BigInteger版セッター（分母のみ）
    public void setDenominator(BigInteger denominator){
        if (denominator == null) {
			throw new IllegalArgumentException("分母はnullにできません.");
		}
		if (denominator == new BigInteger("0")) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
		this.denominator = denominator;
    }

    // int版セッター（分母のみ）
    public void setDenominator(int denominator){
        if (denominator == 0) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
        Integer d = Integer.valueOf(denominator);
		this.denominator = new BigInteger(d.toString());
    }

    // BigInteger版セッター（分数全体）
    public void setRational(BigInteger numerator, BigInteger denominator){
        if (denominator == null) {
			throw new IllegalArgumentException("分母はnullにできません.");
		}
		if (numerator == null) {
			throw new IllegalArgumentException("分子はnullにできません.");
		}
		if (denominator == new BigInteger("0")) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
		this.numerator = numerator;
		this.denominator = denominator;   
    }

    // int版セッター（分数全体）
    public void setRational(int numerator, int denominator){
        if (denominator == 0) {
			throw new IllegalArgumentException("分母は0にできません.");
		}
        Integer n = Integer.valueOf(numerator);
        Integer d = Integer.valueOf(denominator);
		this.numerator = new BigInteger(n.toString());
		this.denominator = new BigInteger(d.toString());
    }

    // 同値かどうか判定するメソッド
    public boolean equals(Rational b){
        if(this.numerator.equals(b.numerator) && this.denominator.equals(b.denominator)){   // 分子と分母どちらも等しければ
            return true;
        } else {
            return false;
        }
    }

    // 標準化するメソッド
    public void Standardization(){
        BigInteger gcd = this.numerator.gcd(this.denominator);  // 分母と分子の最大公約数
        // 約分：分母と分子を最大公約数で割る
        this.numerator = this.numerator.divide(gcd); 
        this.denominator = this.denominator.divide(gcd);
        // 符号の標準化：分母のマイナスを分子に変える
        if (this.denominator.compareTo(BigInteger.valueOf(0)) < 0){
            this.denominator = this.denominator.negate();
            this.numerator = this.numerator.negate();
        }
    }

    // 逆数にするメソッド
    public Rational inv(){
        Rational b = new Rational();
        // 分子と分母を入れ替える
        b.numerator = this.denominator; 
        b.denominator = this.numerator;
        return b;
    }

    // 足し算をするメソッド
    public Rational add(Rational b){
        BigInteger gcd = this.denominator.gcd(b.denominator);   // 分母の最大公約数
        BigInteger lcm = (this.denominator.multiply(b.denominator)).divide(gcd);    // 分母の最小公倍数
        // 通分した時に分子にかけられる数
        BigInteger times1 = b.denominator.divide(gcd);   
        BigInteger times2 = this.denominator.divide(gcd);

        Rational c = new Rational();
        // 通分
        c.denominator = lcm;
        c.numerator = (this.numerator.multiply(times1)).add(b.numerator.multiply(times2));
        c.Standardization();    // 約分
        return c;
    }

    // 引き算をするメソッド
    public Rational minus(Rational b){
        Rational c = new Rational(b.numerator.negate(),b.denominator);
        return this.add(c); // a - b = a + (-b)
    }

    // 掛け算をするメソッド
    public Rational multiply(Rational b){
        Rational c = new Rational();
        // あらかじめ約分できるものはしておく
        this.Standardization();
        b.Standardization();
        // 分子同士、分母同士掛け算
        c.numerator = this.numerator.multiply(b.numerator);
        c.denominator = this.denominator.multiply(b.denominator);
        c.Standardization();    // 標準化
        return c;
    }

    // 割り算をするメソッド
    public Rational divide(Rational b){
        return this.multiply(b.inv());  // a / b = a * (1/b)
    }
}
