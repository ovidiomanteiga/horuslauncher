
package org.paradaise.horussense.launcher.domain

import android.graphics.drawable.Drawable


open class PromotedAction: Action {

	constructor(icon: Drawable? = null, name: LocalizableString? = null,
	            description: LocalizableString?): super(icon, name) {
		this.description = description
	}

	open var description: LocalizableString? = null

}
