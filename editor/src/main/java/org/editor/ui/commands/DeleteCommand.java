package org.editor.ui.commands;

import org.editor.interfaces.IEditor;

public class DeleteCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        switch (splits.length) {
            case 2:
                editor.delete(Integer.parseInt(splits[1]), Integer.parseInt(splits[1]));
                break;
            case 3:
                editor.delete(Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
                break;
        }
    }
}
