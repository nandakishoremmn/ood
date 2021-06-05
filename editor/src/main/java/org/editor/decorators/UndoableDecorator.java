package org.editor.decorators;

import org.editor.enums.Action;
import org.editor.data.ActionData;
import org.editor.interfaces.IEditor;

import java.util.List;
import java.util.Stack;

public class UndoableDecorator implements IEditor {
    private final IEditor editor;
    private final Stack<ActionData> history = new Stack<>();
    private final Stack<ActionData> undoHistory = new Stack<>();

    public UndoableDecorator(IEditor editor) {
        this.editor = editor;
    }

    public void redo() {
        if (undoHistory.isEmpty()) {
            System.out.println("Nothing to redo");
        } else {
            ActionData actionData = undoHistory.pop();
            history.push(actionData);
            Action action = actionData.getAction();
            switch (action) {
                case i:
                    editor.insert(actionData.getStart(), actionData.getData());
                    break;
                case dd:
                    editor.delete(actionData.getStart(), actionData.getStart() + actionData.getData().size());
            }
        }
    }

    public void undo() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo");
        } else {
            ActionData actionData = history.pop();
            undoHistory.push(actionData);
            switch (actionData.getAction()) {
                case i:
                    editor.delete(actionData.getStart(), actionData.getStart() + actionData.getData().size() - 1);
                    break;
                case dd:
                    editor.insert(actionData.getStart(), actionData.getData());
            }
        }
    }

    @Override
    public void copy(Integer start, Integer end) {
        editor.copy(start, end);
    }

    @Override
    public void paste(int lineNo) {
        editor.paste(lineNo);
    }

    @Override
    public void insert(int lineNo, List<String> lines) {
        editor.insert(lineNo, lines);
        undoHistory.clear();
        history.push(new ActionData(Action.i, lineNo, null, lines));
    }

    @Override
    public List<String> delete(Integer start, Integer end) {
        List<String> lines = editor.delete(start, end);
        undoHistory.clear();
        history.push(new ActionData(Action.dd, start, end, lines));
        return lines;
    }

    @Override
    public List<String> get(Integer start, Integer end) {
        return editor.get(start, end);
    }

    @Override
    public void display(Integer start, Integer end) {
        editor.display(start, end);
    }
}
