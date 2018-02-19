import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewInit extends JFrame {

    private JLabel lbWelcome;
    private JLabel lbConfSnmp;
    private JLabel lbIpQues;
    private JLabel lbIpGet;
    private JButton btnAdd;
    private JButton btnSubmit;
    private JRadioButton rdbYes;
    private JRadioButton rdbNo;
    private JRadioButton rdbYes1;
    private JRadioButton rdbNo1;
    private JTextField txfIp;
    private JTextArea txaIp;

    public ViewInit (Frame parent) {
        //super (parent, "Initialize", true);

        ViewLogin login = new ViewLogin(parent);
        String username = login.run();

        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbWelcome = new JLabel("Welcome " + username);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbWelcome, cs);

        lbConfSnmp = new JLabel("Is SNMP configured on all devices?");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbConfSnmp, cs);

        rdbYes = new JRadioButton("Yes");
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(rdbYes, cs);

        rdbYes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (rdbYes.isSelected()){
                    JOptionPane.showMessageDialog(panel, "Okay, go on with setup... Remember to sudo service snmpd restart!");
                }
            }
        });

        rdbNo = new JRadioButton("No");
        cs.gridx = 2;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(rdbNo, cs);

        rdbNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (rdbNo.isSelected()){
                    JOptionPane.showMessageDialog(panel, "You should configure SNMP first! Exiting system...");
                    System.exit(1);
                }
            }
        });

        lbIpQues = new JLabel("Do you want to add Ip addresses?");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbIpQues, cs);

        rdbYes1 = new JRadioButton("Yes");
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(rdbYes1, cs);

        rdbYes1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (rdbYes1.isSelected()){
                    JOptionPane.showMessageDialog(panel, "Okay, please add Ip addresses one by one!");
                }
            }
        });

        rdbNo1 = new JRadioButton("No");
        cs.gridx = 2;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(rdbNo1, cs);

        rdbNo1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (rdbNo1.isSelected()){
                    JOptionPane.showMessageDialog(panel, "Okay, please proceed.");
                }
            }
        });

        lbIpGet = new JLabel("Ip Address: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbIpGet, cs);

        txfIp = new JTextField(16);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth =1;
        panel.add(txfIp, cs);

        txaIp = new JTextArea(10, 15);
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(txaIp, cs);

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        txaIp.append(txfIp.getText() + "\n");
                        txfIp.setText("");
                    }
                }
        );
        cs.gridy = 3;
        cs.gridx = 2;
        cs.gridwidth = 1;
        panel.add(btnAdd, cs);

        final ArrayList<String> ipAddresses = new ArrayList<String>();
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //parse input then send them to database
                String regexPattern = "\\S.+";
                Pattern pattern = Pattern.compile(regexPattern);
                String line;
                Matcher matcher = pattern.matcher(txaIp.getText());
                while (matcher.find()) {
                    ipAddresses.add((matcher.group().substring(0, matcher.group().length())));
                }
            }
        });
        cs.gridy = 4;
        cs.gridx = 1;
        panel.add(btnSubmit, cs);

        panel.setBorder(new LineBorder(Color.GRAY));
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(true);
        //setLocationRelativeTo(parent);
    }

    public static void main (String[] args){


        final JFrame frame = new JFrame("SNMPIDS");
        ViewInit viewInit = new ViewInit(frame);
        viewInit.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        //frame.setVisible(true);
    }


}
