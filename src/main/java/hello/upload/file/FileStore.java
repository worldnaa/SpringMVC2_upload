package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //파일 여러개 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                //파일 한개 저장하는 메서드 호출 후 리스트에 담기
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    //파일 한개 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); //고객이 저장한 파일명
        String storeFileName = createStoreFileName(originalFilename);  //서버에 저장할 파일명

        multipartFile.transferTo(new File(getFullPath(storeFileName))); //파일 저장

        return new UploadFile(originalFilename, storeFileName);

    }

    //서버에 저장할 파일명을 만드는 메서드
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    //파일 확장명 가져오는 메서드 (파일명 예시 - SpringLogo.png)
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf("."); //.이 찍힌 위치 리턴
        return originalFilename.substring(pos + 1); //. 다음에 있는 값 리턴
    }
}
