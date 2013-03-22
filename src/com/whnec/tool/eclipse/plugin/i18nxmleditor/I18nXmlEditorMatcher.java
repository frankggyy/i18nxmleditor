/**
 * 
 */
package com.whnec.tool.eclipse.plugin.i18nxmleditor;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.ResourceUtil;

/**
 * @author tiger
 * 
 */
public class I18nXmlEditorMatcher implements IEditorMatchingStrategy {

	public static final String[] FILENAMES = { "resource_en.xml", "resource_zh.xml" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorMatchingStrategy#matches(org.eclipse.ui.
	 * IEditorReference, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		IFile inputFile = ResourceUtil.getFile(input);
		if (inputFile != null && input instanceof IFileEditorInput) {
			try {
				// a positive match if there is an editor already open on the same file
				if (input.equals(editorRef.getEditorInput()))
					return true;

				
				// a positive match if the i18n xml file in the same folder being opened
				IFile editorFile = ResourceUtil.getFile(editorRef.getEditorInput());
				if (inputFile.getParent().equals(editorFile.getParent()))
					return Arrays.binarySearch(FILENAMES, input.getName()) >= 0;
					
			} catch (PartInitException e) {
				return false;
			}
		} else if (input instanceof IStorageEditorInput) {
			try {
				IEditorInput existing = editorRef.getEditorInput();
				return input.equals(existing);
			} catch (PartInitException e1) {
			}
		}
		return false;

	}
}
