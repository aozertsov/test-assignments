package io.aozertsov.xml.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.TagValue;

import java.util.List;

public interface Node extends DomElement {

    @TagValue
    String getValue();

    @Attribute
    GenericAttributeValue<String> getId();

    List<Node> getChildren();
}
