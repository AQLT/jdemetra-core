/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.timeseries.calendars;

import demetra.util.WeightedItem;

/**
 *
 * @author PALATEJ
 */
@lombok.Value
public class CompositeCalendar implements CalendarDefinition{
    private WeightedItem<String>[] calendars;    
}
