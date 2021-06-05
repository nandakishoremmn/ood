package org.editor.interfaces;

import java.util.List;

public interface Editable {
    void insert(int lineNo, List<String> lines);
    List<String> delete(Integer start, Integer end);
}
