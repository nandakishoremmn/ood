package org.editor.ui.commands;

import org.editor.exceptions.EditorException;
import org.editor.interfaces.IEditor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        if (splits.length >= 3) {
            editor.insert(Integer.parseInt(splits[1]), Arrays.stream(splits).skip(2).collect(Collectors.toList()));
        } else {
            throw new EditorException("Not text to insert");
        }
    }
}
