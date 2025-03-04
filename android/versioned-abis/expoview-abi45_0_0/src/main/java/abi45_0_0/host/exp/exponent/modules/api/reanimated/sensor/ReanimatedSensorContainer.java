package abi45_0_0.host.exp.exponent.modules.api.reanimated.sensor;

import android.util.Log;
import abi45_0_0.com.facebook.react.bridge.ReactApplicationContext;
import abi45_0_0.host.exp.exponent.modules.api.reanimated.NativeProxy;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ReanimatedSensorContainer {

  private int nextSensorId = 0;
  private final WeakReference<ReactApplicationContext> reactContext;
  private final HashMap<Integer, ReanimatedSensor> sensors = new HashMap<>();

  public ReanimatedSensorContainer(WeakReference<ReactApplicationContext> reactContext) {
    this.reactContext = reactContext;
  }

  public int registerSensor(
      ReanimatedSensorType sensorType, int interval, NativeProxy.SensorSetter setter) {
    ReanimatedSensor sensor = new ReanimatedSensor(reactContext, sensorType, interval, setter);
    int sensorId = -1;
    if (sensor.initialize()) {
      sensorId = nextSensorId;
      nextSensorId++;
      sensors.put(sensorId, new ReanimatedSensor(reactContext, sensorType, interval, setter));
    }
    return sensorId;
  }

  public void unregisterSensor(int sensorId) {
    ReanimatedSensor sensor = sensors.get(sensorId);
    if (sensor != null) {
      sensor.cancel();
      sensors.remove(sensorId);
    } else {
      Log.e("Reanimated", "Tried to unregister nonexistent sensor");
    }
  }
}
