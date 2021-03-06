package io.aozertsov.xml.dom;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;

import java.util.List;

public interface ListNode extends Node {
    List<Node> getNodes();

    @Attribute("title")
    GenericAttributeValue<String> getTittle();

    @Override
    default List<Node> getChildren() {
        return this.getNodes();
    }

    @Override
    default String getValue() {
        return this.getTittle().getValue();
    }
}
