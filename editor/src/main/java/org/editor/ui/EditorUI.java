package org.editor.ui;

import org.editor.enums.Action;
import org.editor.exceptions.EditorException;
import org.editor.exceptions.InvalidCommandException;
import org.editor.interfaces.IEditor;

public class EditorUI {
    final private IEditor editor;

    public EditorUI(IEditor editor) {
        this.editor = editor;
    }

    public void sendCommand(String commandString) {
        editor.display(null, null);
        System.out.println(commandString);
        try {
            sendCommandProxy(commandString);
        } catch (EditorException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        editor.display(null, null);
        System.out.println("-------------------------");
    }

    public void sendCommandProxy(String commandString) {
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

    }
}
