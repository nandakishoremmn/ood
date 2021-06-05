package org.editor.ui.commands;

import org.editor.exceptions.EditorException;
import org.editor.interfaces.IEditor;

public class UndoCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        if (splits.length == 1) {
            editor.undo();
        } else {
            throw new EditorException("wrong no. of args");
        }
    }
}
