package io.aozertsov.xml.editor;

import com.intellij.psi.xml.XmlTag;

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
        XmlTag tag = (XmlTag) ((DefaultMutableTreeNode) value).getUserObject();
        String text;

        if (tag.getAttributeValue("src") != null) {
            text = "imported from: " + tag.getAttributeValue("src");
        }
        else if (tag.getAttributeValue("title") != null) {
            text = tag.getAttributeValue("title");
        }
        else {
            text = tag.getValue().getText();
        }
        label.setText(text);
        return label;
    }
}
