package org.editor.decorators;

import org.editor.interfaces.IEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DisplayDecorator implements IEditor {
    private final List<String> clipboard = new ArrayList<>();

    final private IEditor editor;

    public DisplayDecorator(IEditor editor) {
        this.editor = editor;
    }


    public void redo() {
        editor.redo();
    }

    public void undo() {
        editor.undo();
    }

    public void paste(int lineNo) {
        editor.paste(lineNo);
    }

    public void copy(Integer start, Integer end) {
        editor.copy(start, end);
    }

    public void insert(int lineNo, List<String> lines) {
        editor.insert(lineNo, lines);
    }

    public List<String> delete(Integer start, Integer end) {
        return editor.delete(start, end);
    }

    @Override
    public List<String> get(Integer start, Integer end) {
        return editor.get(start, end);
    }

    @Override
    public void display(Integer start, Integer end) {
        System.out.println("==================START=================");
        List<String> data = get(start, end);
        int finalStart = start == null ? 0 : start;
        IntStream.range(0, data.size()).forEach(i -> System.out.printf("%d:\t%s%n", finalStart + i + 1, data.get(i)));
        System.out.println("===================END==================");
    }
}
