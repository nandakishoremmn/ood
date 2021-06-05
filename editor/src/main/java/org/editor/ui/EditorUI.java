package org.editor.ui;

import org.editor.enums.Action;
import org.editor.exceptions.EditorException;
import org.editor.exceptions.InvalidCommandException;
import org.editor.interfaces.IEditor;

public class EditorUI {
    final private IEditor editor;
    boolean log = true;

    public EditorUI(IEditor editor) {
        this.editor = editor;
    }

    public void sendCommand(String commandString) {
        if(log) System.out.println(">>> " + commandString);
        String[] splits = commandString.split("#");
        if (splits.length == 0) {
            throw new InvalidCommandException(commandString);
        }
        Action action;
        try {
            action = Action.valueOf(splits[0]);
        } catch (Exception e) {
            throw new InvalidCommandException(commandString);
        }
        action.getCommand().execute(splits, editor);
        if(log) editor.display(null, null);

    }
}
