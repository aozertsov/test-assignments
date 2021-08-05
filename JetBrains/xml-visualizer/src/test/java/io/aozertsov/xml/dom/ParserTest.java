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


}
