/*
 * SPDX-FileCopyrightText: 2011 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.reporting.common.period;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.data.time.RegularTimePeriod;

import com.top_logic.base.time.BusinessYearConfiguration;
import com.top_logic.base.time.TimePeriod;
import com.top_logic.base.time.Year;
import com.top_logic.basic.DateUtil;

/**
 * @author <a href="mailto:fsc@top-logic.com">fsc</a>
 */
public class BYYear extends BusinessYearPeriodBase {

    public BYYear(Date time, TimeZone zone, Locale locale) {
        super(time, zone, locale);
    }

    public BYYear(Date time, TimeZone zone) {
        super(time, zone);
    }

    private BYYear(TimePeriod period, TimeZone zone, Locale locale) {
        super(period, zone, locale);
    }

    @Override
    protected TimePeriod createPeriod(Date time) {
		return new Year(createCalendar(time), BusinessYearConfiguration.getBeginMonth(),
			BusinessYearConfiguration.getBeginDay());
    }
    
    @Override
	public RegularTimePeriod previous() {
        return new BYYear(DateUtil.addSeconds(this.period.getBegin(), -1), this.timezone, this.locale);
    }

    @Override
	public RegularTimePeriod next() {
        return new BYYear(this.period.getNextPeriod(), this.timezone, this.locale);
    }

    @Override
	public String toString() {
		return BusinessYearConfiguration.getYear(createCalendar(this.period.getBegin())) + " BY";
    }
}
