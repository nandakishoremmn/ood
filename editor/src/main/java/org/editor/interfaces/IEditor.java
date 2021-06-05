package org.editor.interfaces;

import java.util.List;

public interface IEditor extends Editable, Undoable, Clipboard {
    List<String> get(Integer start, Integer end);
    void display(Integer start, Integer end);
}
