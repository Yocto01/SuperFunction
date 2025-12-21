import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import javax.swing.*;

/*次回やること
 *ボタンを押した際に一般項を出すようにする(済)
 *スライダーで見やすくする(済)
 *入力・保存ファイルを用意する
 *より見やすい出力にする
 */
public class SuperFunctionApp extends JFrame implements ActionListener{
    private JLabel lb[];    // ラベル配列
    private JTextField tf[];    // テキストフィールド配列
    private JTextArea ta[];     // テキストエリア配列
    private JButton btn[];   // ボタン配列

    private int n;                  // 項数
    private BigInteger an[];    // 数列の各項
    private Rational solved[];  // 解

    public SuperFunctionApp(){
        super();
        this.n = 0; 

        int NLB = 5;    // ラベル数
        int NTF = 2;    // テキストフィールド数
        int NTA = 1;    // テキストエリア数
        int NBTN = 1;   // ボタン数
        // コンポーネントの生成
        this.lb = new JLabel[NLB];
        this.tf = new JTextField[NTF];
        this.ta = new JTextArea[NTA];
        this.btn = new JButton[NBTN];

        // コンポーネントの設定
        this.lb[0] = new JLabel("SUPER FUNCTION ~ 任意の有限数列から一般項を求める関数 ~");
        this.lb[0].setFont(new Font("Serif", Font.BOLD, 20));   // タイトルを大きく表示,フォントの設定
        this.lb[1] = new JLabel("項数を半角数字で入力 : ");
        this.lb[2] = new JLabel("第1項から第n項までスペース区切りで半角数字で入力 : ");
        this.lb[3] = new JLabel("例：3 1 4");
        this.lb[4] = new JLabel("一般項 a_n = ");
        for(int i=0; i<NLB; i++){
            this.lb[i].setAlignmentX(Component.CENTER_ALIGNMENT);   // ラベルを中央寄せ
        }
        this.tf[0] = new JTextField();
        this.tf[0].setMaximumSize(new Dimension(Integer.MAX_VALUE,25));  // テキストフィールドの高さを25に固定
        this.tf[1] = new JTextField();
        this.tf[1].setMaximumSize(new Dimension(Integer.MAX_VALUE,25)); // テキストフィールドの高さを25に固定
        this.ta[0] = new JTextArea();
        this.ta[0].setLineWrap(true);   // テキストエリアの折り返し設定
        this.btn[0] = new JButton("一般項を求める");
        JScrollPane scroll = new JScrollPane(this.ta[0]);   // テキストエリアにスクロールバーを追加
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);   // 常に縦スクロールバーを表示
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);    // 横スクロールバーは必要に応じて表示
        
        this.btn[0].addActionListener(this);    // ボタンにアクションリスナーを追加

        JPanel panel[]; 
        int NPANEL = 2;  // パネル数
        // パネルの生成と設定
        panel = new JPanel[NPANEL];
        for(int i=0; i<NPANEL; i++){
            panel[i] = new JPanel();
        }
        panel[0].setLayout(new BoxLayout(panel[0],BoxLayout.PAGE_AXIS));
        panel[1].setLayout(new BoxLayout(panel[1],BoxLayout.LINE_AXIS));

        // コンポーネントの配置
        panel[0].add(this.lb[0]);
        panel[0].add(this.lb[1]);
        panel[0].add(this.tf[0]);
        panel[0].add(this.lb[2]);
        panel[1].add(this.lb[3]);
        panel[1].add(this.btn[0]);
        panel[0].add(panel[1]);
        panel[0].add(this.tf[1]);
        panel[0].add(this.lb[4]);
        panel[0].add(scroll);
        
        // フレームにパネルを追加
        super.getContentPane().add(panel[0], BorderLayout.CENTER);
    }

    // ボタンが押されたときの処理
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.btn[0]){   // 「一般項を求める」ボタンが押されたとき
            String s = this.tf[0].getText();    // 項数を取得
            this.n = Integer.parseInt(s);   // 項数を整数に変換
            this.an = new BigInteger[n];    // 数列の各項を格納する配列を生成
            s = this.tf[1].getText();   // 数列の各項を取得
            String[] split = s.split(" ");  // スペースで分割
            // 各項を数列の配列に格納
            // 項数分の値が入力されているか確認
            if(split.length >= n){
                for (int i=0; i<n; i++){
                    this.an[i] = new BigInteger(split[i]);
                }
            } else {
                this.ta[0].setText("項が足りないです！");   
                return;
            }
            this.ta[0].setText("計算中...");

            // 一般項を求める
            SuperFunction sf = new SuperFunction();
            this.solved = sf.solve(this.an);

            // 結果を表示
            String output = "";
            for(int i=0; i<n; i++){
                if (i != 0){
                    output += " + ";
                }
                output += solved[i].getRationalToString() + " n^" + (n-i-1);
            }
            this.ta[0].setText(output);
        }
    }

    public static void main(String[] args){
        SuperFunctionApp a = new SuperFunctionApp();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 閉じるボタンで終了
        a.setSize(900,600);
        a.setLocation(400,100);
        a.setVisible(true);
    }
}