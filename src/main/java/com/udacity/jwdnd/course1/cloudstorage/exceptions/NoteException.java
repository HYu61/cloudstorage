package com.udacity.jwdnd.course1.cloudstorage.exceptions;

/**
 * author: Heng Yu
 */
public class NoteException extends AppException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param message
     */
    public NoteException(String message) {
        super(message);
    }
}
