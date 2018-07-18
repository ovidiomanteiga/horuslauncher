
package org.paradaise.horussense.launcher.infrastructure

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import org.paradaise.horussense.launcher.domain.LocalizableString
import org.paradaise.horussense.launcher.domain.PromotedAction


class OpenLinkPromotedAction: PromotedAction {

	constructor(icon: Drawable? = null, name: LocalizableString? = null,
	            description: LocalizableString?): super(icon, name, description)


	var context: Context? = null


	override fun perform() {
		super.perform()
		this.intent?.let {
			this.launch(it)
		}
	}


	private val intent: Intent?
		get() = Intent(Intent.ACTION_VIEW, Uri.parse(this.url))


	private fun launch(it: Intent) {
		Handler(Looper.getMainLooper()).post {
			this.context?.startActivity(it)
		}
	}

}
