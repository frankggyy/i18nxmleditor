package com.whnec.tool.eclipse.plugin.i18nxmleditor;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.FileEditorInput;

import com.whnec.i18n.I18nItem;
import com.whnec.i18n.I18nResource;
import com.whnec.tool.eclipse.plugin.i18nxmleditor.service.I18nXmlInterpreterService;
import com.whnec.tool.eclipse.plugin.i18nxmleditor.service.InterpretException;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class I18nXmlEditor extends FormEditor implements IResourceChangeListener {

    /** Regexp for i18n xml file name */
    public static final Pattern PATTERN_FILENAME = Pattern.compile("^resource_?[a-zA-Z]{0,2}\\.xml$");
    /** The editors for all i18n xml file */
    private HashMap<String, TextEditor> sourceEditors = new HashMap<String, TextEditor>();

    /**
     * Creates editor
     */
    public I18nXmlEditor() {
        super();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    }

    /**
     * Create a text editor associated with a input and add it to multi-page
     * editor
     * 
     * @param input
     *            The input for new text editor
     */
    void addSourceEditor(final IEditorInput input) {
        if (null == input)
            return;
        TextEditor editor = new TextEditor();
        try {
            int index = addPage(editor, input);
            setPageText(index, editor.getTitle());
            sourceEditors.put(input.getName(), editor);
        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor: " + input.getName(),
                    e.getMessage(), e.getStatus());
        }
    }

    /**
     * creates text editor pages for all i18n xml file
     */
    void addSourceEditors() {
        // create a text editor page for the current file
        addSourceEditor(getEditorInput());

        IResource[] resources = null;
        try {
            resources = ResourceUtil.getFile(getEditorInput()).getParent().members();
        } catch (CoreException e) {
        }
        if (null != resources && resources.length > 0) {
            String editorFileName = getEditorInput().getName();
            for (IResource resource : resources) {
                if (resource instanceof IFile) {
                    IFile file = (IFile) resource;
                    if (!file.getName().equals(editorFileName) && PATTERN_FILENAME.matcher(file.getName()).matches()) {
                        /**
                         * create a text editor page for other i18n xml file
                         * according to file name pattern in the same folder
                         */
                        // createTextEditor(new FileEditorInput(file));
                        addSourceEditor(new FileEditorInput(file));
                    }
                }
            }
        }
    }

    void createI18nEditor() {
        IFile file = ResourceUtil.getFile(getEditorInput());
        try {
            I18nXmlInterpreterService interpreter = (I18nXmlInterpreterService) getSite().getService(
                    I18nXmlInterpreterService.class);
            I18nResource resource = interpreter.unmarshal(file.getContents(), "gbk");
            if (null != resource)
                for (I18nItem item : resource.getItems())
                    System.out.println(item.getName());
        } catch (InterpretException e) {
            ErrorDialog.openError(getSite().getShell(), "Unmarshall Error", "Exception on unmarshall i18n xml file: "
                    + file.getName(),
                    new Status(IStatus.ERROR, I18nXmlEditorPlugin.PLUGIN_ID, Status.ERROR, e.getMessage(), e));
        } catch (CoreException e) {
            ErrorDialog.openError(getSite().getShell(), "Resource Error",
                    "Exception on reading file: " + file.getName(), new Status(IStatus.ERROR,
                            I18nXmlEditorPlugin.PLUGIN_ID, Status.ERROR, e.getMessage(), e));
        }
    }

    /**
     * Creates page 0 of the multi-page editor, which contains a text editor.
     */
    // void createPage0() {
    // try {
    // editor = new TextEditor();
    // int index = addPage(editor, getEditorInput());
    // setPageText(index, editor.getTitle());
    // } catch (PartInitException e) {
    // ErrorDialog.openError(getSite().getShell(),
    // "Error creating nested text editor", null, e.getStatus());
    // }
    // }

    /**
     * Creates page 1 of the multi-page editor, which allows you to change the
     * font used in page 2.
     */
    // void createPage1() {
    //
    // Composite composite = new Composite(getContainer(), SWT.NONE);
    // GridLayout layout = new GridLayout();
    // composite.setLayout(layout);
    // layout.numColumns = 2;
    //
    // Button fontButton = new Button(composite, SWT.NONE);
    // GridData gd = new GridData(GridData.BEGINNING);
    // gd.horizontalSpan = 2;
    // fontButton.setLayoutData(gd);
    // fontButton.setText("Change Font...");
    //
    // fontButton.addSelectionListener(new SelectionAdapter() {
    // public void widgetSelected(SelectionEvent event) {
    // setFont();
    // }
    // });
    //
    // int index = addPage(composite);
    // setPageText(index, "Properties");
    // }

    /**
     * Creates page 2 of the multi-page editor, which shows the sorted text.
     */
    // void createPage2() {
    // Composite composite = new Composite(getContainer(), SWT.NONE);
    // FillLayout layout = new FillLayout();
    // composite.setLayout(layout);
    // text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
    // text.setEditable(false);
    //
    // int index = addPage(composite);
    // setPageText(index, "Preview");
    // }

    /**
     * The <code>MultiPageEditorPart</code> implementation of this
     * <code>IWorkbenchPart</code> method disposes all nested editors.
     * Subclasses may extend.
     */
    public void dispose() {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * Saves the multi-page editor's document.
     */
    public void doSave(IProgressMonitor monitor) {
        getEditor(0).doSave(monitor);
    }

    /**
     * Saves the multi-page editor's document as another file. Also updates the
     * text for page 0's tab, and updates this multi-page editor's input to
     * correspond to the nested editor's.
     */
    public void doSaveAs() {
        // IEditorPart editor = getEditor(0);
        // editor.doSaveAs();
        // setPageText(0, editor.getTitle());
        // setInput(editor.getEditorInput());
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart
     */
    public void gotoMarker(IMarker marker) {
        setActivePage(0);
        IDE.gotoMarker(getEditor(0), marker);
    }

    /**
     * checks that the input is an instance of <code>IFileEditorInput</code> and
     * match the file name pattern.
     */
    public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
        if (!(editorInput instanceof IFileEditorInput))
            throw new PartInitException("Invalid Input: Must be IFileEditorInput");
        else if (!PATTERN_FILENAME.matcher(ResourceUtil.getFile(editorInput).getName()).matches())
            throw new PartInitException("Invalid file, Supported files: resource*.xml");
        super.init(site, editorInput);
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart.
     */
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Calculates the contents of page 2 when the it is activated.
     */
    protected void pageChange(int newPageIndex) {
        super.pageChange(newPageIndex);
        // if (newPageIndex == 2) {
        // sortWords();
        // }
    }

    /**
     * Closes all i18n files on project close.
     */
    public void resourceChanged(final IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
                    for (int i = 0; i < pages.length; i++) {
                        if (((FileEditorInput) getEditorInput()).getFile().getProject().equals(event.getResource())) {
                            IEditorPart editorPart = pages[i].findEditor(getEditorInput());
                            pages[i].closeEditor(editorPart, true);
                        }
                    }
                }
            });
        }
    }

    protected void addPages() {
        addSourceEditors();

    }
    /**
     * Sets the font related data to be applied to the text in page 2.
     */
    // void setFont() {
    // FontDialog fontDialog = new FontDialog(getSite().getShell());
    // fontDialog.setFontList(text.getFont().getFontData());
    // FontData fontData = fontDialog.open();
    // if (fontData != null) {
    // if (font != null)
    // font.dispose();
    // font = new Font(text.getDisplay(), fontData);
    // text.setFont(font);
    // }
    // }

    /**
     * Sorts the words in page 0, and shows them in page 2.
     */
    // void sortWords() {
    //
    // String editorText =
    // editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
    //
    // StringTokenizer tokenizer = new StringTokenizer(editorText,
    // " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
    // ArrayList<String> editorWords = new ArrayList<String>();
    // while (tokenizer.hasMoreTokens()) {
    // editorWords.add(tokenizer.nextToken());
    // }
    //
    // Collections.sort(editorWords, Collator.getInstance());
    // StringWriter displayText = new StringWriter();
    // for (int i = 0; i < editorWords.size(); i++) {
    // displayText.write(((String) editorWords.get(i)));
    // displayText.write(System.getProperty("line.separator"));
    // }
    // text.setText(displayText.toString());
    // }
}
