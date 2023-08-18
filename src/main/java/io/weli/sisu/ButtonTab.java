package io.weli.sisu;

import javax.inject.Named;
import javax.swing.*;

@Named("One")
class ButtonTab extends AbstractTab {
    ButtonTab() {
        add(new JButton("Button"));
    }
}
