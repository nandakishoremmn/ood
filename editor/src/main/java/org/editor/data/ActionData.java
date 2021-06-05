package org.editor.data;

import org.editor.enums.Action;

import java.util.List;

public class ActionData {
    Action action;
    Integer start;
    Integer end;
    List<String> data;

    public ActionData(Action action, Integer start, Integer end, List<String> data) {
        this.action = action;
        this.start = start;
        this.end = end;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
