package net.ent.etrs.ski.utils;

import javax.enterprise.inject.spi.CDI;

/**
 * classe utilitaire CDI.
 * 
 * @author CDT RBN
 *
 */
public final class CDIUtils
{
	private CDIUtils()
	{
		// protection
	}
	
	/**
	 * retourne un bean géré par CDI, celui marqué par Default ou Any.
	 * 
	 * @param clazz
	 * @return instance du BEAN.
	 */
	public static final <T> T getBean(Class<T> clazz)
	{
		return CDI.current().select(clazz).get();
	}
}
