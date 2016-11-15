package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.queue.function.scattergather.ScatterGatherableFunction
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Input
import org.broadinstitute.gatk.utils.commandline.Output

class MuTect2 extends org.broadinstitute.gatk.queue.extensions.gatk.CommandLineGATK with ScatterGatherableFunction {
analysisName = "MuTect2"
analysis_type = "MuTect2"
scatterClass = classOf[LocusScatterFunction]
setupScatterFunction = { case scatter: GATKScatterFunction => scatter.includeUnmapped = false }

/** Print out very verbose M2 debug information */
@Argument(fullName="m2debug", shortName="m2debug", doc="Print out very verbose M2 debug information", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var m2debug: Boolean = _

/** Enable artifact detection for creating panels of normals */
@Argument(fullName="artifact_detection_mode", shortName="", doc="Enable artifact detection for creating panels of normals", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var artifact_detection_mode: Boolean = _

/** Initial LOD threshold for calling tumor variant */
@Argument(fullName="initial_tumor_lod", shortName="", doc="Initial LOD threshold for calling tumor variant", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var initial_tumor_lod: Option[Double] = None

/** Format string for initial_tumor_lod */
@Argument(fullName="initial_tumor_lodFormat", shortName="", doc="Format string for initial_tumor_lod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var initial_tumor_lodFormat: String = "%s"

/** Initial LOD threshold for calling normal variant */
@Argument(fullName="initial_normal_lod", shortName="", doc="Initial LOD threshold for calling normal variant", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var initial_normal_lod: Option[Double] = None

/** Format string for initial_normal_lod */
@Argument(fullName="initial_normal_lodFormat", shortName="", doc="Format string for initial_normal_lod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var initial_normal_lodFormat: String = "%s"

/** LOD threshold for calling tumor variant */
@Argument(fullName="tumor_lod", shortName="", doc="LOD threshold for calling tumor variant", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var tumor_lod: Option[Double] = None

/** Format string for tumor_lod */
@Argument(fullName="tumor_lodFormat", shortName="", doc="Format string for tumor_lod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var tumor_lodFormat: String = "%s"

/** LOD threshold for calling normal non-germline */
@Argument(fullName="normal_lod", shortName="", doc="LOD threshold for calling normal non-germline", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var normal_lod: Option[Double] = None

/** Format string for normal_lod */
@Argument(fullName="normal_lodFormat", shortName="", doc="Format string for normal_lod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var normal_lodFormat: String = "%s"

/** LOD threshold for calling normal non-variant at dbsnp sites */
@Argument(fullName="dbsnp_normal_lod", shortName="", doc="LOD threshold for calling normal non-variant at dbsnp sites", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dbsnp_normal_lod: Option[Double] = None

/** Format string for dbsnp_normal_lod */
@Argument(fullName="dbsnp_normal_lodFormat", shortName="", doc="Format string for dbsnp_normal_lod", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dbsnp_normal_lodFormat: String = "%s"

/** Threshold for maximum alternate allele counts in normal */
@Argument(fullName="max_alt_alleles_in_normal_count", shortName="", doc="Threshold for maximum alternate allele counts in normal", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_alt_alleles_in_normal_count: Option[Int] = None

/** Threshold for maximum alternate allele quality score sum in normal */
@Argument(fullName="max_alt_alleles_in_normal_qscore_sum", shortName="", doc="Threshold for maximum alternate allele quality score sum in normal", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_alt_alleles_in_normal_qscore_sum: Option[Int] = None

/** Threshold for maximum alternate allele fraction in normal */
@Argument(fullName="max_alt_allele_in_normal_fraction", shortName="", doc="Threshold for maximum alternate allele fraction in normal", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_alt_allele_in_normal_fraction: Option[Double] = None

/** Format string for max_alt_allele_in_normal_fraction */
@Argument(fullName="max_alt_allele_in_normal_fractionFormat", shortName="", doc="Format string for max_alt_allele_in_normal_fraction", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_alt_allele_in_normal_fractionFormat: String = "%s"

/** Print out very verbose debug information about each triggering active region */
@Argument(fullName="debug", shortName="debug", doc="Print out very verbose debug information about each triggering active region", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var debug: Boolean = _

/** Use the contamination-filtered read maps for the purposes of annotating variants */
@Argument(fullName="useFilteredReadsForAnnotations", shortName="useFilteredReadsForAnnotations", doc="Use the contamination-filtered read maps for the purposes of annotating variants", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var useFilteredReadsForAnnotations: Boolean = _

/** Mode for emitting reference confidence scores */
@Argument(fullName="emitRefConfidence", shortName="ERC", doc="Mode for emitting reference confidence scores", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var emitRefConfidence: org.broadinstitute.gatk.tools.walkers.haplotypecaller.ReferenceConfidenceMode = _

/**
 * Short name of emitRefConfidence
 * @return Short name of emitRefConfidence
 */
def ERC = this.emitRefConfidence

/**
 * Short name of emitRefConfidence
 * @param value Short name of emitRefConfidence
 */
def ERC_=(value: org.broadinstitute.gatk.tools.walkers.haplotypecaller.ReferenceConfidenceMode) { this.emitRefConfidence = value }

/** File to which assembled haplotypes should be written */
@Output(fullName="bamOutput", shortName="bamout", doc="File to which assembled haplotypes should be written", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[BamGatherFunction])
var bamOutput: File = _

/**
 * Short name of bamOutput
 * @return Short name of bamOutput
 */
def bamout = this.bamOutput

/**
 * Short name of bamOutput
 * @param value Short name of bamOutput
 */
def bamout_=(value: File) { this.bamOutput = value }

/** Automatically generated index for bamOutput */
@Output(fullName="bamOutputIndex", shortName="", doc="Automatically generated index for bamOutput", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(enabled=false)
private var bamOutputIndex: File = _

/** Automatically generated md5 for bamOutput */
@Output(fullName="bamOutputMD5", shortName="", doc="Automatically generated md5 for bamOutput", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(enabled=false)
private var bamOutputMD5: File = _

/** Which haplotypes should be written to the BAM */
@Argument(fullName="bamWriterType", shortName="bamWriterType", doc="Which haplotypes should be written to the BAM", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var bamWriterType: org.broadinstitute.gatk.utils.haplotypeBAMWriter.HaplotypeBAMWriter.Type = _

/** Emit reads that are dropped for filtering, trimming, realignment failure */
@Argument(fullName="emitDroppedReads", shortName="edr", doc="Emit reads that are dropped for filtering, trimming, realignment failure", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var emitDroppedReads: Boolean = _

/**
 * Short name of emitDroppedReads
 * @return Short name of emitDroppedReads
 */
def edr = this.emitDroppedReads

/**
 * Short name of emitDroppedReads
 * @param value Short name of emitDroppedReads
 */
def edr_=(value: Boolean) { this.emitDroppedReads = value }

/** Don't skip calculations in ActiveRegions with no variants */
@Argument(fullName="disableOptimizations", shortName="disableOptimizations", doc="Don't skip calculations in ActiveRegions with no variants", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var disableOptimizations: Boolean = _

/** If provided, we will annotate records with the number of alternate alleles that were discovered (but not necessarily genotyped) at a given site */
@Argument(fullName="annotateNDA", shortName="nda", doc="If provided, we will annotate records with the number of alternate alleles that were discovered (but not necessarily genotyped) at a given site", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var annotateNDA: Boolean = _

/**
 * Short name of annotateNDA
 * @return Short name of annotateNDA
 */
def nda = this.annotateNDA

/**
 * Short name of annotateNDA
 * @param value Short name of annotateNDA
 */
def nda_=(value: Boolean) { this.annotateNDA = value }

/** Heterozygosity value used to compute prior likelihoods for any locus */
@Argument(fullName="heterozygosity", shortName="hets", doc="Heterozygosity value used to compute prior likelihoods for any locus", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var heterozygosity: Option[Double] = None

/**
 * Short name of heterozygosity
 * @return Short name of heterozygosity
 */
def hets = this.heterozygosity

/**
 * Short name of heterozygosity
 * @param value Short name of heterozygosity
 */
def hets_=(value: Option[Double]) { this.heterozygosity = value }

/** Format string for heterozygosity */
@Argument(fullName="heterozygosityFormat", shortName="", doc="Format string for heterozygosity", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var heterozygosityFormat: String = "%s"

/** Heterozygosity for indel calling */
@Argument(fullName="indel_heterozygosity", shortName="indelHeterozygosity", doc="Heterozygosity for indel calling", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var indel_heterozygosity: Option[Double] = None

/**
 * Short name of indel_heterozygosity
 * @return Short name of indel_heterozygosity
 */
def indelHeterozygosity = this.indel_heterozygosity

/**
 * Short name of indel_heterozygosity
 * @param value Short name of indel_heterozygosity
 */
def indelHeterozygosity_=(value: Option[Double]) { this.indel_heterozygosity = value }

/** Format string for indel_heterozygosity */
@Argument(fullName="indel_heterozygosityFormat", shortName="", doc="Format string for indel_heterozygosity", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var indel_heterozygosityFormat: String = "%s"

/** The minimum phred-scaled confidence threshold at which variants should be called */
@Argument(fullName="standard_min_confidence_threshold_for_calling", shortName="stand_call_conf", doc="The minimum phred-scaled confidence threshold at which variants should be called", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var standard_min_confidence_threshold_for_calling: Option[Double] = None

/**
 * Short name of standard_min_confidence_threshold_for_calling
 * @return Short name of standard_min_confidence_threshold_for_calling
 */
def stand_call_conf = this.standard_min_confidence_threshold_for_calling

/**
 * Short name of standard_min_confidence_threshold_for_calling
 * @param value Short name of standard_min_confidence_threshold_for_calling
 */
def stand_call_conf_=(value: Option[Double]) { this.standard_min_confidence_threshold_for_calling = value }

/** Format string for standard_min_confidence_threshold_for_calling */
@Argument(fullName="standard_min_confidence_threshold_for_callingFormat", shortName="", doc="Format string for standard_min_confidence_threshold_for_calling", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var standard_min_confidence_threshold_for_callingFormat: String = "%s"

/** The minimum phred-scaled confidence threshold at which variants should be emitted (and filtered with LowQual if less than the calling threshold) */
@Argument(fullName="standard_min_confidence_threshold_for_emitting", shortName="stand_emit_conf", doc="The minimum phred-scaled confidence threshold at which variants should be emitted (and filtered with LowQual if less than the calling threshold)", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var standard_min_confidence_threshold_for_emitting: Option[Double] = None

/**
 * Short name of standard_min_confidence_threshold_for_emitting
 * @return Short name of standard_min_confidence_threshold_for_emitting
 */
def stand_emit_conf = this.standard_min_confidence_threshold_for_emitting

/**
 * Short name of standard_min_confidence_threshold_for_emitting
 * @param value Short name of standard_min_confidence_threshold_for_emitting
 */
def stand_emit_conf_=(value: Option[Double]) { this.standard_min_confidence_threshold_for_emitting = value }

/** Format string for standard_min_confidence_threshold_for_emitting */
@Argument(fullName="standard_min_confidence_threshold_for_emittingFormat", shortName="", doc="Format string for standard_min_confidence_threshold_for_emitting", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var standard_min_confidence_threshold_for_emittingFormat: String = "%s"

/** Maximum number of alternate alleles to genotype */
@Argument(fullName="max_alternate_alleles", shortName="maxAltAlleles", doc="Maximum number of alternate alleles to genotype", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_alternate_alleles: Option[Int] = None

/**
 * Short name of max_alternate_alleles
 * @return Short name of max_alternate_alleles
 */
def maxAltAlleles = this.max_alternate_alleles

/**
 * Short name of max_alternate_alleles
 * @param value Short name of max_alternate_alleles
 */
def maxAltAlleles_=(value: Option[Int]) { this.max_alternate_alleles = value }

/** Maximum number of PL values to output */
@Argument(fullName="max_num_PL_values", shortName="maxNumPLValues", doc="Maximum number of PL values to output", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var max_num_PL_values: Option[Int] = None

/**
 * Short name of max_num_PL_values
 * @return Short name of max_num_PL_values
 */
def maxNumPLValues = this.max_num_PL_values

/**
 * Short name of max_num_PL_values
 * @param value Short name of max_num_PL_values
 */
def maxNumPLValues_=(value: Option[Int]) { this.max_num_PL_values = value }

/** Input prior for calls */
@Argument(fullName="input_prior", shortName="inputPrior", doc="Input prior for calls", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var input_prior: Seq[Double] = Nil

/**
 * Short name of input_prior
 * @return Short name of input_prior
 */
def inputPrior = this.input_prior

/**
 * Short name of input_prior
 * @param value Short name of input_prior
 */
def inputPrior_=(value: Seq[Double]) { this.input_prior = value }

/** Ploidy (number of chromosomes) per sample. For pooled data, set to (Number of samples in each pool * Sample Ploidy). */
@Argument(fullName="sample_ploidy", shortName="ploidy", doc="Ploidy (number of chromosomes) per sample. For pooled data, set to (Number of samples in each pool * Sample Ploidy).", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var sample_ploidy: Option[Int] = None

/**
 * Short name of sample_ploidy
 * @return Short name of sample_ploidy
 */
def ploidy = this.sample_ploidy

/**
 * Short name of sample_ploidy
 * @param value Short name of sample_ploidy
 */
def ploidy_=(value: Option[Int]) { this.sample_ploidy = value }

/** Specifies how to determine the alternate alleles to use for genotyping */
@Argument(fullName="genotyping_mode", shortName="gt_mode", doc="Specifies how to determine the alternate alleles to use for genotyping", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var genotyping_mode: org.broadinstitute.gatk.tools.walkers.genotyper.GenotypingOutputMode = _

/**
 * Short name of genotyping_mode
 * @return Short name of genotyping_mode
 */
def gt_mode = this.genotyping_mode

/**
 * Short name of genotyping_mode
 * @param value Short name of genotyping_mode
 */
def gt_mode_=(value: org.broadinstitute.gatk.tools.walkers.genotyper.GenotypingOutputMode) { this.genotyping_mode = value }

/** The set of alleles at which to genotype when --genotyping_mode is GENOTYPE_GIVEN_ALLELES */
@Input(fullName="alleles", shortName="alleles", doc="The set of alleles at which to genotype when --genotyping_mode is GENOTYPE_GIVEN_ALLELES", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var alleles: File = _

/** Dependencies on the index of alleles */
@Input(fullName="allelesIndex", shortName="", doc="Dependencies on the index of alleles", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var allelesIndex: Seq[File] = Nil

/** Fraction of contamination in sequencing data (for all samples) to aggressively remove */
@Argument(fullName="contamination_fraction_to_filter", shortName="contamination", doc="Fraction of contamination in sequencing data (for all samples) to aggressively remove", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var contamination_fraction_to_filter: Option[Double] = None

/**
 * Short name of contamination_fraction_to_filter
 * @return Short name of contamination_fraction_to_filter
 */
def contamination = this.contamination_fraction_to_filter

/**
 * Short name of contamination_fraction_to_filter
 * @param value Short name of contamination_fraction_to_filter
 */
def contamination_=(value: Option[Double]) { this.contamination_fraction_to_filter = value }

/** Format string for contamination_fraction_to_filter */
@Argument(fullName="contamination_fraction_to_filterFormat", shortName="", doc="Format string for contamination_fraction_to_filter", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var contamination_fraction_to_filterFormat: String = "%s"

/** Tab-separated File containing fraction of contamination in sequencing data (per sample) to aggressively remove. Format should be \"<SampleID><TAB><Contamination>\" (Contamination is double) per line; No header. */
@Argument(fullName="contamination_fraction_per_sample_file", shortName="contaminationFile", doc="Tab-separated File containing fraction of contamination in sequencing data (per sample) to aggressively remove. Format should be \"<SampleID><TAB><Contamination>\" (Contamination is double) per line; No header.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var contamination_fraction_per_sample_file: File = _

/**
 * Short name of contamination_fraction_per_sample_file
 * @return Short name of contamination_fraction_per_sample_file
 */
def contaminationFile = this.contamination_fraction_per_sample_file

/**
 * Short name of contamination_fraction_per_sample_file
 * @param value Short name of contamination_fraction_per_sample_file
 */
def contaminationFile_=(value: File) { this.contamination_fraction_per_sample_file = value }

/** Non-reference probability calculation model to employ */
@Argument(fullName="p_nonref_model", shortName="pnrm", doc="Non-reference probability calculation model to employ", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var p_nonref_model: org.broadinstitute.gatk.tools.walkers.genotyper.afcalc.AFCalculatorImplementation = _

/**
 * Short name of p_nonref_model
 * @return Short name of p_nonref_model
 */
def pnrm = this.p_nonref_model

/**
 * Short name of p_nonref_model
 * @param value Short name of p_nonref_model
 */
def pnrm_=(value: org.broadinstitute.gatk.tools.walkers.genotyper.afcalc.AFCalculatorImplementation) { this.p_nonref_model = value }

/** x */
@Argument(fullName="exactcallslog", shortName="logExactCalls", doc="x", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var exactcallslog: File = _

/**
 * Short name of exactcallslog
 * @return Short name of exactcallslog
 */
def logExactCalls = this.exactcallslog

/**
 * Short name of exactcallslog
 * @param value Short name of exactcallslog
 */
def logExactCalls_=(value: File) { this.exactcallslog = value }

/** Specifies which type of calls we should output */
@Argument(fullName="output_mode", shortName="out_mode", doc="Specifies which type of calls we should output", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var output_mode: org.broadinstitute.gatk.tools.walkers.genotyper.OutputMode = _

/**
 * Short name of output_mode
 * @return Short name of output_mode
 */
def out_mode = this.output_mode

/**
 * Short name of output_mode
 * @param value Short name of output_mode
 */
def out_mode_=(value: org.broadinstitute.gatk.tools.walkers.genotyper.OutputMode) { this.output_mode = value }

/** Annotate all sites with PLs */
@Argument(fullName="allSitePLs", shortName="allSitePLs", doc="Annotate all sites with PLs", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var allSitePLs: Boolean = _

/** Kmer size to use in the read threading assembler */
@Argument(fullName="kmerSize", shortName="kmerSize", doc="Kmer size to use in the read threading assembler", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var kmerSize: Seq[Int] = Nil

/** Disable iterating over kmer sizes when graph cycles are detected */
@Argument(fullName="dontIncreaseKmerSizesForCycles", shortName="dontIncreaseKmerSizesForCycles", doc="Disable iterating over kmer sizes when graph cycles are detected", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dontIncreaseKmerSizesForCycles: Boolean = _

/** Allow graphs that have non-unique kmers in the reference */
@Argument(fullName="allowNonUniqueKmersInRef", shortName="allowNonUniqueKmersInRef", doc="Allow graphs that have non-unique kmers in the reference", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var allowNonUniqueKmersInRef: Boolean = _

/** Number of samples that must pass the minPruning threshold */
@Argument(fullName="numPruningSamples", shortName="numPruningSamples", doc="Number of samples that must pass the minPruning threshold", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var numPruningSamples: Option[Int] = None

/** Disable dangling head and tail recovery */
@Argument(fullName="doNotRecoverDanglingBranches", shortName="doNotRecoverDanglingBranches", doc="Disable dangling head and tail recovery", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var doNotRecoverDanglingBranches: Boolean = _

/** Minimum length of a dangling branch to attempt recovery */
@Argument(fullName="minDanglingBranchLength", shortName="minDanglingBranchLength", doc="Minimum length of a dangling branch to attempt recovery", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var minDanglingBranchLength: Option[Int] = None

/** 1000G consensus mode */
@Argument(fullName="consensus", shortName="consensus", doc="1000G consensus mode", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var consensus: Boolean = _

/** Maximum number of haplotypes to consider for your population */
@Argument(fullName="maxNumHaplotypesInPopulation", shortName="maxNumHaplotypesInPopulation", doc="Maximum number of haplotypes to consider for your population", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var maxNumHaplotypesInPopulation: Option[Int] = None

/** Use an exploratory algorithm to error correct the kmers used during assembly */
@Argument(fullName="errorCorrectKmers", shortName="errorCorrectKmers", doc="Use an exploratory algorithm to error correct the kmers used during assembly", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var errorCorrectKmers: Boolean = _

/** Minimum support to not prune paths in the graph */
@Argument(fullName="minPruning", shortName="minPruning", doc="Minimum support to not prune paths in the graph", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var minPruning: Option[Int] = None

/** Write DOT formatted graph files out of the assembler for only this graph size */
@Argument(fullName="debugGraphTransformations", shortName="debugGraphTransformations", doc="Write DOT formatted graph files out of the assembler for only this graph size", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var debugGraphTransformations: Boolean = _

/** Allow cycles in the kmer graphs to generate paths with multiple copies of the path sequenece rather than just the shortest paths */
@Argument(fullName="allowCyclesInKmerGraphToGeneratePaths", shortName="allowCyclesInKmerGraphToGeneratePaths", doc="Allow cycles in the kmer graphs to generate paths with multiple copies of the path sequenece rather than just the shortest paths", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var allowCyclesInKmerGraphToGeneratePaths: Boolean = _

/** Write debug assembly graph information to this file */
@Output(fullName="graphOutput", shortName="graph", doc="Write debug assembly graph information to this file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[org.broadinstitute.gatk.queue.function.scattergather.SimpleTextGatherFunction])
var graphOutput: File = _

/**
 * Short name of graphOutput
 * @return Short name of graphOutput
 */
def graph = this.graphOutput

/**
 * Short name of graphOutput
 * @param value Short name of graphOutput
 */
def graph_=(value: File) { this.graphOutput = value }

/** Use an exploratory algorithm to error correct the kmers used during assembly */
@Argument(fullName="kmerLengthForReadErrorCorrection", shortName="kmerLengthForReadErrorCorrection", doc="Use an exploratory algorithm to error correct the kmers used during assembly", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var kmerLengthForReadErrorCorrection: Option[Int] = None

/** A k-mer must be seen at least these times for it considered to be solid */
@Argument(fullName="minObservationsForKmerToBeSolid", shortName="minObservationsForKmerToBeSolid", doc="A k-mer must be seen at least these times for it considered to be solid", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var minObservationsForKmerToBeSolid: Option[Int] = None

/** Flat gap continuation penalty for use in the Pair HMM */
@Argument(fullName="gcpHMM", shortName="gcpHMM", doc="Flat gap continuation penalty for use in the Pair HMM", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var gcpHMM: Option[Int] = None

/** The PairHMM implementation to use for genotype likelihood calculations */
@Argument(fullName="pair_hmm_implementation", shortName="pairHMM", doc="The PairHMM implementation to use for genotype likelihood calculations", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var pair_hmm_implementation: org.broadinstitute.gatk.utils.pairhmm.PairHMM.HMM_IMPLEMENTATION = _

/**
 * Short name of pair_hmm_implementation
 * @return Short name of pair_hmm_implementation
 */
def pairHMM = this.pair_hmm_implementation

/**
 * Short name of pair_hmm_implementation
 * @param value Short name of pair_hmm_implementation
 */
def pairHMM_=(value: org.broadinstitute.gatk.utils.pairhmm.PairHMM.HMM_IMPLEMENTATION) { this.pair_hmm_implementation = value }

/** The PairHMM machine-dependent sub-implementation to use for genotype likelihood calculations */
@Argument(fullName="pair_hmm_sub_implementation", shortName="pairHMMSub", doc="The PairHMM machine-dependent sub-implementation to use for genotype likelihood calculations", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var pair_hmm_sub_implementation: org.broadinstitute.gatk.utils.pairhmm.PairHMM.HMM_SUB_IMPLEMENTATION = _

/**
 * Short name of pair_hmm_sub_implementation
 * @return Short name of pair_hmm_sub_implementation
 */
def pairHMMSub = this.pair_hmm_sub_implementation

/**
 * Short name of pair_hmm_sub_implementation
 * @param value Short name of pair_hmm_sub_implementation
 */
def pairHMMSub_=(value: org.broadinstitute.gatk.utils.pairhmm.PairHMM.HMM_SUB_IMPLEMENTATION) { this.pair_hmm_sub_implementation = value }

/** Load the vector logless PairHMM library each time a GATK run is initiated in the test suite */
@Argument(fullName="always_load_vector_logless_PairHMM_lib", shortName="alwaysloadVectorHMM", doc="Load the vector logless PairHMM library each time a GATK run is initiated in the test suite", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var always_load_vector_logless_PairHMM_lib: Boolean = _

/**
 * Short name of always_load_vector_logless_PairHMM_lib
 * @return Short name of always_load_vector_logless_PairHMM_lib
 */
def alwaysloadVectorHMM = this.always_load_vector_logless_PairHMM_lib

/**
 * Short name of always_load_vector_logless_PairHMM_lib
 * @param value Short name of always_load_vector_logless_PairHMM_lib
 */
def alwaysloadVectorHMM_=(value: Boolean) { this.always_load_vector_logless_PairHMM_lib = value }

/** The global assumed mismapping rate for reads */
@Argument(fullName="phredScaledGlobalReadMismappingRate", shortName="globalMAPQ", doc="The global assumed mismapping rate for reads", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var phredScaledGlobalReadMismappingRate: Option[Int] = None

/**
 * Short name of phredScaledGlobalReadMismappingRate
 * @return Short name of phredScaledGlobalReadMismappingRate
 */
def globalMAPQ = this.phredScaledGlobalReadMismappingRate

/**
 * Short name of phredScaledGlobalReadMismappingRate
 * @param value Short name of phredScaledGlobalReadMismappingRate
 */
def globalMAPQ_=(value: Option[Int]) { this.phredScaledGlobalReadMismappingRate = value }

/** Disable the use of the FPGA HMM implementation */
@Argument(fullName="noFpga", shortName="noFpga", doc="Disable the use of the FPGA HMM implementation", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var noFpga: Boolean = _

/** trace this read name through the calling process */
@Argument(fullName="debug_read_name", shortName="", doc="trace this read name through the calling process", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var debug_read_name: String = _

/** Set an alternate MQ threshold for debugging */
@Argument(fullName="MQ_filtering_level", shortName="MQthreshold", doc="Set an alternate MQ threshold for debugging", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var MQ_filtering_level: Option[Int] = None

/**
 * Short name of MQ_filtering_level
 * @return Short name of MQ_filtering_level
 */
def MQthreshold = this.MQ_filtering_level

/**
 * Short name of MQ_filtering_level
 * @param value Short name of MQ_filtering_level
 */
def MQthreshold_=(value: Option[Int]) { this.MQ_filtering_level = value }

/** VCF file of COSMIC sites */
@Input(fullName="cosmic", shortName="cosmic", doc="VCF file of COSMIC sites", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var cosmic: Seq[File] = Nil

/** Dependencies on any indexes of cosmic */
@Input(fullName="cosmicIndexes", shortName="", doc="Dependencies on any indexes of cosmic", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var cosmicIndexes: Seq[File] = Nil

/** VCF file of sites observed in normal */
@Input(fullName="normal_panel", shortName="PON", doc="VCF file of sites observed in normal", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var normal_panel: Seq[File] = Nil

/**
 * Short name of normal_panel
 * @return Short name of normal_panel
 */
def PON = this.normal_panel

/**
 * Short name of normal_panel
 * @param value Short name of normal_panel
 */
def PON_=(value: Seq[File]) { this.normal_panel = value }

/** Dependencies on any indexes of normal_panel */
@Input(fullName="normal_panelIndexes", shortName="", doc="Dependencies on any indexes of normal_panel", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var normal_panelIndexes: Seq[File] = Nil

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

/** comparison VCF file */
@Input(fullName="comp", shortName="comp", doc="comparison VCF file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var comp: Seq[File] = Nil

/** Dependencies on any indexes of comp */
@Input(fullName="compIndexes", shortName="", doc="Dependencies on any indexes of comp", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
private var compIndexes: Seq[File] = Nil

/** One or more specific annotations to apply to variant calls */
@Argument(fullName="annotation", shortName="A", doc="One or more specific annotations to apply to variant calls", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var annotation: Seq[String] = Nil

/**
 * Short name of annotation
 * @return Short name of annotation
 */
def A = this.annotation

/**
 * Short name of annotation
 * @param value Short name of annotation
 */
def A_=(value: Seq[String]) { this.annotation = value }

/** One or more specific annotations to exclude */
@Argument(fullName="excludeAnnotation", shortName="XA", doc="One or more specific annotations to exclude", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var excludeAnnotation: Seq[String] = Nil

/**
 * Short name of excludeAnnotation
 * @return Short name of excludeAnnotation
 */
def XA = this.excludeAnnotation

/**
 * Short name of excludeAnnotation
 * @param value Short name of excludeAnnotation
 */
def XA_=(value: Seq[String]) { this.excludeAnnotation = value }

/** One or more classes/groups of annotations to apply to variant calls */
@Argument(fullName="group", shortName="G", doc="One or more classes/groups of annotations to apply to variant calls", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var group: Seq[String] = Nil

/**
 * Short name of group
 * @return Short name of group
 */
def G = this.group

/**
 * Short name of group
 * @param value Short name of group
 */
def G_=(value: Seq[String]) { this.group = value }

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

/** If specified, we will not trim down the active region from the full region (active + extension) to just the active interval for genotyping */
@Argument(fullName="dontTrimActiveRegions", shortName="dontTrimActiveRegions", doc="If specified, we will not trim down the active region from the full region (active + extension) to just the active interval for genotyping", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dontTrimActiveRegions: Boolean = _

/** the maximum extent into the full active region extension that we're willing to go in genotyping our events for discovery */
@Argument(fullName="maxDiscARExtension", shortName="maxDiscARExtension", doc="the maximum extent into the full active region extension that we're willing to go in genotyping our events for discovery", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var maxDiscARExtension: Option[Int] = None

/** the maximum extent into the full active region extension that we're willing to go in genotyping our events for GGA mode */
@Argument(fullName="maxGGAARExtension", shortName="maxGGAARExtension", doc="the maximum extent into the full active region extension that we're willing to go in genotyping our events for GGA mode", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var maxGGAARExtension: Option[Int] = None

/** Include at least this many bases around an event for calling indels */
@Argument(fullName="paddingAroundIndels", shortName="paddingAroundIndels", doc="Include at least this many bases around an event for calling indels", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var paddingAroundIndels: Option[Int] = None

/** Include at least this many bases around an event for calling snps */
@Argument(fullName="paddingAroundSNPs", shortName="paddingAroundSNPs", doc="Include at least this many bases around an event for calling snps", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var paddingAroundSNPs: Option[Int] = None

/** Only use read from this read group when making calls (but use all reads to build the assembly) */
@Argument(fullName="keepRG", shortName="keepRG", doc="Only use read from this read group when making calls (but use all reads to build the assembly)", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var keepRG: String = _

/** Minimum base quality required to consider a base for calling */
@Argument(fullName="min_base_quality_score", shortName="mbq", doc="Minimum base quality required to consider a base for calling", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var min_base_quality_score: Option[Byte] = None

/**
 * Short name of min_base_quality_score
 * @return Short name of min_base_quality_score
 */
def mbq = this.min_base_quality_score

/**
 * Short name of min_base_quality_score
 * @param value Short name of min_base_quality_score
 */
def mbq_=(value: Option[Byte]) { this.min_base_quality_score = value }

/** Use an exploratory algorithm to error correct the kmers used during assembly.  May cause fundamental problems with the assembly graph itself */
@Argument(fullName="errorCorrectReads", shortName="errorCorrectReads", doc="Use an exploratory algorithm to error correct the kmers used during assembly.  May cause fundamental problems with the assembly graph itself", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var errorCorrectReads: Boolean = _

/** If specified, we will write a BAM called assemblyFailure.bam capturing all of the reads that were in the active region when the assembler failed for any reason */
@Argument(fullName="captureAssemblyFailureBAM", shortName="captureAssemblyFailureBAM", doc="If specified, we will write a BAM called assemblyFailure.bam capturing all of the reads that were in the active region when the assembler failed for any reason", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var captureAssemblyFailureBAM: Boolean = _

/** If specified, we will not analyze soft clipped bases in the reads */
@Argument(fullName="dontUseSoftClippedBases", shortName="dontUseSoftClippedBases", doc="If specified, we will not analyze soft clipped bases in the reads", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var dontUseSoftClippedBases: Boolean = _

/** If specified, the HC won't actually do any assembly or calling, it'll just run the upfront active region determination code.  Useful for benchmarking and scalability testing */
@Argument(fullName="justDetermineActiveRegions", shortName="justDetermineActiveRegions", doc="If specified, the HC won't actually do any assembly or calling, it'll just run the upfront active region determination code.  Useful for benchmarking and scalability testing", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var justDetermineActiveRegions: Boolean = _

/** Disable physical phasing */
@Argument(fullName="doNotRunPhysicalPhasing", shortName="doNotRunPhysicalPhasing", doc="Disable physical phasing", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var doNotRunPhysicalPhasing: Boolean = _

/** Output the raw activity profile results in IGV format */
@Output(fullName="activityProfileOut", shortName="APO", doc="Output the raw activity profile results in IGV format", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[org.broadinstitute.gatk.queue.function.scattergather.SimpleTextGatherFunction])
var activityProfileOut: File = _

/**
 * Short name of activityProfileOut
 * @return Short name of activityProfileOut
 */
def APO = this.activityProfileOut

/**
 * Short name of activityProfileOut
 * @param value Short name of activityProfileOut
 */
def APO_=(value: File) { this.activityProfileOut = value }

/** Output the active region to this IGV formatted file */
@Output(fullName="activeRegionOut", shortName="ARO", doc="Output the active region to this IGV formatted file", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[org.broadinstitute.gatk.queue.function.scattergather.SimpleTextGatherFunction])
var activeRegionOut: File = _

/**
 * Short name of activeRegionOut
 * @return Short name of activeRegionOut
 */
def ARO = this.activeRegionOut

/**
 * Short name of activeRegionOut
 * @param value Short name of activeRegionOut
 */
def ARO_=(value: File) { this.activeRegionOut = value }

/** Use this interval list file as the active regions to process */
@Input(fullName="activeRegionIn", shortName="AR", doc="Use this interval list file as the active regions to process", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var activeRegionIn: Seq[File] = Nil

/**
 * Short name of activeRegionIn
 * @return Short name of activeRegionIn
 */
def AR = this.activeRegionIn

/**
 * Short name of activeRegionIn
 * @param value Short name of activeRegionIn
 */
def AR_=(value: Seq[File]) { this.activeRegionIn = value }

/** The active region extension; if not provided defaults to Walker annotated default */
@Argument(fullName="activeRegionExtension", shortName="activeRegionExtension", doc="The active region extension; if not provided defaults to Walker annotated default", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var activeRegionExtension: Option[Int] = None

/** If provided, all bases will be tagged as active */
@Argument(fullName="forceActive", shortName="forceActive", doc="If provided, all bases will be tagged as active", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var forceActive: Boolean = _

/** The active region maximum size; if not provided defaults to Walker annotated default */
@Argument(fullName="activeRegionMaxSize", shortName="activeRegionMaxSize", doc="The active region maximum size; if not provided defaults to Walker annotated default", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var activeRegionMaxSize: Option[Int] = None

/** The sigma of the band pass filter Gaussian kernel; if not provided defaults to Walker annotated default */
@Argument(fullName="bandPassSigma", shortName="bandPassSigma", doc="The sigma of the band pass filter Gaussian kernel; if not provided defaults to Walker annotated default", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var bandPassSigma: Option[Double] = None

/** Format string for bandPassSigma */
@Argument(fullName="bandPassSigmaFormat", shortName="", doc="Format string for bandPassSigma", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var bandPassSigmaFormat: String = "%s"

/** Region probability propagation distance beyond it's maximum size. */
@Argument(fullName="maxProbPropagationDistance", shortName="maxProbPropDist", doc="Region probability propagation distance beyond it's maximum size.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var maxProbPropagationDistance: Option[Int] = None

/**
 * Short name of maxProbPropagationDistance
 * @return Short name of maxProbPropagationDistance
 */
def maxProbPropDist = this.maxProbPropagationDistance

/**
 * Short name of maxProbPropagationDistance
 * @param value Short name of maxProbPropagationDistance
 */
def maxProbPropDist_=(value: Option[Int]) { this.maxProbPropagationDistance = value }

/** Threshold for the probability of a profile state being active. */
@Argument(fullName="activeProbabilityThreshold", shortName="ActProbThresh", doc="Threshold for the probability of a profile state being active.", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var activeProbabilityThreshold: Option[Double] = None

/**
 * Short name of activeProbabilityThreshold
 * @return Short name of activeProbabilityThreshold
 */
def ActProbThresh = this.activeProbabilityThreshold

/**
 * Short name of activeProbabilityThreshold
 * @param value Short name of activeProbabilityThreshold
 */
def ActProbThresh_=(value: Option[Double]) { this.activeProbabilityThreshold = value }

/** Format string for activeProbabilityThreshold */
@Argument(fullName="activeProbabilityThresholdFormat", shortName="", doc="Format string for activeProbabilityThreshold", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var activeProbabilityThresholdFormat: String = "%s"

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
if (bamOutput != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(bamOutput))
  if (!disable_bam_indexing)
    bamOutputIndex = new File(bamOutput.getPath.stripSuffix(".bam") + ".bai")
if (bamOutput != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(bamOutput))
  if (generate_md5)
    bamOutputMD5 = new File(bamOutput.getPath + ".md5")
if (alleles != null)
  allelesIndex :+= new File(alleles.getPath + ".idx")
cosmicIndexes ++= cosmic.filter(orig => orig != null && (!orig.getName.endsWith(".list"))).map(orig => new File(orig.getPath + ".idx"))
normal_panelIndexes ++= normal_panel.filter(orig => orig != null && (!orig.getName.endsWith(".list"))).map(orig => new File(orig.getPath + ".idx"))
if (dbsnp != null)
  dbsnpIndex :+= new File(dbsnp.getPath + ".idx")
compIndexes ++= comp.filter(orig => orig != null && (!orig.getName.endsWith(".list"))).map(orig => new File(orig.getPath + ".idx"))
if (out != null && !org.broadinstitute.gatk.utils.io.IOUtils.isSpecialFile(out))
  if (!org.broadinstitute.gatk.utils.commandline.ArgumentTypeDescriptor.isCompressed(out.getPath))
    outIndex = new File(out.getPath + ".idx")
}

override def commandLine = super.commandLine + conditional(m2debug, "-m2debug", escape=true, format="%s") + conditional(artifact_detection_mode, "--artifact_detection_mode", escape=true, format="%s") + optional("--initial_tumor_lod", initial_tumor_lod, spaceSeparated=true, escape=true, format=initial_tumor_lodFormat) + optional("--initial_normal_lod", initial_normal_lod, spaceSeparated=true, escape=true, format=initial_normal_lodFormat) + optional("--tumor_lod", tumor_lod, spaceSeparated=true, escape=true, format=tumor_lodFormat) + optional("--normal_lod", normal_lod, spaceSeparated=true, escape=true, format=normal_lodFormat) + optional("--dbsnp_normal_lod", dbsnp_normal_lod, spaceSeparated=true, escape=true, format=dbsnp_normal_lodFormat) + optional("--max_alt_alleles_in_normal_count", max_alt_alleles_in_normal_count, spaceSeparated=true, escape=true, format="%s") + optional("--max_alt_alleles_in_normal_qscore_sum", max_alt_alleles_in_normal_qscore_sum, spaceSeparated=true, escape=true, format="%s") + optional("--max_alt_allele_in_normal_fraction", max_alt_allele_in_normal_fraction, spaceSeparated=true, escape=true, format=max_alt_allele_in_normal_fractionFormat) + conditional(debug, "-debug", escape=true, format="%s") + conditional(useFilteredReadsForAnnotations, "-useFilteredReadsForAnnotations", escape=true, format="%s") + optional("-ERC", emitRefConfidence, spaceSeparated=true, escape=true, format="%s") + optional("-bamout", bamOutput, spaceSeparated=true, escape=true, format="%s") + optional("-bamWriterType", bamWriterType, spaceSeparated=true, escape=true, format="%s") + conditional(emitDroppedReads, "-edr", escape=true, format="%s") + conditional(disableOptimizations, "-disableOptimizations", escape=true, format="%s") + conditional(annotateNDA, "-nda", escape=true, format="%s") + optional("-hets", heterozygosity, spaceSeparated=true, escape=true, format=heterozygosityFormat) + optional("-indelHeterozygosity", indel_heterozygosity, spaceSeparated=true, escape=true, format=indel_heterozygosityFormat) + optional("-stand_call_conf", standard_min_confidence_threshold_for_calling, spaceSeparated=true, escape=true, format=standard_min_confidence_threshold_for_callingFormat) + optional("-stand_emit_conf", standard_min_confidence_threshold_for_emitting, spaceSeparated=true, escape=true, format=standard_min_confidence_threshold_for_emittingFormat) + optional("-maxAltAlleles", max_alternate_alleles, spaceSeparated=true, escape=true, format="%s") + optional("-maxNumPLValues", max_num_PL_values, spaceSeparated=true, escape=true, format="%s") + repeat("-inputPrior", input_prior, spaceSeparated=true, escape=true, format="%s") + optional("-ploidy", sample_ploidy, spaceSeparated=true, escape=true, format="%s") + optional("-gt_mode", genotyping_mode, spaceSeparated=true, escape=true, format="%s") + optional(TaggedFile.formatCommandLineParameter("-alleles", alleles), alleles, spaceSeparated=true, escape=true, format="%s") + optional("-contamination", contamination_fraction_to_filter, spaceSeparated=true, escape=true, format=contamination_fraction_to_filterFormat) + optional("-contaminationFile", contamination_fraction_per_sample_file, spaceSeparated=true, escape=true, format="%s") + optional("-pnrm", p_nonref_model, spaceSeparated=true, escape=true, format="%s") + optional("-logExactCalls", exactcallslog, spaceSeparated=true, escape=true, format="%s") + optional("-out_mode", output_mode, spaceSeparated=true, escape=true, format="%s") + conditional(allSitePLs, "-allSitePLs", escape=true, format="%s") + repeat("-kmerSize", kmerSize, spaceSeparated=true, escape=true, format="%s") + conditional(dontIncreaseKmerSizesForCycles, "-dontIncreaseKmerSizesForCycles", escape=true, format="%s") + conditional(allowNonUniqueKmersInRef, "-allowNonUniqueKmersInRef", escape=true, format="%s") + optional("-numPruningSamples", numPruningSamples, spaceSeparated=true, escape=true, format="%s") + conditional(doNotRecoverDanglingBranches, "-doNotRecoverDanglingBranches", escape=true, format="%s") + optional("-minDanglingBranchLength", minDanglingBranchLength, spaceSeparated=true, escape=true, format="%s") + conditional(consensus, "-consensus", escape=true, format="%s") + optional("-maxNumHaplotypesInPopulation", maxNumHaplotypesInPopulation, spaceSeparated=true, escape=true, format="%s") + conditional(errorCorrectKmers, "-errorCorrectKmers", escape=true, format="%s") + optional("-minPruning", minPruning, spaceSeparated=true, escape=true, format="%s") + conditional(debugGraphTransformations, "-debugGraphTransformations", escape=true, format="%s") + conditional(allowCyclesInKmerGraphToGeneratePaths, "-allowCyclesInKmerGraphToGeneratePaths", escape=true, format="%s") + optional("-graph", graphOutput, spaceSeparated=true, escape=true, format="%s") + optional("-kmerLengthForReadErrorCorrection", kmerLengthForReadErrorCorrection, spaceSeparated=true, escape=true, format="%s") + optional("-minObservationsForKmerToBeSolid", minObservationsForKmerToBeSolid, spaceSeparated=true, escape=true, format="%s") + optional("-gcpHMM", gcpHMM, spaceSeparated=true, escape=true, format="%s") + optional("-pairHMM", pair_hmm_implementation, spaceSeparated=true, escape=true, format="%s") + optional("-pairHMMSub", pair_hmm_sub_implementation, spaceSeparated=true, escape=true, format="%s") + conditional(always_load_vector_logless_PairHMM_lib, "-alwaysloadVectorHMM", escape=true, format="%s") + optional("-globalMAPQ", phredScaledGlobalReadMismappingRate, spaceSeparated=true, escape=true, format="%s") + conditional(noFpga, "-noFpga", escape=true, format="%s") + optional("--debug_read_name", debug_read_name, spaceSeparated=true, escape=true, format="%s") + optional("-MQthreshold", MQ_filtering_level, spaceSeparated=true, escape=true, format="%s") + repeat("-cosmic", cosmic, formatPrefix=TaggedFile.formatCommandLineParameter, spaceSeparated=true, escape=true, format="%s") + repeat("-PON", normal_panel, formatPrefix=TaggedFile.formatCommandLineParameter, spaceSeparated=true, escape=true, format="%s") + optional(TaggedFile.formatCommandLineParameter("-D", dbsnp), dbsnp, spaceSeparated=true, escape=true, format="%s") + repeat("-comp", comp, formatPrefix=TaggedFile.formatCommandLineParameter, spaceSeparated=true, escape=true, format="%s") + repeat("-A", annotation, spaceSeparated=true, escape=true, format="%s") + repeat("-XA", excludeAnnotation, spaceSeparated=true, escape=true, format="%s") + repeat("-G", group, spaceSeparated=true, escape=true, format="%s") + optional("-o", out, spaceSeparated=true, escape=true, format="%s") + conditional(dontTrimActiveRegions, "-dontTrimActiveRegions", escape=true, format="%s") + optional("-maxDiscARExtension", maxDiscARExtension, spaceSeparated=true, escape=true, format="%s") + optional("-maxGGAARExtension", maxGGAARExtension, spaceSeparated=true, escape=true, format="%s") + optional("-paddingAroundIndels", paddingAroundIndels, spaceSeparated=true, escape=true, format="%s") + optional("-paddingAroundSNPs", paddingAroundSNPs, spaceSeparated=true, escape=true, format="%s") + optional("-keepRG", keepRG, spaceSeparated=true, escape=true, format="%s") + optional("-mbq", min_base_quality_score, spaceSeparated=true, escape=true, format="%s") + conditional(errorCorrectReads, "-errorCorrectReads", escape=true, format="%s") + conditional(captureAssemblyFailureBAM, "-captureAssemblyFailureBAM", escape=true, format="%s") + conditional(dontUseSoftClippedBases, "-dontUseSoftClippedBases", escape=true, format="%s") + conditional(justDetermineActiveRegions, "-justDetermineActiveRegions", escape=true, format="%s") + conditional(doNotRunPhysicalPhasing, "-doNotRunPhysicalPhasing", escape=true, format="%s") + optional("-APO", activityProfileOut, spaceSeparated=true, escape=true, format="%s") + optional("-ARO", activeRegionOut, spaceSeparated=true, escape=true, format="%s") + repeat("-AR", activeRegionIn, spaceSeparated=true, escape=true, format="%s") + optional("-activeRegionExtension", activeRegionExtension, spaceSeparated=true, escape=true, format="%s") + conditional(forceActive, "-forceActive", escape=true, format="%s") + optional("-activeRegionMaxSize", activeRegionMaxSize, spaceSeparated=true, escape=true, format="%s") + optional("-bandPassSigma", bandPassSigma, spaceSeparated=true, escape=true, format=bandPassSigmaFormat) + optional("-maxProbPropDist", maxProbPropagationDistance, spaceSeparated=true, escape=true, format="%s") + optional("-ActProbThresh", activeProbabilityThreshold, spaceSeparated=true, escape=true, format=activeProbabilityThresholdFormat) + conditional(filter_reads_with_N_cigar, "-filterRNC", escape=true, format="%s") + conditional(filter_mismatching_base_and_quals, "-filterMBQ", escape=true, format="%s") + conditional(filter_bases_not_stored, "-filterNoBases", escape=true, format="%s")
}
