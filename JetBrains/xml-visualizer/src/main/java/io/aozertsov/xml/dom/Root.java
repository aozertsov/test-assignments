package io.aozertsov.xml.dom;

import com.intellij.util.xml.DomElement;

import java.util.List;

public interface Root extends DomElement {

    List<Node> getNodes();
}
