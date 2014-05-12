package gradetrackerpro.transmission;
public interface ITrigger{
  public void pushData(String title, String data[]);
  public void addReceiver(IReceiver receiver);
  public void removeReceiver(IReceiver receiver);
}