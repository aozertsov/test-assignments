package io.aozertsov.xml.util;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public abstract class XmlHelper {

    public static boolean isXmlFile(Project project, VirtualFile file) {
        if (project == null || file == null) {
            return false;
        }
        final Language language = LanguageUtil.getLanguageForPsi(project, file);
        return language != null && language.isKindOf(XMLLanguage.INSTANCE);
    }
}
