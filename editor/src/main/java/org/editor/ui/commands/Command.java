package org.editor.ui.commands;

import org.editor.interfaces.IEditor;

public interface Command {
    void execute(String [] splits, IEditor editor);
}
