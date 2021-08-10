package io.aozertsov.xml.dom;

import com.intellij.openapi.paths.PathReferenceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.Converter;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SourceConverter extends Converter<List<Node>> {
    @Override
    public @Nullable List<Node> fromString(@Nullable @NonNls String s, ConvertContext context) {
        final XmlElement element = context.getXmlElement();
        PsiElement reference = null;
        if (s != null && element != null) {
            var manager = PathReferenceManager.getInstance();
            reference = manager.getPathReference(s, element).resolve();
        }
        var file = reference instanceof XmlFileImpl ? (XmlFileImpl) reference : null;

        var manager = DomManager.getDomManager(context.getProject());
        return manager.getFileElement(file, Root.class).getRootElement().getNodes();
    }

    @Override
    public @Nullable String toString(@Nullable List<Node> root, ConvertContext context) {
        return null;
    }
}
