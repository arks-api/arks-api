import org.apache.hadoop.fs.Path;

public class TestWatchService {
  public static void main(String[] args) throws IOException {
    AbstractWatchService service = WatchServiceFactory.newWatchService();
    service.register(new Path("/user/min/"),
        StandardWatchEventKinds.ENTRY_CREATE);
    while (true) {
      try {
        WatchKey key = service.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          WatchEvent<Path> ev = (WatchEvent<Path>) event;
          Path eventPath = ev.context();
          String realPath = eventPath.getName();
          System.out.println(realPath);
        }
        boolean valid = key.reset();
        if (!valid) {
          break;
        }
      } catch (InterruptedException x) {
        x.printStackTrace();
        continue;
      }
    }
  }
}
