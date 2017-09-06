package DB;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class test extends JFrame implements ActionListener{

  JComboBox startCombo;
  JComboBox endCombo;
  JLabel label;

  public static void main(String[] args){
    test frame = new test();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 300, 200);
    frame.setTitle("タイトル");
    frame.setVisible(true);
  }

  test(){
    String[] startTime = {"08:00","09:00","10:00","11:00",
                          "12:00","13:00","14:00","15:00",
                          "16:00","17:00","18:00","19:00"};

    String[] endTime = {"08:00","09:00","10:00","11:00",
                          "12:00","13:00","14:00","15:00",
                          "16:00","17:00","18:00","19:00"};

    startCombo = new JComboBox(startTime);
    startCombo.setPreferredSize(new Dimension(80, 30));

    startCombo.addActionListener(this);

    endCombo = new JComboBox(endTime);
    endCombo.setPreferredSize(new Dimension(80, 30));

    endCombo.addActionListener(this);

    JPanel p = new JPanel();
    p.add(new JLabel("start:"));
    p.add(startCombo);
    p.add(new JLabel("  end:"));
    p.add(endCombo);

    label = new JLabel();
    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    getContentPane().add(p, BorderLayout.CENTER);
    getContentPane().add(labelPanel, BorderLayout.PAGE_END);
  }

  public void actionPerformed(ActionEvent e) {
    String start;
    String end;

    if (startCombo.getSelectedIndex() == -1){
        start = "(not select)";
    }else{
        start = (String)startCombo.getSelectedItem();
    }

    if (endCombo.getSelectedIndex() == -1){
        end = "(not select)";
    }else{
        end = (String)endCombo.getSelectedItem();
    }

    label.setText("START:" + start + ", END:" + end);
  }
}

