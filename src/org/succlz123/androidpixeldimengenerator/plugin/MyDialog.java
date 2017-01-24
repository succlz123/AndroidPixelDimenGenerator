package org.succlz123.androidpixeldimengenerator.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.succlz123.androidpixeldimengenerator.generator.DimenElement;
import org.succlz123.androidpixeldimengenerator.generator.SAXGenerator;
import org.succlz123.androidpixeldimengenerator.generator.Values;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by succlz123 on 16/6/15.
 */
public class MyDialog extends DialogWrapper {
    private Project project;
    private InputPanel inputPanel;

    protected MyDialog(@Nullable Project project) {
        super(project);
        this.project = project;
        super.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        inputPanel = new InputPanel();
        return inputPanel.getComponent();
    }

    @Override
    protected void doOKAction() {
        String inputText = inputPanel.getTextPixel();

        if (inputText == null || inputText.isEmpty()) {
            showMessageDialog("输入的内容为空\nText is empty");
            return;
        }

        List<Values> valuesList = parseInputText(inputText);
        if (valuesList == null) {
            return;
        }

        String firstOutput = "";
        VirtualFile baseFile = project.getBaseDir();

        VirtualFile[] childFiles = baseFile.getChildren();

        if (childFiles.length > 0) {
            firstOutput = getOutputPath(childFiles);
            if (firstOutput == null) {
                firstOutput = baseFile.getCanonicalPath();
            }
        } else {
            firstOutput = baseFile.getCanonicalPath();
        }

        int positiveEnd = 1000;
        int negativeEnd = -100;

        try {
            positiveEnd = Integer.valueOf(inputPanel.getPositiveNumber());
            negativeEnd = Integer.valueOf(inputPanel.getNegativeNumber());
        } catch (NumberFormatException ignored) {
        }

        genValuesFiles(valuesList, positiveEnd, negativeEnd, firstOutput);

        super.doOKAction();
    }

    private List<Values> parseInputText(String inputText) {
        List<Values> valueList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        String[] resolutionArray = Pattern.compile("[\n]").split(inputText);
        for (String s : resolutionArray) {
            String[] whArray = Pattern.compile("[ ]").split(s);
            if (whArray.length == 2) {
                int width = 0;
                int height = 0;
                try {
                    width = Integer.valueOf(whArray[0]);
                    height = Integer.valueOf(whArray[1]);
                    if (width == 0 || height == 0) {
                        errorList.add(whArray[0] + "x" + whArray[1]);
                        break;
                    }

                    Values values = new Values(width, height);
                    valueList.add(values);
                } catch (NumberFormatException e) {
                    errorList.add(whArray[0] + "x" + whArray[1]);
                    break;
                }
            }
        }

        if (errorList.size() > 0) {
            String errorString = "";
            for (String s : errorList) {
                errorString += "\n" + s;
            }
            showMessageDialog("输入的分辨率不合法\nThe resolution of the input is illegal" + errorString);
            return null;
        }

        return valueList;
    }

    private String getOutputPath(VirtualFile[] virtualFiles) {
        for (VirtualFile virtualFile : virtualFiles) {
            String name = virtualFile.getName();
            VirtualFile[] childVirtualFile = virtualFile.getChildren();

            if (name.equals("res")) {
                return virtualFile.getCanonicalPath();
            } else if (childVirtualFile.length > 0) {
                String resPath = getOutputPath(childVirtualFile);
                if (resPath != null) {
                    return resPath;
                }
            }
        }
        return null;
    }

    private void genValuesFiles(List<Values> values, int positiveEnd, int negativeEnd, String ouput) {
        SAXGenerator saxGenerator = new SAXGenerator();

        DimenElement positiveDimenElement = new DimenElement();
        positiveDimenElement.nameType = "px_";
        positiveDimenElement.start = 1;
        positiveDimenElement.end = positiveEnd;

        DimenElement negativeDimenElement = new DimenElement();
        negativeDimenElement.nameType = "px_minus_";
        negativeDimenElement.start = 1;
        negativeDimenElement.end = Math.abs(negativeEnd);

        for (Values value : values) {
            String fileName = genFilesName(value);
            String dicPath = "";
            if (ouput.endsWith(File.separator)) {
                dicPath = ouput + fileName + File.separator;
            } else {
                dicPath = ouput + File.separator + fileName + File.separator;
            }

            File dic = new File(dicPath);
            if (dic.exists()) {
                dic.delete();
            }
            dic.mkdirs();
            String dimenFile = dicPath + "px_dimen.xml";
            File dimen = new File(dimenFile);
            if (dimen.exists()) {
                dimen.delete();
            }
            try {
                dimen.createNewFile();

                value.dimenFile = dimen;
                value.addDimenElement(positiveDimenElement);
                value.addDimenElement(negativeDimenElement);

                value.generate();
                saxGenerator.generate(value);
            } catch (IOException e) {
                showMessageDialog("无法写入文件\nCan not create dimen file\n" + dicPath);
                System.exit(-1);
            }
        }
    }

    private static String genFilesName(Values values) {
        return "values-nodpi-" + values.width + "x" + values.height;
    }

    private void showMessageDialog(String message) {
        Messages.showErrorDialog(project, message, MainAnAction.PROJECT_NAME);
    }
}
