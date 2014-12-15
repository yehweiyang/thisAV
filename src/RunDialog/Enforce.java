package RunDialog;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Enforce extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Enforce frame = new Enforce();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Enforce() {
        setTitle("ThisAv下載器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("網址");
        label.setBounds(10, 115, 32, 15);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(52, 112, 372, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton button = new JButton("下載");
        button.setBounds(158, 198, 87, 23);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    do_button_actionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(button);
    }

    // button觸發
    protected void do_button_actionPerformed(ActionEvent e) throws Exception {
        // 判斷結尾是否有斜線20120423
        ArrayList<String> myList = new ArrayList<String>();
        myList.add(textField.getText());
        int a = myList.get(0).length() - 1;
        String path = textField.getText();
        if (!myList.get(0).substring(a).equals("/")) {
            path = textField.getText() + "/";
        }
        if (!myList.get(0).substring(0, 7).equals("http://")) {
            path = "http://" +path;
        }
      
        RunDialogDeemo.getPageData(path);
    }
}
