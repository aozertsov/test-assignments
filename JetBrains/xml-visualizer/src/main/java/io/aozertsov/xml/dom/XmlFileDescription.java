package io.aozertsov.xml.dom;

import com.intellij.util.xml.DomFileDescription;

public class XmlFileDescription extends DomFileDescription<Root> {
    public XmlFileDescription() {
        super(Root.class, "root");
    }

    @Override
    protected void initializeFileDescription() {
        registerTypeChooser(Node.class, new NodeChooser());
    }
}
