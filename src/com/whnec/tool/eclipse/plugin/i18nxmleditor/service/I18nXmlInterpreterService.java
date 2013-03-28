/**
 * 
 */
package com.whnec.tool.eclipse.plugin.i18nxmleditor.service;

import java.io.InputStream;
import java.io.Reader;

import com.whnec.i18n.I18nResource;

/**
 * Supply unmarshal service for {@linkplain I18nResource}.
 * @author tiger
 * 
 */
public interface I18nXmlInterpreterService {

    /**
     * Unmarshal document from stream to {@linkplain I18nResource} Object.
     * 
     * @param in
     *            Stream supplying document data
     * @param encoding
     *            encoding of input document, or null if to be determined by
     *            parser
     * @return Unmarshalled object
     * @throws InterpretException
     *             Any error on unmarshalling document
     */
    I18nResource unmarshal(final InputStream in, final String encoding) throws InterpretException;

    /**
     * Unmarshal document from reader to {@linkplain I18nResource} Object.
     * 
     * @param in
     *            Reader supplying document data
     * @param encoding
     *            encoding of input document, or null if to be determined by
     *            parse
     * @return Unmarshalled object
     * @throws InterpretException
     *             Any error on unmarshalling document
     */
    I18nResource unmarshal(final Reader in, final String encoding) throws InterpretException;

}
