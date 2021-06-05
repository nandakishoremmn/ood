package org.editor.data;

import org.editor.enums.Action;

import java.util.List;

public class ActionData {
    final Action action;
    final Integer start;
    final Integer end;
    final List<String> data;

    public ActionData(Action action, Integer start, Integer end, List<String> data) {
        this.action = action;
        this.start = start;
        this.end = end;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public Integer getStart() {
        return start;
    }

    public List<String> getData() {
        return data;
    }

}
