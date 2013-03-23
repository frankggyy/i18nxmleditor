package com.whnec.i18n;

import java.util.Comparator;
import java.util.TreeSet;

public class I18nResource {

    private TreeSet<I18nItem> items;

    public TreeSet<I18nItem> getItems() {
        return items;
    }

    public void setItems(TreeSet<I18nItem> items) {
        this.items = items;
    }

    public static final TreeSet<I18nItem> collectionFactory() {
        return new TreeSet<I18nItem>(new Comparator<I18nItem>() {
            @Override
            public int compare(I18nItem o1, I18nItem o2) {
                if (null == o1)
                    if (null == o2)
                        return 0;
                    else
                        return 1;
                else if (null == o2)
                    return 1;
                else
                    return o1.getCode().compareTo(o2.getCode());
            }
        });
    }

}
