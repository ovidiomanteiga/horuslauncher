
package org.paradaise.horussense.launcher.domain.action

import android.graphics.drawable.Drawable
import org.paradaise.horussense.launcher.domain.action.Action
import org.paradaise.horussense.launcher.domain.action.LocalizableString


open class HorusAction : Action {

	constructor(icon: Drawable? = null, name: LocalizableString? = null): super(icon, name)

}


