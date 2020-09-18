package nz.ac.vuw.ecs.swen225.gp20.maze;
    /**
     * This class represent the movement being done in the game.
     */
    public class Move {
        String direction;

        public Move(String direction) {
            this.direction = direction;
        }

        /**
         * Apply the movement to the player.
         * @param player -- the current player in the game.
         */
        public void apply(Player player){

        }

        @Override
        public String toString() {
            return direction;
        }
    }
