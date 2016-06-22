package org.succlz123.androidpixeldimengenerator.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by succlz123 on 16/6/22.
 */
public class Values {
    public int width;
    public int height;

    public float scale;

    public File dimenFile;

    public List<DimenElement> elements;
    public List<Dimen> dimens;

    public Values(int width, int height) {
        this.width = width;
        this.height = height;
        this.scale = (float) width / 1920;

        elements = new ArrayList<>();
        dimens = new ArrayList<>();
    }

    public void addDimenElement(DimenElement element) {
        elements.add(element);
    }

    public void generate() {
        for (DimenElement element : elements) {
            List<Dimen> generate = element.generate(scale);
            dimens.addAll(generate);
        }
     }
}
