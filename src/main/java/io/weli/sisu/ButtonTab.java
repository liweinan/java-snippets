package io.weli.sisu;

import jakarta.inject.Named;
import javax.swing.*;

@Named("One")
class ButtonTab extends AbstractTab {
    ButtonTab() {
        add(new JButton("Button"));
    }
}
