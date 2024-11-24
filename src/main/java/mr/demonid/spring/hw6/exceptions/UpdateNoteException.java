package mr.demonid.spring.hw6.exceptions;

public class UpdateNoteException extends NoteException {

    public UpdateNoteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return "Ошибка обновления данных: " + msg;
    }
}
