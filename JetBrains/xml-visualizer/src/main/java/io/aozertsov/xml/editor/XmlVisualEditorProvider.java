package io.aozertsov.xml.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.ui.DomFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditor;
import com.intellij.util.xml.ui.PerspectiveFileEditorProvider;
import io.aozertsov.xml.dom.Root;
import io.aozertsov.xml.util.XmlHelper;
import org.jetbrains.annotations.NotNull;

public class XmlVisualEditorProvider extends PerspectiveFileEditorProvider {


    @Override
    public @NotNull PerspectiveFileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        DomManager domManager = DomManager.getDomManager(project);
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        Root rootElement = domManager.getFileElement((XmlFile) psiFile, Root.class).getRootElement();

        var editor = new XmlFileEditorGUI((XmlFileImpl)psiFile, rootElement);
        VirtualFileManager.getInstance().addAsyncFileListener(editor, editor);
        return new DomFileEditor<>(project, file, "GUI", editor);
    }


    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        if (XmlHelper.isXmlFile(project, file)) {
            DomManager domManager = DomManager.getDomManager(project);
            PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
            var fileElement = domManager.getFileElement((XmlFile) psiFile, Root.class);
            return fileElement != null;
        }
        return false;
    }
}
