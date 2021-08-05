package io.aozertsov.xml.dom;

import com.intellij.util.xml.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Node extends DomElement {

    @TagValue
    String getValue();
}
