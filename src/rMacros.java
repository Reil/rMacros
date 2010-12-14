import java.util.logging.Level;
import java.util.logging.Logger;


public class rMacros extends Plugin{
	rPropertiesFile Macros;
	PluginListener listener = new rMacrosListener();
	Logger log = Logger.getLogger("Minecraft");

	public rMacros () {
		Macros = new rPropertiesFile("rMacros.properties");
	}
	
	public void initialize(){
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
	}
	
	
	public void enable(){
		try{
			Macros.load();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[rMacros]: Exception while loading properties file.", e);
		}
		for (String Macro : Macros.getKeys()){
			etc.getInstance().addCommand(Macro, Macros.getString(Macro));
		}
		log.info("[rMacros] Macros loaded.");
	}
	public void disable(){
		for (String Macro : Macros.getKeys()){
			etc.getInstance().removeCommand(Macro);
		}
		log.info("[rMacros] Macros disabled");
	}
	
	public class rMacrosListener extends PluginListener {
		public boolean onCommand(Player player, String[] split){
			if (!player.canUseCommand(split[0])){
				return false;
			}
			boolean isFirst = true;
			String searchFor = split[0].substring(1);
			if (Macros.keyExists(searchFor)) {
				for (String commandString : Macros.getStrings(searchFor)){
					if (isFirst){
						isFirst = false;
						continue;
					}
					player.command(commandString);
				}
				return true;
			}
			return false;
		}
	} 
}
