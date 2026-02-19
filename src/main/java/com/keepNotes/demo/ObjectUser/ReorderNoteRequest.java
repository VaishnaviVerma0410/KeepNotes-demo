package com.keepNotes.demo.ObjectUser;

public class ReorderNoteRequest {
    int fromIndex;
    int toIndex;

    public int getFromIndex(){
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex(){
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }
}
