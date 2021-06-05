package org.editor.exceptions;

public class InvalidCommandException extends EditorException {
    public InvalidCommandException(String s) {
        super("Invalid Command : " + s);
    }
}
