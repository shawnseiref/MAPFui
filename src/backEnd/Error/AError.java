package backEnd.Error;

public abstract class AError implements IError {
    int timeStamp;

    public AError(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public abstract String getError();
}
