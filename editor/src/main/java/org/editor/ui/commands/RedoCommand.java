package org.editor.ui.commands;

import org.editor.exceptions.EditorException;
import org.editor.interfaces.IEditor;

public class RedoCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        if (splits.length == 1) {
            editor.redo();
        } else {
            throw new EditorException("Wrong no. of args");
        }
    }
}
