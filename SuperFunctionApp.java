import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/* メインクラス */
public class SuperFunctionApp extends JFrame implements ActionListener{
    private JLabel lb[];    // ラベル配列
    private JTextField tf[];    // テキストフィールド配列
    private JTextArea ta[];     // テキストエリア配列
    private JButton btn[];   // ボタン配列

    private int n;                  // 項数
    private BigInteger an[];    // 数列の各項
    private Rational solved[];  // 解
    private String input1,input2; // 入力用データ
    private String output;  // 出力用データ

    public SuperFunctionApp(){
        super();    // JFrameのコンストラクタを呼び出す
        this.n = 0; 

        int NLB = 6;    // ラベル数
        int NTF = 2;    // テキストフィールド数
        int NTA = 1;    // テキストエリア数
        int NBTN = 3;   // ボタン数
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
        this.lb[5] = new JLabel();
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
        this.btn[1] = new JButton("入力ファイルを選択");
        this.btn[2] = new JButton("出力をファイルに保存");
        JScrollPane scroll = new JScrollPane(this.ta[0]);   // テキストエリアにスクロールバーを追加
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);   // 常に縦スクロールバーを表示
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);    // 横スクロールバーは必要に応じて表示
        
        for(int i=0; i<NBTN; i++){
            this.btn[i].addActionListener(this);    // ボタンにアクションリスナーを追加
        }

        JPanel panel[]; 
        int NPANEL = 4;  // パネル数
        // パネルの生成と設定
        panel = new JPanel[NPANEL];
        for(int i=0; i<NPANEL; i++){
            panel[i] = new JPanel();
        }
        // レイアウト設定
        panel[0].setLayout(new BoxLayout(panel[0],BoxLayout.PAGE_AXIS));
        for(int i=1; i<NPANEL; i++){
            panel[i].setLayout(new BoxLayout(panel[i],BoxLayout.LINE_AXIS));
        }

        // コンポーネントの配置
        panel[0].add(this.lb[0]);
        panel[1].add(this.lb[1]);
        panel[1].add(this.btn[1]);
        panel[0].add(panel[1]);
        panel[0].add(this.tf[0]);
        panel[0].add(this.lb[2]);
        panel[2].add(this.lb[3]);
        panel[2].add(this.btn[0]);
        panel[0].add(panel[2]);
        panel[0].add(this.tf[1]);
        panel[3].add(this.lb[4]);
        panel[3].add(this.btn[2]);
        panel[0].add(panel[3]);
        panel[0].add(this.lb[5]);
        panel[0].add(scroll);
        
        // フレームにパネルを追加
        super.getContentPane().add(panel[0], BorderLayout.CENTER);
    }

    // ボタンが押されたときの処理
    public void actionPerformed(ActionEvent e){
        // 入力データの取得(一般項を求めるボタンが押されたとき)
        if(e.getSource() == this.btn[0]){
            try {
                this.input1 = this.tf[0].getText();
                this.input2 = this.tf[1].getText();
            } catch (NumberFormatException ex) {    // 入力が不正な場合(ちゃんとcatchできてないので要修正)
                this.lb[5].setText("入力が不正です！");
                return;
            }
        }
        // 入力ファイルを選択ボタンが押されたとき
        if(e.getSource() == this.btn[1]){
            JFileChooser chooser = new JFileChooser();  // ファイル選択ダイアログの生成
            chooser.setAcceptAllFileFilterUsed(false);  // 全てのファイルフィルタを無効化
            FileNameExtensionFilter filter = new FileNameExtensionFilter("テキストファイル", "txt");    // 拡張子がtxtのファイルのみ表示するフィルタの生成
            chooser.addChoosableFileFilter(filter); // フィルタの追加
            chooser.setFileFilter(filter);  // デフォルトのフィルタを設定
            int selected = chooser.showOpenDialog(this);    // ファイル選択ダイアログの表示
            if (selected != JFileChooser.APPROVE_OPTION) return;    // キャンセルされたときは何もしない
            // OKボタンが押されたとき
            File file = chooser.getSelectedFile();  // 選択されたファイルの取得
            String path = file.getAbsolutePath();   // ファイルのパスを取得
            if(!path.toLowerCase().endsWith(".txt")){   // 拡張子が.txtでない場合
                this.lb[0].setText("拡張子が.txtのファイルを選択してください!");
                return;
            }   
            try {   // 入力データの読み込み
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                // 2行読み込み
                this.input1 = br.readLine();
                this.input2 = br.readLine();
                if(input1 == null || input2 == null){   // ファイルの内容が不正な場合
                    this.lb[5].setText("ファイルの内容が不正です！");
                    br.close();
                    return;
                }
                this.tf[0].setText(this.input1);    // テキストフィールドに表示
                this.tf[1].setText(this.input2);    // テキストフィールドに表示
                br.close(); // ファイルを閉じる
            } catch (FileNotFoundException e1) {    // ファイルが見つからなかった場合
                // TODO Auto-generated catch block
                e1.printStackTrace();   // エラーメッセージの表示
            } catch (IOException e1) {  // 入出力エラーが発生した場合
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } 
        
        // 入力されたデータをもとに一般項を求める
        // 文字列から項数と各項の取得
        try {
            this.n = Integer.parseInt(this.input1);   // 項数を整数に変換
            this.an = new BigInteger[n];    // 数列の各項を格納する配列を生成
            String[] split = this.input2.split(" ");  // スペースで分割
            // 各項を数列の配列に格納
            // 項数分の値が入力されているか確認
            if(split.length >= n){
                for (int i=0; i<n; i++){
                    this.an[i] = new BigInteger(split[i]);
                }
            } else {
                this.lb[5].setText("項が足りないです！");   
                return;
            }
        } catch (NumberFormatException ex) {    // 入力が不正な場合
            this.lb[5].setText("入力が不正です！");
            return;
        }
        this.lb[5].setText(""); // エラーメッセージをクリア
        this.ta[0].setText("計算中...");

        // 一般項を求める
        SuperFunction sf = new SuperFunction();
        this.solved = sf.solve(this.an);

        // 結果を表示
        this.output = "";
        for(int i=0; i<n; i++){
            if (i != 0){
                this.output += " + ";
            }
            this.output += solved[i].getRationalToString() + " n^" + (n-i-1);
        }
        this.ta[0].setText(this.output);
        
        // ファイルに保存ボタンが押されたとき出力を保存
        if(e.getSource() == this.btn[2]){
            JFileChooser chooser = new JFileChooser();  // ファイル選択ダイアログの生成
            chooser.setAcceptAllFileFilterUsed(false);  // 全てのファイルフィルタを無効化
            FileNameExtensionFilter filter = new FileNameExtensionFilter("テキストファイル", "txt");    // 拡張子がtxtのファイルのみ表示するフィルタの生成
            chooser.addChoosableFileFilter(filter); // フィルタの追加
            chooser.setFileFilter(filter);  // デフォルトのフィルタを設定
            int selected = chooser.showSaveDialog(this);    // ファイル選択ダイアログの表示
            if (selected != JFileChooser.APPROVE_OPTION) return;    // キャンセルされたときは何もしない
            File file = chooser.getSelectedFile();  // 選択されたファイルの取得
            String path = file.getAbsolutePath();   // ファイルのパスを取得
            if(!path.toLowerCase().endsWith(".txt")){   // 拡張子が.txtでない場合
                this.lb[5].setText("拡張子が.txtのファイルを選択してください!");
                return;
            }
            try {   // 出力データの書き込み
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(this.output);
                bw.close();
            } catch (IOException e1) {  // 入出力エラーが発生した場合
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
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