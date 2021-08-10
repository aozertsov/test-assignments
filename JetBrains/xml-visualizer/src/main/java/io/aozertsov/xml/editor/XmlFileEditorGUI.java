package io.aozertsov.xml.editor;

import com.intellij.util.xml.ui.BasicDomElementComponent;
import io.aozertsov.xml.dom.Node;
import io.aozertsov.xml.dom.Root;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class XmlFileEditorGUI extends BasicDomElementComponent<Root> implements TreeSelectionListener {

    private JPanel editorWindowContent;
    private JTree rootTree;

    public XmlFileEditorGUI(Root domElement) {
        super(domElement);
        initTree();
    }

    @Override
    public JComponent getComponent() {
        return editorWindowContent;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {

    }

    private void initTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode();
        createNodes(top, getDomElement().getNodes());
        rootTree.setModel(new DefaultTreeModel(top));
        rootTree.setRootVisible(false);
        rootTree.setCellRenderer(new XmlRenderer());
    }

    private void createNodes(DefaultMutableTreeNode treeNode, List<Node> nodes) {
        for (Node node : nodes) {
            DefaultMutableTreeNode entityNode = new DefaultMutableTreeNode(node);
            treeNode.add(entityNode);
            createNodes(entityNode, node.getChildren());
        }
    }
}
