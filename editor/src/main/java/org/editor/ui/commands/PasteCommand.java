package org.editor.ui.commands;

import org.editor.exceptions.EditorException;
import org.editor.interfaces.IEditor;

public class PasteCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        if (splits.length == 2) {
            editor.paste(Integer.parseInt(splits[1]));
        } else {
            throw new EditorException("Wrong no. of args");
        }
    }
}
