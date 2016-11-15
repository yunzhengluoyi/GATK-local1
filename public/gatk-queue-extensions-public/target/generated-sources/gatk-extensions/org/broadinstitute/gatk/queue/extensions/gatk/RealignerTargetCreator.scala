package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.queue.function.scattergather.ScatterGatherableFunction
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Input
import org.broadinstitute.gatk.utils.commandline.Output

class RealignerTargetCreator extends org.broadinstitute.gatk.queue.extensions.gatk.CommandLineGATK with ScatterGatherableFunction {
analysisName = "RealignerTargetCreator"
analysis_type = "RealignerTargetCreator"
scatterClass = classOf[LocusScatterFunction]
setupScatterFunction = { case scatter: GATKScatterFunction => scatter.includeUnmapped = false }

/** An output file created by the walker.  Will overwrite contents if file exists */
@Output(fullName="out", shortName="o", doc="An output file created by the walker.  Will overwrite contents if file exists", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[org.broadinstitute.gatk.queue.function.scattergather.SimpleTextGatherFunction])
var out: File = _

/**
 * Short name of out
 * @return Short name of out
 */
def o = this.out

/**
 * Short name of out
 * @param value Short name of out
 */
def o_=(value: File) { this.out = value }

/** Input VCF file with known indels */
@Input(fullName="known", shortName="known", doc="Input VCF file with known indels", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var known: Seq[File] = Nil

/** Dependencies on any indexes of known */
@Input(fullName="knownIndexes", shortName="", doc="Dependencies on any indexes of known", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var knownIndexes: Seq[File] = Nil

/** window size for calculating entropy or SNP clusters */
@Argument(fullName="windowSize", shortName="window", doc="window size for calculating entropy or SNP clusters", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var windowSize: Option[Int] = None

/**
 * Short name of windowSize
 * @return Short name of windowSize
 */
def window = this.windowSize

/**
 * Short name of windowSize
 * @param value Short name of windowSize
 */
def window_=(value: Option[Int]) { this.windowSize = value }

/** fraction of base qualities needing to mismatch for a position to have high entropy */
@Argument(fullName="mismatchFraction", shortName="mismatch", doc="fraction of base qualities needing to mismatch for a position to have high entropy", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var mismatchFraction: Option[Double] = None

/**
 * Short name of mismatchFraction
 * @return Short name of mismatchFraction
 */
def mismatch = this.mismatchFraction

/**
 * Short name of mismatchFraction
 * @param value Short name of mismatchFraction
 */
def mismatch_=(value: Option[Double]) { this.mismatchFraction = value }

/** Format string for mismatchFraction */
@Argument(fullName="mismatchFractionFormat", shortName="", doc="Format string for mismatchFraction", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var mismatchFractionFormat: String = "%s"

/** minimum reads at a locus to enable using the entropy calculation */
@Argument(fullName="minReadsAtLocus", shortName="minReads", doc="minimum reads at a locus to enable using the entropy calculation", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var minReadsAtLocus: Option[Int] = None

/**
 * Short name of minReadsAtLocus
 * @return Short name of minReadsAtLocus
 */
def minReads = this.minReadsAtLocus

/**
 * Short name of minReadsAtLocus
 * @param value Short name of minReadsAtLocus
 */
def minReads_=(value: Option[Int]) { this.minReadsAtLocus = value }

/** maximum interval size; any intervals larger than this value will be dropped */
@Argument(fullName="maxIntervalSize", shortName="maxInterval", doc="maximum interval size; any intervals larger than this value will be dropped", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var maxIntervalSize: Option[Int] = None

/**
 * Short name of maxIntervalSize
 * @return Short name of maxIntervalSize
 */
def maxInterval = this.maxIntervalSize

/**
 * Short name of maxIntervalSize
 * @param value Short name of maxIntervalSize
 */
def maxInterval_=(value: Option[Int]) { this.maxIntervalSize = value }

/** Filter out reads with CIGAR containing the N operator, instead of failing with an error */
@Argument(fullName="filter_reads_with_N_cigar", shortName="filterRNC", doc="Filter out reads with CIGAR containing the N operator, instead of failing with an error", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var filter_reads_with_N_cigar: Boolean = _

/**
 * Short name of filter_reads_with_N_cigar
 * @return Short name of filter_reads_with_N_cigar
 */
def filterRNC = this.filter_reads_with_N_cigar

/**
 * Short name of filter_reads_with_N_cigar
 * @param value Short name of filter_reads_with_N_cigar
 */
def filterRNC_=(value: Boolean) { this.filter_reads_with_N_cigar = value }

/** Filter out reads with mismatching numbers of bases and base qualities, instead of failing with an error */
@Argument(fullName="filter_mismatching_base_and_quals", shortName="filterMBQ", doc="Filter out reads with mismatching numbers of bases and base qualities, instead of failing with an error", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var filter_mismatching_base_and_quals: Boolean = _

/**
 * Short name of filter_mismatching_base_and_quals
 * @return Short name of filter_mismatching_base_and_quals
 */
def filterMBQ = this.filter_mismatching_base_and_quals

/**
 * Short name of filter_mismatching_base_and_quals
 * @param value Short name of filter_mismatching_base_and_quals
 */
def filterMBQ_=(value: Boolean) { this.filter_mismatching_base_and_quals = value }

/** Filter out reads with no stored bases (i.e. '*' where the sequence should be), instead of failing with an error */
@Argument(fullName="filter_bases_not_stored", shortName="filterNoBases", doc="Filter out reads with no stored bases (i.e. '*' where the sequence should be), instead of failing with an error", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var filter_bases_not_stored: Boolean = _

/**
 * Short name of filter_bases_not_stored
 * @return Short name of filter_bases_not_stored
 */
def filterNoBases = this.filter_bases_not_stored

/**
 * Short name of filter_bases_not_stored
 * @param value Short name of filter_bases_not_stored
 */
def filterNoBases_=(value: Boolean) { this.filter_bases_not_stored = value }

override def freezeFieldValues() {
super.freezeFieldValues()
knownIndexes ++= known.filter(orig => orig != null && (!orig.getName.endsWith(".list"))).map(orig => new File(orig.getPath + ".idx"))
}

override def commandLine = super.commandLine + optional("-o", out, spaceSeparated=true, escape=true, format="%s") + repeat("-known", known, formatPrefix=TaggedFile.formatCommandLineParameter, spaceSeparated=true, escape=true, format="%s") + optional("-window", windowSize, spaceSeparated=true, escape=true, format="%s") + optional("-mismatch", mismatchFraction, spaceSeparated=true, escape=true, format=mismatchFractionFormat) + optional("-minReads", minReadsAtLocus, spaceSeparated=true, escape=true, format="%s") + optional("-maxInterval", maxIntervalSize, spaceSeparated=true, escape=true, format="%s") + conditional(filter_reads_with_N_cigar, "-filterRNC", escape=true, format="%s") + conditional(filter_mismatching_base_and_quals, "-filterMBQ", escape=true, format="%s") + conditional(filter_bases_not_stored, "-filterNoBases", escape=true, format="%s")
}
