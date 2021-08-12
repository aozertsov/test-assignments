package io.aozertsov.xml.psi;

import com.intellij.openapi.paths.PathReferenceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class NodeVisitor extends XmlElementVisitor {

    DefaultMutableTreeNode root;

    public NodeVisitor(DefaultMutableTreeNode root) {
        this.root = root;
    }

    @Override
    public void visitXmlTag(XmlTag tag) {
        super.visitXmlTag(tag);
        if (tag.getName().equals("node")) {
            DefaultMutableTreeNode entityNode;
            if (tag.getAttributeValue("src") != null) {
                var manager = PathReferenceManager.getInstance();
                var reference = (XmlFileImpl) manager.getPathReference(tag.getAttributeValue("src"), tag.getAttribute("src")).resolve();
                entityNode = new DefaultMutableTreeNode(tag);
                acceptChildren(reference.getRootTag(), entityNode);
            }
            else {
                entityNode = new DefaultMutableTreeNode(tag);
                acceptChildren(tag, entityNode);
            }
            root.add(entityNode);
        }
        else {
            for (PsiElement element : tag.getChildren()) {
                element.accept(this);
            }
        }
    }

    private void acceptChildren(XmlTag tag, DefaultMutableTreeNode parent) {
        for (PsiElement element : tag.getChildren()) {
            element.accept(new NodeVisitor(parent));
        }
    }

    @Override
    public void visitXmlFile(XmlFile file) {
        super.visitXmlFile(file);
        XmlDocument document = file.getDocument();
        List<XmlTag> tags = PsiTreeUtil.getChildrenOfTypeAsList(document, XmlTag.class);

        for (XmlTag tag : tags) {
            if (tag != null) {
                tag.accept(this);
            }
        }
    }
}
