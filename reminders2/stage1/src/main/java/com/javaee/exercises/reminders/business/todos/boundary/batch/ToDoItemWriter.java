package com.javaee.exercises.reminders.business.todos.boundary.batch;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Dependent
@Named("ToDoItemWriter")
public class ToDoItemWriter extends AbstractItemWriter {
    private static final String ARCHIVE_DIRECTORY = "archive_directory";
    @Inject
    private JobContext jobContext;
    @PersistenceContext
    private EntityManager em;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        File archiveDirectory = new File(jobContext.getProperties().getProperty(ARCHIVE_DIRECTORY));
        if (!archiveDirectory.exists()) {
            archiveDirectory.mkdirs();
        }
    }

    @Override
    @Transactional
    public void writeItems(List<Object> items) throws Exception {
        try (PrintWriter archive = new PrintWriter(new BufferedWriter(new FileWriter(
                new File(jobContext.getProperties().getProperty(ARCHIVE_DIRECTORY) + "/archive_" + jobContext.getJobName() + "_" + jobContext.getInstanceId() + " .csv"), true)))) {
            for (Object item : items) {
                ToDo todo = (ToDo) item;
                this.em.persist(todo);
                archive.println(todo.getCaption() + ", " + todo.getDescription() + "," + todo.getPriority());
            }
        }
    }
}