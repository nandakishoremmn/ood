package org.editor.decorators;

import org.editor.interfaces.IEditor;
import org.editor.exceptions.InvalidLineReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicEditor implements IEditor {
    final private List<String> data = new ArrayList<>();

    public void redo() {
        throw new UnsupportedOperationException();
    }

    public void undo() {
        throw new UnsupportedOperationException();
    }

    public void paste(int lineNo) {
        throw new UnsupportedOperationException();
    }

    public void copy(Integer start, Integer end) {
        throw new UnsupportedOperationException();
    }

    public void insert(int lineNo, List<String> lines) {
        if (data.size() < lineNo - 1) {
            IntStream.range(data.size(), lineNo - 1).forEach(i -> data.add(""));
        }
        data.addAll(lineNo - 1, lines);
    }

    public List<String> delete(Integer start, Integer end) {
        if (start != null && end != null) {
            if (start > data.size() || end > data.size() || start == 0 || end == 0 || start > end) {
                throw new InvalidLineReference(start, end, data.size());
            }
            return IntStream.rangeClosed(start, end)
                    .mapToObj(i -> data.remove(start - 1))
                    .collect(Collectors.toList());
        } else if (start != null) {
            if (start > data.size() || start == 0) {
                throw new InvalidLineReference(start, end, data.size());
            }
            return Collections.singletonList(data.remove(start - 1));
        } else {
            return new ArrayList<>(data);
        }
    }

    public List<String> get(Integer start, Integer end) {
        if (start != null && end != null) {
            if (start > data.size() || end > data.size() || start == 0 || end == 0 || start > end) {
                throw new InvalidLineReference(start, end, data.size());
            }
            return new ArrayList<>(data.subList(start - 1, end));
        } else if (start != null) {
            if (start > data.size() || start == 0) {
                throw new InvalidLineReference(start, end, data.size());
            }
            return Collections.singletonList(data.get(start - 1));
        } else {
            return new ArrayList<>(data);
        }
    }

    @Override
    public void display(Integer start, Integer end) {
        throw new UnsupportedOperationException();
    }
}
