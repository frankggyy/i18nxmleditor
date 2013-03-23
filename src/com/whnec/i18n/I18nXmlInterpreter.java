package com.whnec.i18n;

import java.io.InputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class I18nXmlInterpreter {

    private static I18nXmlInterpreter instance = null;

    IUnmarshallingContext unmarshallingContext = null;

    private I18nXmlInterpreter() {
    }

    public static synchronized final I18nXmlInterpreter getInstance() {
        if (null == instance)
            instance = new I18nXmlInterpreter();
        return instance;
    }

    public I18nResource unmarshal(InputStream in, String encoding) throws JiBXException {
        return (I18nResource) getUnMarshallingContext().unmarshalDocument(in, encoding);
    }

    IUnmarshallingContext getUnMarshallingContext() throws JiBXException {
        if (null == unmarshallingContext)
            unmarshallingContext = BindingDirectory.getFactory(I18nResource.class).createUnmarshallingContext();
        return unmarshallingContext;
    }

}
