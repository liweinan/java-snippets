package io.weli.sisu;

import javax.inject.Named;
import javax.swing.*;
import java.util.Enumeration;

@Named("Three")
class RadioButtonTab extends AbstractTab {
    RadioButtonTab() {
        ButtonGroup group = new ButtonGroup();
        group.add(new JRadioButton("+1"));
        group.add(new JRadioButton("0"));
        group.add(new JRadioButton("-1"));
        Enumeration<AbstractButton> e = group.getElements();
        while (e.hasMoreElements()) {
            add(e.nextElement());
        }
    }
}
