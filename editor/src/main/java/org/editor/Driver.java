package org.editor;

import org.editor.decorators.ClipboardDecorator;
import org.editor.decorators.DisplayDecorator;
import org.editor.decorators.BasicEditor;
import org.editor.decorators.UndoableDecorator;
import org.editor.interfaces.IEditor;
import org.editor.ui.EditorUI;

public class Driver {
    public static void main(String[] args) {
        IEditor editor = new BasicEditor();
        editor = new DisplayDecorator(editor);
        editor = new UndoableDecorator(editor);
        editor = new ClipboardDecorator(editor);

        EditorUI editorUI = new EditorUI(editor);

//        Scanner in = new Scanner(System.in);
//        while (true) {
//            String commandString = in.nextLine();
//            editorUI.sendCommand(commandString);
//
//        }

        editorUI.sendCommand("i#1#Hello World");
        editorUI.sendCommand("i#4#Welcome World");
        editorUI.sendCommand("i#3#Hola World");
        editorUI.sendCommand("z");
        editorUI.sendCommand("d");
        editorUI.sendCommand("zz");
        editorUI.sendCommand("i#1#Hello World");
        editorUI.sendCommand("d");
        editorUI.sendCommand("d#5");
        editorUI.sendCommand("dd#3#4");
        editorUI.sendCommand("yy#4#4");
        editorUI.sendCommand("p#1");
    }
}
