
package org.paradaise.horussense.launcher.domain.stats

import org.paradaise.horussense.launcher.domain.stats.LauncherStatsVO


interface StatsService {

	fun send(stats: LauncherStatsVO?)

}
