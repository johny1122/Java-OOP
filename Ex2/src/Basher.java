import oop.ex2.GameGUI;

import java.awt.*;

/**
 * A class that represents a Basher SpaceShip
 */
public class Basher extends SpaceShip {

	/** The distance the basher need to be from another spaceship to try turn on shield */
	private static final double MAX_DISTANCE_TO_SHIELD = 0.19;

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. This ship attempts to collide with other ships. It will always accelerate, and will constantly
	 * turn towards the closest ship. If it gets within a distance of 0.19 units (inclusive) from another
	 * ship, it will attempt to turn on its shields.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		//------------- Resets -------------
		shield_is_on = false;

		//------------- Accelerate and Turn -------------
		SpaceShip closest = game.getClosestShipTo(this);
		double angle_to_closest = this.getPhysics().angleTo(closest.getPhysics());
		double distance_from_closest = this.getPhysics().distanceFrom(closest.getPhysics());
		int turn;
		if (angle_to_closest < ZERO_ANGLE) {
			turn = TURN_RIGHT;
		} else if (angle_to_closest > ZERO_ANGLE) {
			turn = TURN_LEFT;
		} else {
			turn = NO_TURN;
		}
		getPhysics().move(ACCELERATE, turn);

		//------------- Shield -------------
		if (distance_from_closest <= MAX_DISTANCE_TO_SHIELD) {
			shieldOn();
		}

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
