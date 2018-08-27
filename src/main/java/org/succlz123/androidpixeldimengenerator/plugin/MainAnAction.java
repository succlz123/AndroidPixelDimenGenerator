package org.succlz123.androidpixeldimengenerator.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by succlz123 on 16/6/14.
 */
public class MainAnAction extends AnAction {
    public static final String PROJECT_NAME = "AndroidPixelDimenGenerator";

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);

//        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
//        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
//        Application application = ApplicationManager.getApplication();
//        MyApplicationComponent myApplicationComponent = application.getComponent(MyApplicationComponent.class);

        MyDialog myDialog = new MyDialog(project);
        myDialog.show();
        if (myDialog.isOK()) {
            Messages.showMessageDialog(project, "生成像素dimen文件成功\nGenerate pixel dimen file success",
                    PROJECT_NAME, Messages.getInformationIcon());
        }
//        String txt = Messages.showMultilineInputDialog(project,
//                "请按照示例添加所需的分辨率",
//                "AutoGeneratePixelDimen",
//                "1920 1080\n1280 720", Messages.getQuestionIcon(), null);
    }
}
