/*
 * SPDX-FileCopyrightText: 2006 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.chart.generator.item;

import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.top_logic.base.chart.dataset.ExtendedXYSeries;

/**
 * The ExtendedXYSeriesCollectionItemLabelGenerator works together with the {@link com.top_logic.base.chart.dataset.ExtendedXYSeries}.
 * The ExtendedXYSeries stores objects (e.g. goals, risks, ...). A subclass needs only to implement 
 * this method {@link #getItemLabelFor(Object)}. This object is that from the ExtendedXYSeries.
 * 
 * @author  <a href="mailto:tdi@top-logic.com">Thomas Dickhut</a>
 */
public abstract class ExtendedXYSeriesCollectionItemLabelGenerator implements XYItemLabelGenerator {

    /** 
     * @see org.jfree.chart.labels.XYItemLabelGenerator#generateLabel(org.jfree.data.xy.XYDataset, int, int)
     */
    @Override
	public String generateLabel(XYDataset aDataset, int aSeries, int aItem) {
        String theItemLabel = "";
        
        if (aDataset instanceof XYSeriesCollection) {
            XYSeriesCollection theCollection = (XYSeriesCollection) aDataset;
            XYSeries           theSeries     = theCollection.getSeries(aSeries);
            
            if (theSeries instanceof ExtendedXYSeries) {
                ExtendedXYSeries theExdended = (ExtendedXYSeries)theSeries;
                return getItemLabelFor(theExdended.getObject(aItem));
            }
        }
        
        return theItemLabel;
    }

    /**
     * This method returns the item label for the given object.
     * 
     * @param aObject
     *        A object.
     */
    public abstract String getItemLabelFor(Object aObject);

}

