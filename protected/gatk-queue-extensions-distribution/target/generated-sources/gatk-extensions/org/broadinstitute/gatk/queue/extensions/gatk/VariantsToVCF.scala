package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.queue.function.scattergather.ScatterGatherableFunction
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Input
import org.broadinstitute.gatk.utils.commandline.Output

class VariantsToVCF extends org.broadinstitute.gatk.queue.extensions.gatk.CommandLineGATK with ScatterGatherableFunction {
analysisName = "VariantsToVCF"
analysis_type = "VariantsToVCF"
scatterClass = classOf[LocusScatterFunction]
setupScatterFunction = { case scatter: GATKScatterFunction => scatter.includeUnmapped = false }

/** File to which variants should be written */
@Output(fullName="out", shortName="o", doc="File to which variants should be written", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
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

/** Input variant file */
@Input(fullName="variant", shortName="V", doc="Input variant file", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
var variant: File = _

/**
 * Short name of variant
 * @return Short name of variant
 */
def V = this.variant

/**
 * Short name of variant
 * @param value Short name of variant
 */
def V_=(value: File) { this.variant = value }

/** Dependencies on the index of variant */
@Input(fullName="variantIndex", shortName="", doc="Dependencies on the index of variant", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var variantIndex: Seq[File] = Nil

/** dbSNP file */
@Input(fullName="dbsnp", shortName="D", doc="dbSNP file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dbsnp: File = _

/**
 * Short name of dbsnp
 * @return Short name of dbsnp
 */
def D = this.dbsnp

/**
 * Short name of dbsnp
 * @param value Short name of dbsnp
 */
def D_=(value: File) { this.dbsnp = value }

/** Dependencies on the index of dbsnp */
@Input(fullName="dbsnpIndex", shortName="", doc="Dependencies on the index of dbsnp", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var dbsnpIndex: Seq[File] = Nil

/** The sample name represented by the variant rod */
@Argument(fullName="sample", shortName="sample", doc="The sample name represented by the variant rod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var sample: String = _

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
if (out != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(out))
  if (!org.broadinstitute.gatk.utils.commandline.ArgumentTypeDescriptor.isCompressed(out.getPath))
    outIndex = new File(out.getPath + ".idx")
if (variant != null)
  variantIndex :+= new File(variant.getPath + ".idx")
if (dbsnp != null)
  dbsnpIndex :+= new File(dbsnp.getPath + ".idx")
}

override def commandLine = super.commandLine + optional("-o", out, spaceSeparated=true, escape=true, format="%s") + required(TaggedFile.formatCommandLineParameter("-V", variant), variant, spaceSeparated=true, escape=true, format="%s") + optional(TaggedFile.formatCommandLineParameter("-D", dbsnp), dbsnp, spaceSeparated=true, escape=true, format="%s") + optional("-sample", sample, spaceSeparated=true, escape=true, format="%s") + conditional(filter_reads_with_N_cigar, "-filterRNC", escape=true, format="%s") + conditional(filter_mismatching_base_and_quals, "-filterMBQ", escape=true, format="%s") + conditional(filter_bases_not_stored, "-filterNoBases", escape=true, format="%s")
}
