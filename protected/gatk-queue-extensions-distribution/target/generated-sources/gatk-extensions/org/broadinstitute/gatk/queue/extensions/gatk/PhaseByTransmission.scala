package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.queue.function.scattergather.ScatterGatherableFunction
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Input
import org.broadinstitute.gatk.utils.commandline.Output

class PhaseByTransmission extends org.broadinstitute.gatk.queue.extensions.gatk.CommandLineGATK with ScatterGatherableFunction {
analysisName = "PhaseByTransmission"
analysis_type = "PhaseByTransmission"
scatterClass = classOf[LocusScatterFunction]
setupScatterFunction = { case scatter: GATKScatterFunction => scatter.includeUnmapped = false }

/** Input VCF file */
@Input(fullName="variant", shortName="V", doc="Input VCF file", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
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

/** File to output the mendelian violation details. */
@Argument(fullName="MendelianViolationsFile", shortName="mvf", doc="File to output the mendelian violation details.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var MendelianViolationsFile: File = _

/**
 * Short name of MendelianViolationsFile
 * @return Short name of MendelianViolationsFile
 */
def mvf = this.MendelianViolationsFile

/**
 * Short name of MendelianViolationsFile
 * @param value Short name of MendelianViolationsFile
 */
def mvf_=(value: File) { this.MendelianViolationsFile = value }

/** Prior for de novo mutations. Default: 1e-8 */
@Argument(fullName="DeNovoPrior", shortName="prior", doc="Prior for de novo mutations. Default: 1e-8", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var DeNovoPrior: Option[Double] = None

/**
 * Short name of DeNovoPrior
 * @return Short name of DeNovoPrior
 */
def prior = this.DeNovoPrior

/**
 * Short name of DeNovoPrior
 * @param value Short name of DeNovoPrior
 */
def prior_=(value: Option[Double]) { this.DeNovoPrior = value }

/** Format string for DeNovoPrior */
@Argument(fullName="DeNovoPriorFormat", shortName="", doc="Format string for DeNovoPrior", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var DeNovoPriorFormat: String = "%s"

/** Ouputs the father allele as the first allele in phased child genotype. i.e. father|mother rather than mother|father. */
@Argument(fullName="FatherAlleleFirst", shortName="fatherAlleleFirst", doc="Ouputs the father allele as the first allele in phased child genotype. i.e. father|mother rather than mother|father.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var FatherAlleleFirst: Boolean = _

/**
 * Short name of FatherAlleleFirst
 * @return Short name of FatherAlleleFirst
 */
def fatherAlleleFirst = this.FatherAlleleFirst

/**
 * Short name of FatherAlleleFirst
 * @param value Short name of FatherAlleleFirst
 */
def fatherAlleleFirst_=(value: Boolean) { this.FatherAlleleFirst = value }

/** An output file created by the walker.  Will overwrite contents if file exists */
@Output(fullName="out", shortName="o", doc="An output file created by the walker.  Will overwrite contents if file exists", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
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
if (variant != null)
  variantIndex :+= new File(variant.getPath + ".idx")
if (out != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(out))
  if (!org.broadinstitute.gatk.utils.commandline.ArgumentTypeDescriptor.isCompressed(out.getPath))
    outIndex = new File(out.getPath + ".idx")
}

override def commandLine = super.commandLine + required(TaggedFile.formatCommandLineParameter("-V", variant), variant, spaceSeparated=true, escape=true, format="%s") + optional("-mvf", MendelianViolationsFile, spaceSeparated=true, escape=true, format="%s") + optional("-prior", DeNovoPrior, spaceSeparated=true, escape=true, format=DeNovoPriorFormat) + conditional(FatherAlleleFirst, "-fatherAlleleFirst", escape=true, format="%s") + optional("-o", out, spaceSeparated=true, escape=true, format="%s") + conditional(filter_reads_with_N_cigar, "-filterRNC", escape=true, format="%s") + conditional(filter_mismatching_base_and_quals, "-filterMBQ", escape=true, format="%s") + conditional(filter_bases_not_stored, "-filterNoBases", escape=true, format="%s")
}
