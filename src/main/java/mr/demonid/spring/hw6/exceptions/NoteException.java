package mr.demonid.spring.hw6.exceptions;

public abstract class NoteException extends RuntimeException {

    public NoteException(String message) {
        super(message);
    }
}
