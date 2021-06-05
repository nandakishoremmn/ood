package org.editor.exceptions;

public class InvalidLineReference extends EditorException {
    public InvalidLineReference(Integer start, Integer end, Integer size) {
        super(String.format("Line numbers out of bound : [%s,%s] size: %s", start, end, size));
    }
}
