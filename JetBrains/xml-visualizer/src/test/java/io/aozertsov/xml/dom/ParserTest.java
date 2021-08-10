package io.aozertsov.xml.dom;

import com.intellij.psi.xml.XmlFile;
import com.intellij.testFramework.JavaPsiTestCase;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.impl.DomManagerImpl;
import org.jetbrains.annotations.NonNls;
import org.junit.Before;
import org.junit.jupiter.api.Test;


public class ParserTest extends JavaPsiTestCase {


    @Override
    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected XmlFile createXmlFile(String filename, @NonNls final String text) throws Exception {
        return (XmlFile)createFile(filename, text);
    }

    protected DomManagerImpl getDomManager() {
        return (DomManagerImpl) DomManager.getDomManager(getProject());
    }

    @Test
    public void testEmptyFile() throws Exception {
        final XmlFile file = createXmlFile("a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "</root>");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().isEmpty(), true);
    }

    @Test
    public void testSingleNode() throws Exception {
        final XmlFile file = createXmlFile("a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <node id=\"node1\">That is a simple node</node>\n" +
                "</root>\n");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().size(), 1);

        var node = root.getNodes().get(0);
        assertEquals("That is a simple node", node.getValue());
        assertEquals(0, node.getChildren().size());
    }

    @Test
    public void testAttribute() throws Exception {
        final XmlFile file = createXmlFile("a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "    <node id=\"node1\">That is a simple node</node>\n" +
                "</root>\n");
        final DomManagerImpl manager = getDomManager();


        Root root = manager.getFileElement(file, Root.class).getRootElement();


        assertEquals(root.getNodes().size(), 1);

        var node = root.getNodes().get(0);
        assertEquals("That is a simple node", node.getValue());
        assertEquals("node1", node.getId().getValue());
    }

    @Test
    public void testListNode() throws Exception {
        final XmlFile file = createXmlFile("a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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

        var listValue = root.getNodes().get(2);
        assertTrue(listValue instanceof ListNode);

        assertEquals(2, listValue.getChildren().size());

        var children = listValue.getChildren();
        assertEquals("Not so simple node", ((ListNode)listValue).getTittle().getValue());
        assertEquals(2, children.size());
        assertEquals(2, children.get(1).getChildren().size());
    }

    @Test
    public void testImportNode() throws Exception {

        final XmlFile file = createXmlFile("a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                "    <node id=\"node1\">That is a simple node with dupe id</node>\n" +
                "    <node src=\"other-sample-file.xml\" id=\"otherId\"/>\n" +
                "    <node id=\"node4\">That is yet another simple node</node>\n" +
                "</root>\n");

        final XmlFile otherSampleFile = createXmlFile("other-sample-file.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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

        var importNode = root.getNodes().get(4);
        var source = importNode.getValue();

        assertEquals(3, importNode.getChildren().size());
        assertEquals("other-sample-file.xml", source);
    }


}
