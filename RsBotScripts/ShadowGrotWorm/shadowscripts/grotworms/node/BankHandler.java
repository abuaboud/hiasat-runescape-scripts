package shadowscripts.grotworms.node;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

import shadowscripts.grotworms.ulits.Ulits;
import shadowscripts.grotworms.Constants;

public class BankHandler extends Node {

	@Override
	public boolean activate() {
		return reqBank();
	}

	@Override
	public void execute() {
		if (Constants.BANKAREA.contains(
				Players.getLocal().getLocation().getX(), Players.getLocal()
						.getLocation().getY())) {
			if (Bank.isOpen()) {
			  if(Inventory.contains(Constants.POLYPORE_STAFF) && Constants.RENEWAL){
				  Inventory.getItem(Constants.POLYPORE_STAFF).getWidgetChild().interact("Wield");
					for (int i = 0; i < 40
							&& Inventory.contains(Constants.POLYPORE_STAFF); i++, sleep(
							100, 150))
						;
					sleep(400,600);
					Constants.RENEWAL = false;
			  }else if (!Inventory.contains(Constants.POLYPORE_STAFF) && !Inventory.isFull() && Constants.RENEWAL) {
						Constants.STATUS = "Withdraw Polypore Staff...";
						Bank.withdraw(Constants.POLYPORE_STAFF,1);
						for (int i = 0; i < 40
								&& !Inventory.contains(Constants.POLYPORE_STAFF); i++, sleep(
								100, 150))
							;
						sleep(400,600);
				} else if (Inventory.getCount(Ulits.convertIntegers(Constants.DROPS)) > 0) {
					Constants.STATUS = "Deposit Inventory...";
						Constants.LAST_PROFIT = Constants.LAST_PROFIT
								+ Ulits.getInventoryProfit();
						Constants.FIRST_INV= false;
					System.out.println( Ulits.getInventoryProfit() +" " + Constants.LAST_PROFIT);
					Bank.depositInventory();
					Bank.depositFamiliarInventory();
					for (int i = 0; i < 40
							&& Inventory.getCount(Ulits
									.convertIntegers(Constants.DROPS)) > 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
					Constants.FAMILIAR_FULL = false;
				} else if (!Inventory.contains(Constants.NATURE_RUNE)) {
					Constants.STATUS = "Withdraw Nature rune...";
					Bank.withdraw(Constants.NATURE_RUNE,
							Bank.getItemCount(Constants.NATURE_RUNE) - 1);
					for (int i = 0; i < 40
							&& !Inventory.contains(Constants.NATURE_RUNE); i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (!Inventory.contains(Constants.FIRE_RUNE)) {
					Constants.STATUS = "Withdraw Fire rune...";
					Bank.withdraw(Constants.FIRE_RUNE,
							Bank.getItemCount(Constants.FIRE_RUNE) - 1);
					for (int i = 0; i < 40
							&& !Inventory.contains(Constants.FIRE_RUNE); i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (!Inventory.contains(Constants.FALADOR_TAB)) {
					Constants.STATUS = "Withdraw 5 Falador Tab...";
					Bank.withdraw(Constants.FALADOR_TAB, 5);
					for (int i = 0; i < 40
							&& !Inventory.contains(Constants.FALADOR_TAB); i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.SATTPOTION) == 0
						&& Constants.USE_ATT) {
					Constants.STATUS = "Withdraw Supper Attack Potion...";
					Bank.withdraw(getBankItem(Constants.SATTPOTION), 1);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.SATTPOTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.SSTRPOTION) == 0
						&& Constants.USE_STR) {
					Constants.STATUS = "Withdraw Supper Strength Potion...";
					Bank.withdraw(getBankItem(Constants.SSTRPOTION), 1);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.SSTRPOTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.SDEFPOTION) == 0
						&& Constants.USE_DEF) {
					Constants.STATUS = "Withdraw Supper Defence Potion...";
					Bank.withdraw(getBankItem(Constants.SDEFPOTION), 1);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.SDEFPOTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.RANGE_POTION) == 0
						&& Constants.USE_RANGE) {
					Constants.STATUS = "Withdraw Range Potion...";
					Bank.withdraw(getBankItem(Constants.RANGE_POTION), 1);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.RANGE_POTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.PRAYERPOTION) == 0
						&& Constants.USE_PRAYER) {
					Constants.STATUS = "Withdraw Prayer Potion...";
					Bank.withdraw(getBankItem(Constants.PRAYERPOTION),
							Constants.PRAYER_AMOUNT);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.PRAYERPOTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.SUMMONPOTION) == 0
						&& Constants.SUMMON_ACTIVE) {
					Constants.STATUS = "Withdraw Summon Potion...";
					Bank.withdraw(getBankItem(Constants.SUMMONPOTION),
							Constants.SUMMONAMOUNT);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.SUMMONPOTION) == 0; i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else if (Inventory.getCount(Constants.CurrentFamiliar
						.getPouchId()) == 0 && Constants.SUMMON_ACTIVE) {
					Constants.STATUS = "Withdraw Summon Pouch...";
					Bank.withdraw(Constants.CurrentFamiliar.getPouchId(),
							Constants.POUCHAMOUNT);
					for (int i = 0; i < 40
							&& Inventory.getCount(Constants.CurrentFamiliar
									.getPouchId()) == 0; i++, sleep(100, 150))
						;
					sleep(400,600);
				} else if (!Inventory.contains(Constants.CURRENT_FOOD)) {
					Constants.STATUS = "Withdraw " + Constants.FOODAMOUNT
							+ " Food...";
					Bank.withdraw(Constants.CURRENT_FOOD, Constants.FOODAMOUNT);
					for (int i = 0; i < 40
							&& !Inventory.contains(Constants.CURRENT_FOOD); i++, sleep(
							100, 150))
						;
					sleep(400,600);
				} else {
					Constants.STATUS = "Closing Bank...";
					Constants.BANK_SHARKAMOUNT = Bank.getItem(Constants.CURRENT_FOOD).getStackSize();
					Constants.BANK_FALADOR_TELEPORT= Bank.getItem(Constants.FALADOR_TAB).getStackSize();
					if(Constants.SUMMON_ACTIVE){
						Constants.BANK_POUCHAMOUNT= Bank.getItem(Constants.CurrentFamiliar.getPouchId()).getStackSize();
						Constants.BANK_SUMMONAMOUNT= Bank.getItem(Constants.SUMMONPOTION).getStackSize();
					}
					if(Bank.getItem(Constants.POLYPORE_STAFF) !=null){
						Constants.BANK_POLYPORESTAFF= Bank.getItem(Constants.POLYPORE_STAFF).getStackSize();
					}
					Bank.close();
				}
			} else {
				if(!Bank.getNearest().isOnScreen()){
					Camera.turnTo(Constants.PATHTOFALABANK[Constants.PATHTOFALABANK.length - 1]);
				}
				Bank.open();
			}
		} else {
			if (Constants.FALADOR_AREA.contains(Players.getLocal().getLocation().getX(), Players.getLocal().getLocation().getY())) {
				Walking.newTilePath(Constants.PATHTOFALABANK).traverse();
			} else {
				if((System.currentTimeMillis() - Constants.STARTTIME  < 4000)){
					return;
				}
				Inventory.getItem(Constants.FALADOR_TAB).getWidgetChild().interact("Break");
				sleep(1000, 1500);
				Ulits.SleepTillStop(8);
			}
		}
	}

	private int getBankItem(int e[]) {
		for (int i : e) {
			if (Bank.getItemCount(i) > 0) {
				return i;
			}
		}
		return e[e.length - 1];
	}

	public static boolean reqBank() {
		if (Constants.RENEWAL) {
			return true;
		}
		if (Constants.STARTCHECK) {
			Constants.STARTCHECK = false;
			return (Inventory.getCount(Constants.CURRENT_FOOD) > 0
					&& Inventory.getCount(Constants.FALADOR_TAB) > 0 && !Bank
						.isOpen());
		}
		return (Inventory.getItem(Constants.CURRENT_FOOD) == null || Inventory
				.getItem(Constants.FALADOR_TAB) == null) || Bank.isOpen();
	}

}
