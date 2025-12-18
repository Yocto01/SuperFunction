import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*次回やること
 *ボタンを押した際に一般項を出すようにする
 *スライダーを見やすくする
 *入力・保存ファイルを用意する
 *より見やすい出力にする*/
public class SuperFunctionApp extends JFrame implements ActionListener{
    private JLabel lb[];
    private JTextField tf[];
    private JTextArea ta[];

    private int n;
    private int an[];
    private Rational solved[];

    public SuperFunctionApp(){
        super();
        this.n = 0;

        int NLB = 5;
        int NTF = 2;
        int NTA = 1;
        this.lb = new JLabel[NLB];
        this.tf = new JTextField[NTF];
        this.ta = new JTextArea[NTA];

        this.lb[0] = new JLabel("SUPER FUNCTION ~ 任意の有限数列から一般項を求める関数 ~");
        this.lb[1] = new JLabel("項数を半角数字で入力");
        this.lb[2] = new JLabel("第1項から第n項までスペース区切りで半角数字で入力");
        this.lb[3] = new JLabel("例：3 1 4");
        this.lb[4] = new JLabel("一般項 a_n = ");
        this.tf[0] = new JTextField(3);
        this.tf[1] = new JTextField(256);
        this.ta[0] = new JTextArea(20,64);

        this.tf[0].addActionListener(this);
        this.tf[1].addActionListener(this);

        JPanel panel[];
        int NPANEL = 2;
        panel = new JPanel[NPANEL];
        for(int i=0; i<NPANEL; i++){
            panel[i] = new JPanel();
        }
        BoxLayout bl = new BoxLayout(panel[0],BoxLayout.PAGE_AXIS);
        panel[0].setLayout(bl);
        FlowLayout fl = new FlowLayout();
        panel[1].setLayout(fl);

        panel[0].add(this.lb[0]);
        panel[0].add(this.lb[1]);
        panel[0].add(this.tf[0]);
        panel[0].add(this.lb[2]);
        panel[0].add(this.lb[3]);
        panel[0].add(this.tf[1]);
        panel[0].add(this.lb[4]);
        panel[1].add(this.ta[0]);
        panel[0].add(panel[1]);

        super.getContentPane().add(panel[0]);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.tf[0]){
            String s = this.tf[0].getText();
            this.n = Integer.parseInt(s);
            this.an = new int[n];
        }
        if (e.getSource() == this.tf[1]){
            String s = this.tf[1].getText();
            String[] split = s.split(" ");
            if(split.length >= n){
                for (int i=0; i<n; i++){
                    this.an[i] = Integer.parseInt(split[i]);
                }
            } else {
                this.ta[0].setText("項が足りないです！");
                return;
            }
            SuperFunction sf = new SuperFunction();
            this.solved = sf.solve(this.an);
            String output = solved[0].getNumerator() + "/" + solved[0].getDenominator() + " n^" + (n-1) + "\n";
            for(int i=1; i<n; i++){
                output += "+ " + solved[i].getNumerator() + "/" + solved[i].getDenominator() + " n^" + (n-i-1) + "\n";
            }
            this.ta[0].setText(output);
        }
    }

    public static void main(String[] args){
        SuperFunctionApp a = new SuperFunctionApp();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(900,600);
        a.setLocation(400,100);
        a.setVisible(true);
    }
}