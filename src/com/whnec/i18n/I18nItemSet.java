package com.whnec.i18n;

import java.util.Comparator;
import java.util.TreeSet;

public class I18nItemSet extends TreeSet<I18nItem> {

    private static final long serialVersionUID = -2586405054570622601L;

    private I18nItemComparator comparator = null;

    @Override
    public Comparator<? super I18nItem> comparator() {
        if (null == comparator) {
            comparator = new I18nItemComparator();
        }
        return comparator;
    }

    public static class I18nItemComparator implements Comparator<I18nItem> {

        @Override
        public int compare(I18nItem o1, I18nItem o2) {
            if (null != o1) {
                if (null != o2) {
                    return o1.getCode().compareTo(o2.getCode());
                }
            }
            return -1;
        }

    }

}
