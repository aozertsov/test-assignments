package io.aozertsov.xml.editor;

import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import io.aozertsov.xml.dom.Node;
import io.aozertsov.xml.dom.Root;
import io.aozertsov.xml.psi.NodeVisitor;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class XmlFileEditorGUI extends BasicDomElementComponent<Root> implements TreeSelectionListener {

    private JPanel editorWindowContent;
    private JTree rootTree;
    private XmlFileImpl xmlFile;

    public XmlFileEditorGUI(XmlFileImpl xml, Root domElement) {
        super(domElement);
        this.xmlFile = xml;
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
        xmlFile.accept(new NodeVisitor(top));
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
