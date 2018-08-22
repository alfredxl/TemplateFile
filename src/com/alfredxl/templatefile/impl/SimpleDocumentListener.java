package com.alfredxl.templatefile.impl;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class SimpleDocumentListener implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        change();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        change();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        change();
    }

    protected abstract void change();
}
