package rmi2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class textareatest {
        public static void main(String[] args) {
                textareaframe frame = new textareaframe();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.show();
        }
}

class textareaframe extends JFrame {
        public textareaframe() {
                setTitle("textareatest");
                setSize(width, height);

                Container contentpane = getContentPane();

                buttonpane = new JPanel();

                JButton insertbutton = new JButton("insert");
                buttonpane.add(insertbutton);
                insertbutton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                                textArea.append("the fox jumps over the lazy dog.");
                        }
                });

                wrapbutton = new JButton("wrap");
                buttonpane.add(wrapbutton);
                wrapbutton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                                boolean wrap = !textArea.getLineWrap();
                                textArea.setLineWrap(wrap);
                                scrollpane.validate();
                                wrapbutton.setText(wrap ? "not wrap" : "wrap");
                        }
                });

                contentpane.add(buttonpane, BorderLayout.SOUTH);

                textArea = new JTextArea(8, 40);
                scrollpane = new JScrollPane(textArea);
                contentpane.add(scrollpane, BorderLayout.CENTER);

        }

        public static final int width = 300;

        public static final int height = 400;

        private JTextArea textArea;

        private JScrollPane scrollpane;

        private JPanel buttonpane;

        private JButton wrapbutton;
}
