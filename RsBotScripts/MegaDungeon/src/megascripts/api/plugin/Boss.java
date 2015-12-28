package megascripts.api.plugin;

/**
 * User: Magorium
 * Date: 8/12/12
 */
public abstract class Boss {

	public abstract String getAuthor();
	
	public abstract String getName();
	
	public abstract String getStatus();
	
    public abstract boolean isValid();

    public abstract void Kill();

    
}