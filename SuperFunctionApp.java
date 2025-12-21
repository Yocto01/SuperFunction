import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;

import javax.swing.*;

/*次回やること
 *ボタンを押した際に一般項を出すようにする(済)
 *スライダーで見やすくする
 *入力・保存ファイルを用意する
 *より見やすい出力にする
 */
public class SuperFunctionApp extends JFrame implements ActionListener{
    private JLabel lb[];
    private JTextField tf[];
    private JTextArea ta[];
    private JButton btn[];
    

    private int n;
    private BigInteger an[];
    private Rational solved[];

    public SuperFunctionApp(){
        super();
        this.n = 0;

        int NLB = 5;
        int NTF = 2;
        int NTA = 1;
        int NBTN = 1;
        this.lb = new JLabel[NLB];
        this.tf = new JTextField[NTF];
        this.ta = new JTextArea[NTA];
        this.btn = new JButton[NBTN];

        this.lb[0] = new JLabel("SUPER FUNCTION ~ 任意の有限数列から一般項を求める関数 ~");
        this.lb[0].setFont(new Font("Serif", Font.BOLD, 20));
        this.lb[1] = new JLabel("項数を半角数字で入力 : ");
        this.lb[2] = new JLabel("第1項から第n項までスペース区切りで半角数字で入力 : ");
        this.lb[3] = new JLabel("例：3 1 4");
        this.lb[4] = new JLabel("一般項 a_n = ");
        for(int i=0; i<NLB; i++){
            this.lb[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        this.tf[0] = new JTextField();
        this.tf[0].setMaximumSize(new Dimension(Integer.MAX_VALUE,25));
        this.tf[1] = new JTextField();
        this.tf[1].setMaximumSize(new Dimension(Integer.MAX_VALUE,25));
        this.ta[0] = new JTextArea();
        this.ta[0].setLineWrap(true);
        this.btn[0] = new JButton("一般項を求める");
        JScrollPane scroll = new JScrollPane(this.ta[0]);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.btn[0].addActionListener(this);

        JPanel panel[];
        int NPANEL = 2;
        panel = new JPanel[NPANEL];
        for(int i=0; i<NPANEL; i++){
            panel[i] = new JPanel();
        }
        panel[0].setLayout(new BoxLayout(panel[0],BoxLayout.PAGE_AXIS));
        panel[1].setLayout(new BoxLayout(panel[1],BoxLayout.LINE_AXIS));

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
        

        super.getContentPane().add(panel[0], BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.btn[0]){
            String s = this.tf[0].getText();
            this.n = Integer.parseInt(s);
            this.an = new BigInteger[n];
            s = this.tf[1].getText();
            String[] split = s.split(" ");
            if(split.length >= n){
                for (int i=0; i<n; i++){
                    this.an[i] = new BigInteger(split[i]);
                }
            } else {
                this.ta[0].setText("項が足りないです！");
                return;
            }
            this.ta[0].setText("計算中...");
            SuperFunction sf = new SuperFunction();
            this.solved = sf.solve(this.an);
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
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(900,600);
        a.setLocation(400,100);
        a.setVisible(true);
    }
}