package com.javaee.exercises.reminders.business.todos.boundary.batch;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Dependent
@Named("ToDoItemReader")
public class ToDoItemReader extends AbstractItemReader {
    private static final String UPLOAD_DIRECTORY = "upload_directory";
    private static final Logger logger = Logger.getLogger(ToDoItemReader.class.getName());
    @Inject
    private JobContext jobContext;
    private ToDoFilesCheckpoint checkpoint;
    private RandomAccessFile currentFile;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        File uploadDirectory = new File(
                jobContext.getProperties().getProperty(UPLOAD_DIRECTORY));
        if (checkpoint == null) {
            this.checkpoint = new ToDoFilesCheckpoint();
            logger.log(Level.INFO, "Scanning upload directory: {0}", uploadDirectory);
            if (!uploadDirectory.exists()) {
                logger.log(Level.INFO, "Upload directory does not exist, creating it");
                uploadDirectory.mkdirs();
            } else {
                this.checkpoint.setFiles(Arrays.asList(uploadDirectory.listFiles()));
            }
        } else {
            logger.log(Level.INFO, "Starting from previous checkpoint");
            this.checkpoint = (ToDoFilesCheckpoint) checkpoint;
        }
        File file = this.checkpoint.currentFile();
        if (file == null) {
            logger.log(Level.INFO, "No files to process");
            currentFile = null;
        } else {
            currentFile = new RandomAccessFile(file, "r");
            logger.log(Level.INFO, "Processing file: {0}", file);
            currentFile.seek(this.checkpoint.getFilePointer());
        }
    }

    @Override
    public Object readItem() throws Exception {
        if (currentFile != null) {
            String line = currentFile.readLine();
            if (line != null) {
                this.checkpoint.setFilePointer(currentFile.getFilePointer());
                return parseLine(line);
            } else {
                logger.log(Level.INFO, "Finished processing file, deleting: {0}", this.checkpoint.currentFile());
                currentFile.close();
                this.checkpoint.currentFile().delete();
                File nextFile = this.checkpoint.nextFile();
                if (nextFile == null) {
                    logger.log(Level.INFO, "No more files to process");
                    return null;
                } else {
                    currentFile = new RandomAccessFile(nextFile, "r");
                    logger.log(Level.INFO, "Processing file: {0}", nextFile);
                    return readItem();
                }
            }
        } else {
            return null;
        }
    }

    private Object parseLine(String line) throws ToDoLineParseException {
        String[] result = line.split(",");
        if (result.length != 3) {
            throw new ToDoLineParseException("Wrong number of data elements", line);
        }
        ToDo todo = new ToDo();
        todo.setCaption(result[0]);
        todo.setDescription(result[1]);
        todo.setPriority(Integer.valueOf(result[2]));
        return todo;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return this.checkpoint;
    }
}