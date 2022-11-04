package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; //고객 업로드 파일명
    private String storeFileName; //서버 저장 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
