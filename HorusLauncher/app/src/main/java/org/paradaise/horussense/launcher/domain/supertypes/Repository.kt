
package org.paradaise.horussense.launcher.domain.supertypes


interface Repository<TEntity> {

	val all: Collection<TEntity>

	fun add(item: TEntity?)

}
