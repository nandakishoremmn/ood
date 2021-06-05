package org.editor.ui.commands;

import org.editor.exceptions.EditorException;
import org.editor.interfaces.IEditor;

public class CopyCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        if (splits.length == 3) {
            editor.copy(Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
        } else {
            throw new EditorException("Wrong no.of args");
        }
    }
}
