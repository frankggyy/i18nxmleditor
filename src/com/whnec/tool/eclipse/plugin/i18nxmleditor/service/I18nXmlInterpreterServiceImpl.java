package com.whnec.tool.eclipse.plugin.i18nxmleditor.service;

import java.io.InputStream;
import java.io.Reader;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import com.whnec.i18n.I18nResource;

public class I18nXmlInterpreterServiceImpl implements I18nXmlInterpreterService {

    IUnmarshallingContext unmarshallingContext = null;

    public I18nXmlInterpreterServiceImpl() {
    }

    public I18nResource unmarshal(InputStream in, String encoding) throws InterpretException {
        try {
            return (I18nResource) getUnMarshallingContext().unmarshalDocument(in, encoding);
        } catch (JiBXException e) {
            throw new InterpretException("Exception on unmarshalling document from input stream using encoding: "
                    + encoding, e);
        }
    }

    public I18nResource unmarshal(Reader in, String encoding) throws InterpretException {
        try {
            return (I18nResource) getUnMarshallingContext().unmarshalDocument(in, encoding);
        } catch (JiBXException e) {
            throw new InterpretException("Exception on unmarshalling document from input reader using encoding: "
                    + encoding, e);
        }
    }

    IUnmarshallingContext getUnMarshallingContext() throws JiBXException {
        if (null == unmarshallingContext)
            unmarshallingContext = BindingDirectory.getFactory(I18nResource.class).createUnmarshallingContext();
        return unmarshallingContext;
    }

}
