package io.weli.sisu;

import jakarta.inject.Named;
import javax.swing.*;

@Named("Two")
class CheckBoxTab extends AbstractTab {
    CheckBoxTab() {
        add(new JCheckBox("CheckBox"));
    }
}
