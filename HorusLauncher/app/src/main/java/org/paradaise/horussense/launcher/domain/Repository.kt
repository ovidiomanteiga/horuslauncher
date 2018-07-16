
package org.paradaise.horussense.launcher.domain


interface Repository<TEntity> {

	val all: Collection<TEntity>

	fun add(item: TEntity?)

}
