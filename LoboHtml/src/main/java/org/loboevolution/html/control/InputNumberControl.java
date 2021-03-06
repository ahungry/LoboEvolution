package org.loboevolution.html.control;


import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLBaseInputElement;

public class InputNumberControl extends BaseInputTextControl {

    private static final long serialVersionUID = 1L;

    private JTextField numeric;
    private String min = "";
    private String max = "";

    public InputNumberControl(HTMLBaseInputElement modelNode) {
        super(modelNode);
        numeric = (JTextField) this.widget;
        String value = modelNode.getValue();
        min = modelNode.getAttribute("min");
        max = modelNode.getAttribute("max");
        if (!isNumeric(value)) {
            numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        numeric.addKeyListener(addKeyListener());
    }

    @Override
    protected JTextComponent createTextField() {
        return new JTextField();
    }

    private KeyListener addKeyListener() {
        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                JTextField num = (JTextField) keyEvent.getSource();
                if (!isNumeric(num.getText())) {
                    numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    if (Strings.isNotBlank(min) && Strings.isNotBlank(max)) {
                        try {
                            int intText = new Integer(num.getText());
                            int intMin = new Integer(min);
                            int intMax = new Integer(max);

                            if (intText < intMin || intText > intMax) {
                                numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
                            } else {
                                numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            }
                        } catch (NumberFormatException ex) {
                            numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
                        }
                    }
                }
            }
        };
        return keyListener;
    }

    private boolean isNumeric(String keyCode) {
        try {
            if (keyCode == null || (keyCode != null && keyCode.length() == 0))
                return true;
            Integer.parseInt(keyCode);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
