package org.editor.interfaces;

public interface Clipboard {
    void copy(Integer start, Integer end);
    void paste(int lineNo);
}
