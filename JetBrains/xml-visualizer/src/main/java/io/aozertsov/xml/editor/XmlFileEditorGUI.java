package io.aozertsov.xml.editor;

import com.intellij.openapi.vfs.AsyncFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import io.aozertsov.xml.dom.Root;
import io.aozertsov.xml.psi.NodeVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class XmlFileEditorGUI extends BasicDomElementComponent<Root> implements AsyncFileListener {

    private JPanel editorWindowContent;
    private JTree rootTree;
    private XmlFileImpl xmlFile;

    public XmlFileEditorGUI(XmlFileImpl xml, Root domElement) {
        super(domElement);
        this.xmlFile = xml;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(xmlFile.getRootTag());
        initTree(top);
    }

    @Override
    public JComponent getComponent() {
        return editorWindowContent;
    }

    private void initTree(DefaultMutableTreeNode top) {
        xmlFile.accept(new NodeVisitor(top));
        rootTree.setModel(new DefaultTreeModel(top));
        rootTree.setRootVisible(false);
        rootTree.setCellRenderer(new XmlRenderer());
    }

    @Override
    public @Nullable ChangeApplier prepareChange(@NotNull List<? extends @NotNull VFileEvent> events) {
        return new ChangeApplier() {
            @Override
            public void afterVfsChange() {
                rootTree.setModel(null);
                DefaultMutableTreeNode top = new DefaultMutableTreeNode(xmlFile.getRootTag());
                initTree(top);
                editorWindowContent.updateUI();
            }
        };
    }
}
