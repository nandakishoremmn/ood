package org.editor.ui.commands;

import org.editor.interfaces.IEditor;

public class DisplayCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        switch (splits.length) {
            case 1:
                editor.display(null, null);
                break;
            case 2:
                editor.display(Integer.parseInt(splits[1]), null);
                break;
        }
    }
}
