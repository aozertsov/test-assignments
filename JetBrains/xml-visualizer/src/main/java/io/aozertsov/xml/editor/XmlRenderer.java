package io.aozertsov.xml.editor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class XmlRenderer implements javax.swing.tree.TreeCellRenderer {
    private final JTextPane label;

    public XmlRenderer() {
        label = new JTextPane();
        label.setDragEnabled(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        String o = (String) ((DefaultMutableTreeNode) value).getUserObject();

        String text = "" + o;

        label.setText(text);
        return label;
    }
}
