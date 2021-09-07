package engine.render.display;

import engine.util.Logger;

import static engine.util.Logger.Source.RENDERING;

public class DisplayManager {
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  private static final int FPS_CAP = 120;

  private Display display;

  public void create() {
    Logger.INSTANCE.info(RENDERING, "Creating display");
    display = new Display(3, 2);
    display.setForwardCompatible(true);
    display.setProfileCore(true);
    display.setTitle("Duck");

    display.create(WIDTH, HEIGHT);
  }

  public void update() {
    display.sync(FPS_CAP);
    display.update();
  }

  public boolean isCloseRequested() {
    return display.isCloseRequested();
  }

  public void close() {
    display.destroy();
  }
}
