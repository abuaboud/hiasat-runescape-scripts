package megascripts.dungeon.boss;

import megascripts.api.plugin.Boss;


public class CurrentBoss {

	public static String BossName = null;

	public static final Boss[] BOSSES = { new Skeletaltrio(),
			new gravecreeper(), new Blink(), new UnholyCursebearer(),
			new AsteaFrostweb(), new BulwarkBeast(), new Gluttonousbehemoth(),
			new HobgoblinGeomancer(), new IcyBones(), new LexicusRunewright(),
			new LuminescentIcefiend(), new NightGazer(), new Planefreezer(),
			new Rammernaut(), new Riftsplitter(), new Sagittare(),
			new Shadow(), new SkeletalHorde(), new Stomp(), new thePummeller(),
			new TokashTheBloodchiller(), new Runebound(), new Wraped_Gulega(),
			new Dreadnaut() };

	public static void GetCurrentBossTactic() {
		try {
			for (Boss boss : BOSSES) {
				if (boss.isValid()) {
					boss.Kill();
				}
			}
		} catch (NullPointerException e) {

		}
	}
}