import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class ViewLogin extends JDialog{

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    //private static String username = "root";
    //private static String password = "password";
    private String name;
    private int errorCounter = 0;

    public ViewLogin(Frame parent) throws IOException {

        super(parent, "Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");


        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                OntologyConnection connection = null;
                try {
                    connection = new OntologyConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    connection.uploadRDF(new File(connection.getWorkingDirectory()+
                                    "/snmpids.owl"), connection.serviceURIforData);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ArrayList<String> username = connection.execSelectAndProcess(
                        OntologyConnection.prefixSNMP + " \n " + OntologyConnection.prefixRDF +
                        "\nSELECT ?x WHERE {?x rdf:type snmp:Administrator.}");
                System.out.println(username.get(0));
                ArrayList<String> password = connection.execSelectAndProcess(
                        OntologyConnection.prefixSNMP + " \n " + OntologyConnection.prefixRDF +
                        "\nSELECT ?x WHERE {?y rdf:type snmp:Administrator.\n" +
                        "?y snmp:password ?x.}");
                System.out.println(password.get(0));

                if (getUsername().equals(username.get(0)) && getPassword().equals(password.get(0))) {
                    JOptionPane.showMessageDialog(ViewLogin.this,
                            "Hi " + getUsername() + "! You have successfully logged in.",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;

                    try {
                        dispose();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewLogin.this,
                            "Invalid username or password",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;
                    errorCounter++;
                    if (errorCounter==3){
                        JOptionPane.showMessageDialog(ViewLogin.this,
                                "Too many faulty attempts! Exiting system...",
                                "Login",
                                JOptionPane.ERROR_MESSAGE);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        System.exit(1);
                    }
                }
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void setName (String name) { this.name = name;}

    public String getName () { return name; }

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String run() {
        final JFrame frame = new JFrame("SNMPIDS");
        final JButton btnLogin = new JButton("Click to login");

        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        ViewLogin loginDlg = null;
                        try {
                            loginDlg = new ViewLogin(frame);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        loginDlg.setVisible(true);

                        // if logon successfully
                        if(loginDlg.isSucceeded()){
                            ViewInit viewInit = null;
                            try {
                                OntologyConnection connection = new OntologyConnection();
                                ArrayList<String> username = connection.execSelectAndProcess(OntologyConnection.prefixSNMP + "\n" + OntologyConnection.prefixRDF +
                                        "\nSELECT ?x WHERE {?x rdf:type snmp:Administrator.}");
                                viewInit = new ViewInit(frame, username.get(0));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            viewInit.setVisible(true);
                            loginDlg.setVisible(false);
                            frame.setVisible(false);
                            frame.dispose();
                            try {
                                dispose();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);

        if (isSucceeded())
            frame.setVisible(false);

        return getUsername();
    }

    public static void main (String[] args) {
        final JFrame frame = new JFrame("SNMPIDS");
        //final JButton btnLogin = new JButton("Click to login");

        ViewLogin viewLogin = null;
        try {
            viewLogin = new ViewLogin(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewLogin.run();

        /*btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        Login loginDlg = new Login(frame);
                        loginDlg.setVisible(true);
                        // if logon successfully
                        if(loginDlg.isSucceeded()){
                            btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
                        }
                    }
                });*/

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300, 100);
        //frame.setLayout(new FlowLayout());
        //frame.getContentPane().add(btnLogin);
        ///frame.setVisible(true);
    }
}

