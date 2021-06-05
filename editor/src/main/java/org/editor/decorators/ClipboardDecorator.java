package org.editor.decorators;

import org.editor.interfaces.IEditor;

import java.util.ArrayList;
import java.util.List;

public class ClipboardDecorator implements IEditor {
    private List<String> clipboard = new ArrayList<>();

    final private IEditor editor;

    public ClipboardDecorator(IEditor editor) {
        this.editor = editor;
    }


    public void redo() {
        editor.redo();
    }

    public void undo() {
        editor.undo();
    }

    public void paste(int lineNo) {
        insert(lineNo, clipboard);
    }

    public void copy(Integer start, Integer end) {
        clipboard = editor.get(start, end);
    }

    public void insert(int lineNo, List<String> lines) {
        editor.insert(lineNo, lines);
    }

    public List<String> delete(Integer start, Integer end) {
        return editor.delete(start, end);
    }

    @Override
    public List<String>  get(Integer start, Integer end) {
        return editor.get(start, end);
    }

    @Override
    public void display(Integer start, Integer end) {
        editor.display(start, end);
    }
}
