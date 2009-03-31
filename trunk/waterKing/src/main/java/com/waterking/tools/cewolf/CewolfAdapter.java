package com.waterking.tools.cewolf;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.waterking.action.BaseAction;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;

public abstract class CewolfAdapter extends BaseAction implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, Serializable {
	private static final long serialVersionUID = 3221028806051525125L;
	
	protected Log logger = LogFactory.getLog(this.getClass());

	 public Object produceDataset(Map params) throws DatasetProduceException {
		return null;
	}

	/**
	 * This producer's data is invalidated after 5 seconds. By this method the
	 * producer can influence Cewolf's caching behaviour the way it wants to.
	 */
	public boolean hasExpired(Map params, Date since) {		
//		logger.debug(getClass().getName() + "hasExpired()");
//		return (System.currentTimeMillis() - since.getTime())  > 5000;
		return true;
	}


	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		logger.debug(this + " finalized.");
	}


}
