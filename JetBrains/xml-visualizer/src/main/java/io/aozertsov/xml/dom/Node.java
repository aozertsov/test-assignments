package io.aozertsov.xml.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.TagValue;

public interface Node extends DomElement {

    @TagValue
    String getValue();

    @Attribute
    GenericAttributeValue<String> getId();
}
