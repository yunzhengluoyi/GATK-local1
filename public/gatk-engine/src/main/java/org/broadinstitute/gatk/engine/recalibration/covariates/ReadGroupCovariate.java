/*
* Copyright 2012-2016 Broad Institute, Inc.
* 
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:
* 
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
* THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package org.broadinstitute.gatk.engine.recalibration.covariates;

import org.broadinstitute.gatk.engine.recalibration.RecalibrationArgumentCollection;
import org.broadinstitute.gatk.engine.recalibration.ReadCovariates;
import org.broadinstitute.gatk.utils.sam.GATKSAMReadGroupRecord;
import org.broadinstitute.gatk.utils.sam.GATKSAMRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Copyright (c) 2009 The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * Created by IntelliJ IDEA.
 * User: rpoplin
 * Date: Oct 30, 2009
 *
 * The Read Group covariate.
 */

public class ReadGroupCovariate implements RequiredCovariate {

    private final HashMap<String, Integer> readGroupLookupTable = new HashMap<String, Integer>();
    private final HashMap<Integer, String> readGroupReverseLookupTable = new HashMap<Integer, String>();
    private int nextId = 0;
    private String forceReadGroup;

    // Initialize any member variables using the command-line arguments passed to the walkers
    @Override
    public void initialize(final RecalibrationArgumentCollection RAC) {
        forceReadGroup = RAC.FORCE_READGROUP;
    }

    @Override
    public void recordValues(final GATKSAMRecord read, final ReadCovariates values) {
        final String readGroupId = readGroupValueFromRG(read.getReadGroup());
        final int key = keyForReadGroup(readGroupId);

        final int l = read.getReadLength();
        for (int i = 0; i < l; i++)
            values.addCovariate(key, key, key, i);
    }

    @Override
    public final Object getValue(final String str) {
        return str;
    }

    @Override
    public synchronized String formatKey(final int key) {
        // This method is synchronized so that we don't attempt to do a get()
        // from the reverse lookup table while that table is being updated
        return readGroupReverseLookupTable.get(key);
    }

    @Override
    public int keyFromValue(final Object value) {
        return keyForReadGroup((String) value);
    }

    /**
     * Get the mapping from read group names to integer key values for all read groups in this covariate
     * @return a set of mappings from read group names -> integer key values
     */
    public Set<Map.Entry<String, Integer>> getKeyMap() {
        return readGroupLookupTable.entrySet();
    }

    private int keyForReadGroup(final String readGroupId) {
        // Rather than synchronize this entire method (which would be VERY expensive for walkers like the BQSR),
        // synchronize only the table updates.

        // Before entering the synchronized block, check to see if this read group is not in our tables.
        // If it's not, either we will have to insert it, OR another thread will insert it first.
        // This preliminary check avoids doing any synchronization most of the time.
        if ( ! readGroupLookupTable.containsKey(readGroupId) ) {

            synchronized ( this ) {

                // Now we need to make sure the key is STILL not there, since another thread may have come along
                // and inserted it while we were waiting to enter this synchronized block!
                if ( ! readGroupLookupTable.containsKey(readGroupId) ) {
                    readGroupLookupTable.put(readGroupId, nextId);
                    readGroupReverseLookupTable.put(nextId, readGroupId);
                    nextId++;
                }
            }
        }

        return readGroupLookupTable.get(readGroupId);
    }

    @Override
    public synchronized int maximumKeyValue() {
        // Synchronized so that we don't query table size while the tables are being updated
        return readGroupLookupTable.size() - 1;
    }

    /**
     * If the sample has a PU tag annotation, return that. If not, return the read group id.
     *
     * @param rg the read group record
     * @return platform unit or readgroup id
     */
    private String readGroupValueFromRG(final GATKSAMReadGroupRecord rg) {
        if ( forceReadGroup != null )
            return forceReadGroup;

        final String platformUnit = rg.getPlatformUnit();
        return platformUnit == null ? rg.getId() : platformUnit;
    }
    
}


