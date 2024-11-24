package mr.demonid.spring.hw6.exceptions;

public class DeleteNoteException extends NoteException {

    public DeleteNoteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return "Ошибка удаления заметки: " + msg;
    }
}
