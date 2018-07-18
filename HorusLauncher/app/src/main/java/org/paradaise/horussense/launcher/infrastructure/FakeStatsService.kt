
package org.paradaise.horussense.launcher.infrastructure

import android.util.Log
import org.paradaise.horussense.launcher.domain.LauncherStatsVO
import org.paradaise.horussense.launcher.domain.StatsService


class FakeStatsService : StatsService {

	override fun send(stats: LauncherStatsVO?) {
		Log.d("STATS_SERVICE_SENT", stats.toString())
	}

}
