package com.example.administrator.lemondaodemo.dataModel;


import com.example.lemonlibrary.db.annotion.DbField;
import com.example.lemonlibrary.db.annotion.DbPrimaryField;
import com.example.lemonlibrary.db.annotion.DbTable;

/**
 * Created by ShuWen on 2017/2/9.
 */
@DbTable(value = "tb_file")
public class FileModel {

    @DbPrimaryField(value = "id")
    private int id;
    @DbField(value = "tb_filename")
    private String fileName;
    @DbField(value = "tb_filepath")
    private String filePath;
    @DbField(value = "tb_fileid")
    private int fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
