package com.javaee.exercises.reminders.business.todos.boundary.batch;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.batch.operations.JobOperator;
import jakarta.batch.runtime.BatchRuntime;

@Stateless
public class UploadDirectoryScanner {
    @Schedule(minute = "*/2", hour = "*", persistent = false)
    public void processFiles() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        jobOperator.start("file-processor", null);
    }
}