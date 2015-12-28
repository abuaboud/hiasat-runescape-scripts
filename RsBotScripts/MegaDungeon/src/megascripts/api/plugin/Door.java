package megascripts.api.plugin;


public abstract class Door {

	public abstract String getAuthor();
	
	public abstract String getStatus();
	
    public abstract boolean isValid();

    public abstract void Open();

    
}