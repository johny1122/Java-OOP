import oop.ex2.GameGUI;

import java.awt.*;


/**
 * A class that represents a Runner SpaceShip
 */
public class Runner extends SpaceShip {

	/** The distance the runner need to be from another spaceship to try teleporting */
	private static final double MAX_DISTANCE_TO_TELEPORT = 0.25;

	/** The angle the runner need to be from another spaceship to try teleporting */
	private static final double MAX_ANGLE_TO_TELEPORT = 0.23;

	/** Zero angle degree */
	private static final double ZERO_ANGLE = 0;

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. This spaceship attempts to run away from the fight. It will always accelerate, and will
	 * constantly turn away from the closest ship. If the nearest ship is closer than 0.25 units, and if its
	 * angle to the Runner is less than 0.23 radians, the Runner feels threatened and will attempt to
	 * teleport.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		//------------- Teleport -------------
		SpaceShip closest = game.getClosestShipTo(this);
		double angle_to_closest = this.getPhysics().angleTo(closest.getPhysics());
		double distance_from_closest = this.getPhysics().distanceFrom(closest.getPhysics());
		if ((distance_from_closest < MAX_DISTANCE_TO_TELEPORT) &&
			(Math.abs(angle_to_closest) < MAX_ANGLE_TO_TELEPORT)) {
			teleport();
		}

		//------------- Accelerate and Turn -------------
		int turn;
		if (angle_to_closest < ZERO_ANGLE) {
			turn = TURN_LEFT;
		} else if (angle_to_closest > ZERO_ANGLE) {
			turn = TURN_RIGHT;
		} else {
			turn = NO_TURN;
		}
		getPhysics().move(ACCELERATE, turn);

		//------------- Add 1 Energy unit -------------
		if (current_energy < max_energy) {
			current_energy++;
		}
	}


	/**
	 * Gets the image of this ship. This method should return the image of the ship with or without the
	 * shield. This will be displayed on the GUI at the end of the round.
	 * @return the image of this ship.
	 */
	public Image getImage() {
		if (shield_is_on) {
			return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		}
		return GameGUI.ENEMY_SPACESHIP_IMAGE;
	}

}
