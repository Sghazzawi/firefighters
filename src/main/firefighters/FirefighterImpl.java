package main.firefighters;

import main.api.CityNode;
import main.api.Firefighter;

public class FirefighterImpl implements Firefighter {
  private CityNode currentLocation;
  private Integer distance;
  public FirefighterImpl(CityNode location) {
    this.currentLocation = location;
    this.distance = 0;
  }

  @Override
  public CityNode getLocation() {
    return currentLocation;
  }

  @Override
  public int distanceTraveled() {
    return distance;
  }

  public void moveTo(CityNode location) {
    distance = distance + Utils.getManhattanDistance(location,currentLocation);
    this.currentLocation = location;
  }
}
