package megascripts.api.plugin;

/**
 * User: Aonor
 * Date: 8/12/12
 * Time: 6:19 PM
 */
public abstract class Puzzle {

	public abstract String getAuthor();
	
	public abstract String getName();
	
	public abstract String getStatus();
	
    public abstract boolean isValid();

    public abstract boolean isSolved();

    public abstract void solve();

    
}