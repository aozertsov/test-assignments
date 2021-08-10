package io.aozertsov.xml.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ImportNode extends Node {

    @Attribute("src")
    @NotNull
    @Required(value = false, nonEmpty = true)
    @Convert(value = SourceConverter.class, soft = true)
    GenericAttributeValue<List<Node>> getSource();

    @Override
    default String getValue() {
        return getSource().getXmlTag().getAttribute("src").getValue();
    }
}
