package org.editor.ui.commands;

import org.editor.interfaces.IEditor;

public class DisplayCommand implements Command {
    @Override
    public void execute(String[] splits, IEditor editor) {
        switch (splits.length) {
            case 1:
                System.out.println(String.join("\n", editor.get(null, null)));
                break;
            case 2:
                System.out.println(String.join("\n", editor.get(Integer.parseInt(splits[1]), null)));
                break;
        }
    }
}
