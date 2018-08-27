package org.succlz123.androidpixeldimengenerator.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by succlz123 on 16/6/22.
 */
public class DimenElement {
    public String nameType;
    public int start;
    public int end;

    public List<Dimen> generate(float scale) {
        List<Dimen> list = new ArrayList<>();

        if (start > 0) {
            for (int value = start; value <= end; value++) {
                Dimen dimen = internalGenerate(scale, value);
                list.add(dimen);
            }
        } else {
            for (int value = start; value >= end; value--) {
                Dimen dimen = internalGenerate(scale, value);
                list.add(dimen);
            }
        }

        return list;
    }

    protected Dimen internalGenerate(float scale, int value) {
        float target = value * scale;
        Dimen dimen = new Dimen();
        dimen.value = target;
        dimen.name = nameType + value;
        return dimen;
    }
}