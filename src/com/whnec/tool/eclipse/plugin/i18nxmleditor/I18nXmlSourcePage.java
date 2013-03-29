/**
 * 
 */
package com.whnec.tool.eclipse.plugin.i18nxmleditor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

/**
 * @author tiger
 * 
 */
public class I18nXmlSourcePage extends TextEditor implements IFormPage {

    private FormEditor fFormEditor;

    private int fIndex;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.editor.IFormPage#initialize(org.eclipse.ui.forms
     * .editor.FormEditor)
     */
    @Override
    public void initialize(FormEditor editor) {
        this.fFormEditor = editor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#getEditor()
     */
    @Override
    public FormEditor getEditor() {
        return fFormEditor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#getManagedForm()
     */
    @Override
    public IManagedForm getManagedForm() {
        // not a form, actually a text editor
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#setActive(boolean)
     */
    @Override
    public void setActive(boolean active) {
        setFocus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#isActive()
     */
    @Override
    public boolean isActive() {
        return equals(fFormEditor.getActivePageInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#canLeaveThePage()
     */
    @Override
    public boolean canLeaveThePage() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#getPartControl()
     */
    @Override
    public Control getPartControl() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#getId()
     */
    @Override
    public String getId() {
        return getEditorInput().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#getIndex()
     */
    @Override
    public int getIndex() {
        return fIndex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#setIndex(int)
     */
    @Override
    public void setIndex(int index) {
        fIndex = index;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#isEditor()
     */
    @Override
    public boolean isEditor() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.IFormPage#selectReveal(java.lang.Object)
     */
    @Override
    public boolean selectReveal(Object object) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#createPartControl
     * (org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent) {
        // no need;
    }

}
