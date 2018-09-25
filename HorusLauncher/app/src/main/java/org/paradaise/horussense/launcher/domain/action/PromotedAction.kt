
package org.paradaise.horussense.launcher.domain.action

import android.graphics.drawable.Drawable
import org.paradaise.horussense.launcher.domain.action.Action
import org.paradaise.horussense.launcher.domain.action.LocalizableString


open class PromotedAction: Action {

	constructor(icon: Drawable? = null, name: LocalizableString? = null,
	            description: LocalizableString?): super(icon, name) {
		this.description = description
	}

	open var description: LocalizableString? = null

}
