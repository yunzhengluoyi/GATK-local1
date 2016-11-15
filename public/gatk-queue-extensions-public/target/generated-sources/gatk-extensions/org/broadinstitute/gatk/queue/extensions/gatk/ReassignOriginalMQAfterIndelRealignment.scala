package org.broadinstitute.gatk.queue.extensions.gatk

trait ReassignOriginalMQAfterIndelRealignment extends org.broadinstitute.gatk.queue.function.CommandLineFunction {

abstract override def commandLine = super.commandLine + required("--read_filter", "ReassignOriginalMQAfterIndelRealignment")
}
