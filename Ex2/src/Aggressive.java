import oop.ex2.GameGUI;

import java.awt.*;

/**
 * A class that represents an Aggressive SpaceShip
 */
public class Aggressive extends SpaceShip {

	/** The angle the aggressive need to be from another spaceship to try firing */
	private static final double MAX_ANGLE_TO_FIRE = 0.21;

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. This ship pursues other ships and tries to fire at them. It will always accelerate, and turn
	 * towards the nearest ship. When its angle to the nearest ship is less than 0.21 radians, it will try to
	 * fire.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
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

		//------------- Firing a shot -------------
		if (rounds_left_after_fire != NO_ROUNDS_LEFT_AFTER_FIRE) {
			rounds_left_after_fire--;
		} else if (Math.abs(angle_to_closest) < MAX_ANGLE_TO_FIRE) {
			fire(game);
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

