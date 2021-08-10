package io.aozertsov.xml.dom;

import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.xml.TypeChooser;

import java.lang.reflect.Type;


public class NodeChooser extends TypeChooser {
    @Override
    public Type chooseType(XmlTag tag) {
        if (tag.getChildren().length > 0)
            return ListNode.class;
        return Node.class;
    }

    @Override
    public void distinguishTag(XmlTag tag, Type aClass) throws IncorrectOperationException {
        tag.setAttribute("node", ((Class)aClass).getName());
    }

    @Override
    public Type[] getChooserTypes() {
        return ArrayUtil.EMPTY_CLASS_ARRAY;
    }
}
