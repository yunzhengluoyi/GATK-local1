package org.broadinstitute.gatk.queue.extensions.gatk

import java.io.File
import org.broadinstitute.gatk.utils.commandline.Argument
import org.broadinstitute.gatk.utils.commandline.Gather
import org.broadinstitute.gatk.utils.commandline.Output

class PrintBAMRegion extends org.broadinstitute.gatk.queue.function.JavaCommandLineFunction {
analysisName = "PrintBAMRegion"
javaMainClass = "org.broadinstitute.gatk.engine.datasources.reads.utilities.PrintBAMRegion"

/** Input file to process */
@Argument(fullName="input", shortName="I", doc="Input file to process", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
var input: File = _

/**
 * Short name of input
 * @return Short name of input
 */
def I = this.input

/**
 * Short name of input
 * @param value Short name of input
 */
def I_=(value: File) { this.input = value }

/** BAM region to process, in chunk format (mmmm:nn-xxxx:yy) */
@Argument(fullName="region", shortName="R", doc="BAM region to process, in chunk format (mmmm:nn-xxxx:yy)", required=true, exclusiveOf="", otherArgumentRequired="", validation="")
var region: String = _

/**
 * Short name of region
 * @return Short name of region
 */
def R = this.region

/**
 * Short name of region
 * @param value Short name of region
 */
def R_=(value: String) { this.region = value }

/** Set the minimum level of logging */
@Argument(fullName="logging_level", shortName="l", doc="Set the minimum level of logging", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var logging_level: String = _

/**
 * Short name of logging_level
 * @return Short name of logging_level
 */
def l = this.logging_level

/**
 * Short name of logging_level
 * @param value Short name of logging_level
 */
def l_=(value: String) { this.logging_level = value }

/** Set the logging location */
@Output(fullName="log_to_file", shortName="log", doc="Set the logging location", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
@Gather(classOf[org.broadinstitute.gatk.queue.function.scattergather.SimpleTextGatherFunction])
var log_to_file: File = _

/**
 * Short name of log_to_file
 * @return Short name of log_to_file
 */
def log = this.log_to_file

/**
 * Short name of log_to_file
 * @param value Short name of log_to_file
 */
def log_=(value: File) { this.log_to_file = value }

/** Generate the help message */
@Argument(fullName="help", shortName="h", doc="Generate the help message", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var help: Boolean = _

/**
 * Short name of help
 * @return Short name of help
 */
def h = this.help

/**
 * Short name of help
 * @param value Short name of help
 */
def h_=(value: Boolean) { this.help = value }

/** Output version information */
@Argument(fullName="version", shortName="version", doc="Output version information", required=false, exclusiveOf="", otherArgumentRequired="", validation="")
var version: Boolean = _

override def commandLine = super.commandLine + required("-I", input, spaceSeparated=true, escape=true, format="%s") + required("-R", region, spaceSeparated=true, escape=true, format="%s") + optional("-l", logging_level, spaceSeparated=true, escape=true, format="%s") + optional("-log", log_to_file, spaceSeparated=true, escape=true, format="%s") + conditional(help, "-h", escape=true, format="%s") + conditional(version, "-version", escape=true, format="%s")
}
