package mr.demonid.spring.hw6.exceptions;

public class CreateNoteException extends NoteException {

    public CreateNoteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return "Ошибка создания заметки: " + msg;
    }
}
