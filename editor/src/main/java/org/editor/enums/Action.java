package org.editor.enums;

import org.editor.ui.commands.*;

public enum Action {
    d(new DisplayCommand()),
    dd(new DeleteCommand()),
    i(new InsertCommand()),
    yy(new CopyCommand()),
    p(new PasteCommand()),
    z(new UndoCommand()),
    zz(new RedoCommand());

    Command command;

    public Command getCommand() {
        return command;
    }

    Action(Command command) {
        this.command = command;
    }
}
