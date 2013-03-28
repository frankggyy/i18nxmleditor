/**
 * 
 */
package com.whnec.tool.eclipse.plugin.i18nxmleditor.service;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

/**
 * @author tiger
 * 
 */
public class I18nXmlInterpreterServiceFactory extends AbstractServiceFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.services.AbstractServiceFactory#create(java.lang.Class,
     * org.eclipse.ui.services.IServiceLocator,
     * org.eclipse.ui.services.IServiceLocator)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object create(Class serviceInterface, IServiceLocator parentLocator, IServiceLocator locator) {
        if (!I18nXmlInterpreterService.class.equals(serviceInterface))
            return null;
        Object service = parentLocator.getService(serviceInterface);
        if (null != service)
            return service;
        return new I18nXmlInterpreterServiceImpl();
    }

}
