package org.bigcompany;

import org.bigcompany.exception.ValidationException;
import org.bigcompany.processor.EmployeeReportProcessor;


public class EmployeeAnalysisApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new ValidationException("No input file provided. Please pass the path to the input file as an argument.");
        }
        new EmployeeReportProcessor().processEmployeeReport(args[0]);
    }
}