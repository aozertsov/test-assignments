package io.aozertsov.xml.dom;

import com.intellij.psi.xml.XmlFile;
import com.intellij.testFramework.LightPlatformTestCase;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.xml.CallRegistry;
import com.intellij.util.xml.DomEventListener;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.events.DomEvent;
import com.intellij.util.xml.impl.DomManagerImpl;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.Test;


public class ParserTest extends LightPlatformTestCase {

    protected CallRegistry<DomEvent> myCallRegistry;
    private final DomEventListener myListener = new DomEventListener() {
        @Override
        public void eventOccured(@NotNull DomEvent event) {
            myCallRegistry.putActual(event);
        }
    };

    @Override
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        myCallRegistry = new CallRegistry<>();
        getDomManager().addDomEventListener(myListener, getTestRootDisposable());
    }

    protected XmlFile createXmlFile(@NonNls final String text) throws IncorrectOperationException {
        return (XmlFile)createLightFile("a.xml", text);
    }

    protected DomManagerImpl getDomManager() {
        return (DomManagerImpl) DomManager.getDomManager(getProject());
    }

    @Test
    public void testEmptyFile() {
        final XmlFile file = createXmlFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "</root>");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().isEmpty(), true);
    }

    @Test
    public void testSingleNode() {
        final XmlFile file = createXmlFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <node id=\"node1\">That is a simple node</node>\n" +
                "</root>\n");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().size(), 1);

        var value = root.getNodes().get(0).getValue();
        assertEquals("That is a simple node", value);
    }

    @Test
    public void testAttribute() {
        final XmlFile file = createXmlFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <node id=\"node1\">That is a simple node</node>\n" +
                "</root>\n");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().size(), 1);

        var value = root.getNodes().get(0).getValue();
        var id = root.getNodes().get(0).getId().getValue();
        assertEquals("That is a simple node", value);
        assertEquals("node1", id);
    }

    @Test
    public void testListNode() {
        final XmlFile file = createXmlFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <node id=\"node1\">That is a simple node</node>\n" +
                "    <node id=\"node2\">That is another simple node</node>\n" +
                "    <node id=\"node3\" title=\"Not so simple node\">\n" +
                "        <node id=\"node5\">So a child node</node>\n" +
                "        <node id=\"node1\" title=\"Some child with other children\">\n" +
                "            <node id=\"node1\">Just a child node</node>\n" +
                "            <node id=\"node2\">Some other child node</node>\n" +
                "        </node>\n" +
                "\n" +
                "    </node>\n" +
                "</root>\n");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(3, root.getNodes().size());

        var value = root.getNodes().get(2);
        assertTrue(value instanceof ListNode);

        var children = ((ListNode)value).getNodes();
        assertEquals("Not so simple node", ((ListNode)value).getTittle().getValue());
        assertEquals(2, children.size());
        assertEquals(2, ((ListNode)children.get(1)).getNodes().size());
    }


}
