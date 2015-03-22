/*
 * Player.java
 *
 * Created on December 5, 2003, 11:55 PM
 */

/**
 *
 * @author  Chen
 */
public interface PlayerInterface {
    public void go(PlayBoard b);
    public void setMove(int col);
    public int getType();
}
