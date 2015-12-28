package shadowscripts.grotworms.api;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Widgets;
import shadowscripts.grotworms.Constants;

public class MessageListenerHandler extends LoopTask {

	public static String prev = null;


	public static String listen() {
		if(Widgets.get(137) == null || Widgets.get(137, 58) == null || Widgets.get(137, 58).getChild(0) == null || Widgets.get(137, 58).getChild(0).getText() == null)
			return null;
		if(prev == null){
			prev = Widgets.get(137, 58).getChild(0).getText();
		}
		if (!Widgets.get(137, 58).getChild(0).getText().equals(prev)) {
			return Widgets.get(137, 58).getChild(0).getText().toLowerCase();
		}
		return null;
	}
	
	@Override
	public int loop() {
			try {
				if(listen() == null){
					return 2;
				}
				if (listen().contains("coins have been added to your money pouch"
						.toLowerCase())) {
					Constants.ALCH_PROFIT = Constants.ALCH_PROFIT
							+ Math.abs(Integer.parseInt(listen().split(" ")[0]
									.replaceAll(",", "")));
					System.out.println(""
							+ Math.abs(Integer.parseInt(listen().split(" ")[0]
									.replaceAll(",", ""))));
				}
				if (listen().contains("familiar cannot".toLowerCase())) {
					Constants.FAMILIAR_FULL = true;
				}
				if (listen().contains("your polypore staff has run out")
						|| listen().contains("you must wield a charged polypore staff")) {
					Constants.RENEWAL = true;
				}
				System.out.println("[Local Message Listener]: " +listen());
				prev = Widgets.get(137, 58).getChild(0).getText();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return 2;
	}
}
