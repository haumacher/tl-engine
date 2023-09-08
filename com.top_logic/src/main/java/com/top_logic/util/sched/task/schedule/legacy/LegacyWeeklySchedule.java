/*
 * SPDX-FileCopyrightText: 2014 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.util.sched.task.schedule.legacy;

import static com.top_logic.util.sched.task.schedule.legacy.LegacySchedulesCommon.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Document;

import com.top_logic.basic.CalledByReflection;
import com.top_logic.basic.Day;
import com.top_logic.basic.Logger;
import com.top_logic.basic.col.Maybe;
import com.top_logic.basic.config.ConfigurationErrorProtocol;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.SimpleInstantiationContext;
import com.top_logic.basic.config.TypedConfiguration;
import com.top_logic.basic.config.annotation.Format;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.basic.config.annotation.defaults.LongDefault;
import com.top_logic.basic.config.format.MillisFormat;
import com.top_logic.basic.time.CalendarUtil;
import com.top_logic.basic.time.TimeUtil;
import com.top_logic.basic.util.NumberUtil;
import com.top_logic.basic.xml.DOMUtil;
import com.top_logic.layout.form.FormField;
import com.top_logic.layout.form.model.FormFactory;
import com.top_logic.layout.form.model.FormGroup;
import com.top_logic.util.sched.task.TaskCommon;
import com.top_logic.util.sched.task.impl.TaskImpl;
import com.top_logic.util.sched.task.schedule.AbstractSchedulingAlgorithm;
import com.top_logic.util.sched.task.schedule.SchedulingAlgorithm;

/**
 * The old code of the <code>TaskImpl.calcWeek(Calendar)</code> method, before it was rewritten as a
 * {@link SchedulingAlgorithm}.
 * <p>
 * Extracted to this class, in case someone still needs it. Use this class only if switching to the
 * new code is too dangerous and the old code should be used because it is already in use for many
 * years and its behavior is well known. <br/>
 * <b>It is unclear what this code is intended to do exactly, and what it actually does. But it
 * probably has some bugs.</b><br/>
 * It allows to schedule a task at certain days in a week and optionally periodically at those days
 * between the start and stop times.
 * </p>
 * 
 * @author <a href="mailto:jst@top-logic.com">Jan Stolzenburg</a>
 */
@Deprecated
public class LegacyWeeklySchedule extends AbstractSchedulingAlgorithm<LegacyWeeklySchedule.Config> {

	/** {@link TypedConfiguration} for the {@link LegacyWeeklySchedule}. */
	public interface Config extends AbstractSchedulingAlgorithm.Config<LegacyWeeklySchedule> {

		/**
		 * @see #getInterval()
		 */
		static final String INTERVAL_PROPERTY = "interval";

		/**
		 * @see #getHour()
		 */
		static final String HOUR_PROPERTY = "hour";

		/**
		 * @see #getMinute()
		 */
		static final String MINUTE_PROPERTY = "minute";

		/**
		 * @see #isPeriodically()
		 */
		static final String PERIODICALLY_PROPERTY = "periodically";

		/**
		 * @see #getDays()
		 */
		static final String DAYS_PROPERTY = "days";

		/**
		 * @see #getStartHour()
		 */
		static final String START_HOUR_PROPERTY = "start-hour";

		/**
		 * @see #getStartMinute()
		 */
		static final String START_MINUTE_PROPERTY = "start-minute";

		/**
		 * @see #getStopHour()
		 */
		static final String STOP_HOUR_PROPERTY = "stop-hour";

		/**
		 * @see #getStopMinute()
		 */
		static final String STOP_MINUTE_PROPERTY = "stop-minute";

		/**
		 * whether this task should be run periodically
		 */
		@Name(PERIODICALLY_PROPERTY)
		boolean isPeriodically();

		/**
		 * Interval in milliseconds, to determine the next run period.
		 * 
		 * <br/>
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>true</code>.
		 */
		@Name(INTERVAL_PROPERTY)
		@Format(MillisFormat.class)
		@LongDefault(3600L)
		long getInterval();

		/**
		 * The days o which the task must be run.
		 */
		@Format(Day.CommaSeparatedDays.class)
		@Name(DAYS_PROPERTY)
		List<Day> getDays();

		/**
		 * At what hour (0..23) shall the task run (or start)
		 * 
		 * <br/>
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>true</code>.
		 * 
		 * @see #getHour()
		 */
		@Name(START_HOUR_PROPERTY)
		int getStartHour();

		/**
		 * At what minute (0..59) shall the task run (or start)
		 * 
		 * <br/>
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>true</code>.
		 * 
		 * @see #getMinute()
		 */
		@Name(START_MINUTE_PROPERTY)
		int getStartMinute();

		/**
		 * At what hour (1..24) shall the task stop, 0 indicates no stop at all.
		 * 
		 * <br />
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>true</code>.
		 */
		@Name(STOP_HOUR_PROPERTY)
		int getStopHour();

		/**
		 * At what minute (1..60) shall the task stop.
		 * 
		 * <br />
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>true</code>.
		 */
		@Name(STOP_MINUTE_PROPERTY)
		int getStopMinute();

		/**
		 * At what hour (0..23) shall the task run (or start)
		 * 
		 * <br/>
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>false</code>.
		 * 
		 * @see #getStartHour()
		 */
		@Name(HOUR_PROPERTY)
		int getHour();

		/**
		 * At what minute (0..59) shall the task run (or start)
		 * 
		 * <br/>
		 * 
		 * Only needed if {@link #isPeriodically()} is <code>false</code>.
		 * 
		 * @see #getStartMinute()
		 */
		@Name(MINUTE_PROPERTY)
		int getMinute();

	}

	/**
	 * The name of the {@link FormField} presenting {@link Config#isPeriodically()}.
	 */
	public static final String NAME_FIELD_PERIODICALLY = NAME_FIELD_PREFIX + "Periodically";

	/**
	 * The name of the {@link FormField} presenting {@link Config#isPeriodically()}.
	 */
	public static final String NAME_FIELD_INTERVAL = NAME_FIELD_PREFIX + "Interval";

	/**
	 * The name of the {@link FormField} presenting {@link Config#getDays()}.
	 */
	public static final String NAME_FIELD_DAYMASK = NAME_FIELD_PREFIX + "Daymask";

	/**
	 * The name of the {@link FormField} presenting either {@link Config#getHour()} and
	 * {@link Config#getMinute()} or {@link Config#getStartHour()} and
	 * {@link Config#getStartMinute()}.
	 */
	public static final String NAME_FIELD_TIME = NAME_FIELD_PREFIX + "Time";

	/**
	 * The name of the {@link FormField} presenting {@link Config#getStopHour()} and
	 * {@link Config#getStopMinute()}.
	 */
	public static final String NAME_FIELD_STOP_TIME = NAME_FIELD_PREFIX + "StopTime";

	private static final Document TEMPLATE = DOMUtil.parseThreadSafe(""
		+ "	<table " + templateRootAttributes() + " >"
		+ templateStandardFields()
		+ "		<tr>"
		// 'Large' in case multiple days are selected
		+ templateLargeField(NAME_FIELD_DAYMASK)
		+ "		</tr>"
		+ "		<tr>"
		+ templateSmallField(NAME_FIELD_PERIODICALLY)
		+ templateSmallField(NAME_FIELD_INTERVAL)
		+ "		</tr>"
		+ "		<tr>"
		+ templateSmallField(NAME_FIELD_TIME)
		+ templateSmallField(NAME_FIELD_STOP_TIME)
		+ "		</tr>"
		+ "	</table>"
		);

	// Fields are only 'protected' and not 'private', because thats how they were in TaskImpl.
	// This allows subclass of TaskImpl that use these fields to easily migrate by
	// subclassing the corresponding LegacyFooSchedule class and using these fields.

	/** Indicates the type of daily Schedule needed. */
	protected int daytype;

	/** Interval that Task should run PERIODICALLY (in milliseconds). */
	protected long interval;

	/** at what hour (1..24) shall the task stop, 0 indicates no stop at all */
	protected int stopHour;

	/** at what minute (1..60) shall the task run (or start) */
	protected int stopMinute;

	/**
	 * Bit-mask indicating Day in Week/Month for WEEKLY/MONTHLY
	 * 
	 * Bit 0 is Sunday, Bit 6 is Saturday (for WEEKLY)
	 */
	protected int daymask;

	/** at what hour (0..23) shall the task run (or start) */
	protected int hour;

	/** at what minute (0..59) shall the task run (or start) */
	protected int minute;

	/**
	 * Called by the {@link TypedConfiguration} for creating a {@link LegacyWeeklySchedule}.
	 * 
	 * @param context
	 *        For instantiation of dependent configured objects.
	 * @param config
	 *        The configuration of this {@link LegacyWeeklySchedule}.
	 */
	@CalledByReflection
	public LegacyWeeklySchedule(InstantiationContext context, Config config) {
		super(context, config);
		daytype = WEEKLY;
		daymask = calcDayMask(context, config.getDays());
		if (config.isPeriodically()) {
			daytype |= PERIODICALLY;
		}
		if (0 != (daytype & PERIODICALLY)) {
			hour = config.getStartHour();
			minute = config.getStartMinute();
			interval = config.getInterval();
			stopHour = config.getStopHour();
			stopMinute = config.getStopMinute();
		}
		else {
			hour = config.getHour();
			minute = config.getMinute();
		}
	}

	/**
	 * Needed by the legacy {@link TaskImpl} constructors.
	 * <p>
	 * Use {@link #LegacyWeeklySchedule(InstantiationContext, Config)} instead.
	 * </p>
	 */
	@Deprecated
	public LegacyWeeklySchedule(
			int daytype, int daymask, int hour, int minute, long interval, int stopHour, int stopMinute) {
		super(createInstantiationContext(), createConfig());
		this.daytype = daytype;
		this.daymask = daymask;
		this.hour = hour;
		this.minute = minute;
		this.interval = interval;
		this.stopHour = stopHour;
		this.stopMinute = stopMinute;
	}

	private static InstantiationContext createInstantiationContext() {
		return new SimpleInstantiationContext(ConfigurationErrorProtocol.INSTANCE);
	}

	private static Config createConfig() {
		return TypedConfiguration.newConfigItem(Config.class);
	}

	@Override
	protected Maybe<Calendar> nextScheduleImpl(Calendar now, Maybe<Calendar> lastSchedule) {
		long nextShed = SchedulingAlgorithm.NO_SCHEDULE;
		long last = TaskCommon.lastScheduleAsLong(lastSchedule);
		Calendar calc;
		if (last == SchedulingAlgorithm.NO_SCHEDULE) { // assume we executed today
			calc = CalendarUtil.clone(now);
		} else {
			calc = CalendarUtil.createCalendar(last);
		}

		if ((last == SchedulingAlgorithm.NO_SCHEDULE)
			|| !calcNextTime(calc, daytype, interval, stopHour, stopMinute)) {
			int i = 0;
			int daysInWeek = calc.getActualMaximum(Calendar.DAY_OF_WEEK);

			for (; i < daysInWeek; i++) {
				calc.add(Calendar.DAY_OF_WEEK, 1);
				int day = calc.get(Calendar.DAY_OF_WEEK) - 1; // Days start at 1
				if (0 != ((1 << day) & daymask)) {
					calcStartTime(calc, hour, minute);
					nextShed = calc.getTimeInMillis();
					break;
				}
			}
			if (i >= daysInWeek) {
				Logger.error("calcWeek() invalid week mask "
					+ Integer.toHexString(daymask) + ".", this);
				nextShed = SchedulingAlgorithm.NO_SCHEDULE;
			}
		}
		else {
			// Execute again is same day
			nextShed = calc.getTimeInMillis();
		}
		return Maybe.some(CalendarUtil.createCalendar(nextShed));
	}

	@Override
	public Document getFormTemplateDocument() {
		return TEMPLATE;
	}

	@Override
	public void fillFormGroup(FormGroup group) {
		super.fillFormGroup(group);

		group.addMember(FormFactory.newBooleanField(
			NAME_FIELD_PERIODICALLY, isPeriodically(daytype), FormFactory.IMMUTABLE));

		group.addMember(FormFactory.newSelectField(
			NAME_FIELD_DAYMASK, Arrays.asList(Day.values()), FormFactory.MULTIPLE, getDays(), FormFactory.IMMUTABLE));
		group.addMember(FormFactory.newStringField(
			NAME_FIELD_TIME, formatTime(hour, minute), FormFactory.IMMUTABLE));
		if (isPeriodically(daytype)) {
			group.addMember(FormFactory.newStringField(
				NAME_FIELD_STOP_TIME, formatTime(stopHour, stopMinute), FormFactory.IMMUTABLE));
			group.addMember(FormFactory.newStringField(
				NAME_FIELD_INTERVAL, TimeUtil.formatMillisAsTime(interval), FormFactory.IMMUTABLE));
		} else {
			group.addMember(FormFactory.newStringField(
				NAME_FIELD_STOP_TIME, getFieldValueNoValue(), FormFactory.IMMUTABLE));
			group.addMember(FormFactory.newStringField(
				NAME_FIELD_INTERVAL, getFieldValueNoValue(), FormFactory.IMMUTABLE));
		}
	}

	private List<Day> getDays() {
		List<Integer> bitsSet = NumberUtil.getBitsSet(daymask);
		List<Day> result = new ArrayList<>(bitsSet.size());
		Day[] days = Day.values();
		for (int bitSet : bitsSet) {
			result.add(days[bitSet]);
		}
		return result;
	}

}
