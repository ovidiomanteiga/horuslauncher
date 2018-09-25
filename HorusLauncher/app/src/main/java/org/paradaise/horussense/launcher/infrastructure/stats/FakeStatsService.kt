
package org.paradaise.horussense.launcher.infrastructure.stats

import android.util.Log
import org.paradaise.horussense.launcher.domain.stats.LauncherStatsVO
import org.paradaise.horussense.launcher.domain.stats.StatsService


class FakeStatsService : StatsService {

	override fun send(stats: LauncherStatsVO?) {
		Log.d("STATS_SERVICE_SENT", stats.toString())
	}

}
