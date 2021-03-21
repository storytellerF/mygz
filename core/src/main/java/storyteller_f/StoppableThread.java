package storyteller_f;

public abstract class StoppableThread extends Thread {
    public boolean stopByNext = false;
    public boolean isRunning = false;

    @Override
    public void run() {
        isRunning = true;
        super.run();
        task();
        isRunning = false;
    }

    public abstract void task();
}
