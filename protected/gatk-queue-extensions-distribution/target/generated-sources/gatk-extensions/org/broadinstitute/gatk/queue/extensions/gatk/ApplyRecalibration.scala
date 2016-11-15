package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.queue.function.scattergather.ScatterGatherableFunction
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Input
import org.broadinstitute.gatk.utils.commandline.Output

class ApplyRecalibration extends org.broadinstitute.gatk.queue.extensions.gatk.CommandLineGATK with ScatterGatherableFunction {
analysisName = "ApplyRecalibration"
analysis_type = "ApplyRecalibration"
scatterClass = classOf[LocusScatterFunction]
setupScatterFunction = { case scatter: GATKScatterFunction => scatter.includeUnmapped = false }

/** The raw input variants to be recalibrated */
@Input(fullName="input", shortName="input", doc="The raw input variants to be recalibrated", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
var input: Seq[File] = Nil

/** Dependencies on any indexes of input */
@Input(fullName="inputIndexes", shortName="", doc="Dependencies on any indexes of input", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var inputIndexes: Seq[File] = Nil

/** The input recal file used by ApplyRecalibration */
@Input(fullName="recal_file", shortName="recalFile", doc="The input recal file used by ApplyRecalibration", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
var recal_file: File = _

/**
 * Short name of recal_file
 * @return Short name of recal_file
 */
def recalFile = this.recal_file

/**
 * Short name of recal_file
 * @param value Short name of recal_file
 */
def recalFile_=(value: File) { this.recal_file = value }

/** Dependencies on the index of recal_file */
@Input(fullName="recal_fileIndex", shortName="", doc="Dependencies on the index of recal_file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var recal_fileIndex: Seq[File] = Nil

/** The input tranches file describing where to cut the data */
@Input(fullName="tranches_file", shortName="tranchesFile", doc="The input tranches file describing where to cut the data", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var tranches_file: File = _

/**
 * Short name of tranches_file
 * @return Short name of tranches_file
 */
def tranchesFile = this.tranches_file

/**
 * Short name of tranches_file
 * @param value Short name of tranches_file
 */
def tranchesFile_=(value: File) { this.tranches_file = value }

/** The output filtered and recalibrated VCF file in which each variant is annotated with its VQSLOD value */
@Output(fullName="out", shortName="o", doc="The output filtered and recalibrated VCF file in which each variant is annotated with its VQSLOD value", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[CatVariantsGatherer])
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

/** Automatically generated index for out */
@Output(fullName="outIndex", shortName="", doc="Automatically generated index for out", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(enabled=false)
private var outIndex: File = _

/** The truth sensitivity level at which to start filtering */
@Argument(fullName="ts_filter_level", shortName="ts_filter_level", doc="The truth sensitivity level at which to start filtering", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var ts_filter_level: Option[Double] = None

/** Format string for ts_filter_level */
@Argument(fullName="ts_filter_levelFormat", shortName="", doc="Format string for ts_filter_level", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var ts_filter_levelFormat: String = "%s"

/** If specified, the tool will attempt to apply a filter to each allele based on the input tranches and allele-specific .recal file. */
@Argument(fullName="useAlleleSpecificAnnotations", shortName="AS", doc="If specified, the tool will attempt to apply a filter to each allele based on the input tranches and allele-specific .recal file.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var useAlleleSpecificAnnotations: Boolean = _

/**
 * Short name of useAlleleSpecificAnnotations
 * @return Short name of useAlleleSpecificAnnotations
 */
def AS = this.useAlleleSpecificAnnotations

/**
 * Short name of useAlleleSpecificAnnotations
 * @param value Short name of useAlleleSpecificAnnotations
 */
def AS_=(value: Boolean) { this.useAlleleSpecificAnnotations = value }

/** The VQSLOD score below which to start filtering */
@Argument(fullName="lodCutoff", shortName="lodCutoff", doc="The VQSLOD score below which to start filtering", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var lodCutoff: Option[Double] = None

/** Format string for lodCutoff */
@Argument(fullName="lodCutoffFormat", shortName="", doc="Format string for lodCutoff", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var lodCutoffFormat: String = "%s"

/** If specified, the recalibration will be applied to variants marked as filtered by the specified filter name in the input VCF file */
@Argument(fullName="ignore_filter", shortName="ignoreFilter", doc="If specified, the recalibration will be applied to variants marked as filtered by the specified filter name in the input VCF file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var ignore_filter: Seq[String] = Nil

/**
 * Short name of ignore_filter
 * @return Short name of ignore_filter
 */
def ignoreFilter = this.ignore_filter

/**
 * Short name of ignore_filter
 * @param value Short name of ignore_filter
 */
def ignoreFilter_=(value: Seq[String]) { this.ignore_filter = value }

/** If specified, the variant recalibrator will ignore all input filters. Useful to rerun the VQSR from a filtered output file. */
@Argument(fullName="ignore_all_filters", shortName="ignoreAllFilters", doc="If specified, the variant recalibrator will ignore all input filters. Useful to rerun the VQSR from a filtered output file.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var ignore_all_filters: Boolean = _

/**
 * Short name of ignore_all_filters
 * @return Short name of ignore_all_filters
 */
def ignoreAllFilters = this.ignore_all_filters

/**
 * Short name of ignore_all_filters
 * @param value Short name of ignore_all_filters
 */
def ignoreAllFilters_=(value: Boolean) { this.ignore_all_filters = value }

/** Don't output filtered loci after applying the recalibration */
@Argument(fullName="excludeFiltered", shortName="ef", doc="Don't output filtered loci after applying the recalibration", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var excludeFiltered: Boolean = _

/**
 * Short name of excludeFiltered
 * @return Short name of excludeFiltered
 */
def ef = this.excludeFiltered

/**
 * Short name of excludeFiltered
 * @param value Short name of excludeFiltered
 */
def ef_=(value: Boolean) { this.excludeFiltered = value }

/** Recalibration mode to employ: 1.) SNP for recalibrating only SNPs (emitting indels untouched in the output VCF); 2.) INDEL for indels; and 3.) BOTH for recalibrating both SNPs and indels simultaneously. */
@Argument(fullName="mode", shortName="mode", doc="Recalibration mode to employ: 1.) SNP for recalibrating only SNPs (emitting indels untouched in the output VCF); 2.) INDEL for indels; and 3.) BOTH for recalibrating both SNPs and indels simultaneously.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var mode: org.broadinstitute.gatk.tools.walkers.variantrecalibration.VariantRecalibratorArgumentCollection.Mode = _

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
inputIndexes ++= input.filter(orig => orig != null && (!orig.getName.endsWith(".list"))).map(orig => new File(orig.getPath + ".idx"))
if (recal_file != null)
  recal_fileIndex :+= new File(recal_file.getPath + ".idx")
if (out != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(out))
  if (!org.broadinstitute.gatk.utils.commandline.ArgumentTypeDescriptor.isCompressed(out.getPath))
    outIndex = new File(out.getPath + ".idx")
}

override def commandLine = super.commandLine + repeat("-input", input, formatPrefix=TaggedFile.formatCommandLineParameter, spaceSeparated=true, escape=true, format="%s") + required(TaggedFile.formatCommandLineParameter("-recalFile", recal_file), recal_file, spaceSeparated=true, escape=true, format="%s") + optional("-tranchesFile", tranches_file, spaceSeparated=true, escape=true, format="%s") + optional("-o", out, spaceSeparated=true, escape=true, format="%s") + optional("-ts_filter_level", ts_filter_level, spaceSeparated=true, escape=true, format=ts_filter_levelFormat) + conditional(useAlleleSpecificAnnotations, "-AS", escape=true, format="%s") + optional("-lodCutoff", lodCutoff, spaceSeparated=true, escape=true, format=lodCutoffFormat) + repeat("-ignoreFilter", ignore_filter, spaceSeparated=true, escape=true, format="%s") + conditional(ignore_all_filters, "-ignoreAllFilters", escape=true, format="%s") + conditional(excludeFiltered, "-ef", escape=true, format="%s") + optional("-mode", mode, spaceSeparated=true, escape=true, format="%s") + conditional(filter_reads_with_N_cigar, "-filterRNC", escape=true, format="%s") + conditional(filter_mismatching_base_and_quals, "-filterMBQ", escape=true, format="%s") + conditional(filter_bases_not_stored, "-filterNoBases", escape=true, format="%s")
}
