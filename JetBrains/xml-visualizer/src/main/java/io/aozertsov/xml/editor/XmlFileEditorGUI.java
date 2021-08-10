package io.aozertsov.xml.editor;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import io.aozertsov.xml.dom.Root;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class XmlFileEditorGUI extends BasicDomElementComponent<Root> implements TreeSelectionListener {

    private JPanel editorWindowContent;
    private JTextPane textPane;

    public XmlFileEditorGUI(Root domElement) {
        super(domElement);
        textPane.setText("Init component");
    }

    @Override
    public JComponent getComponent() {
        return editorWindowContent;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {

    }
}
