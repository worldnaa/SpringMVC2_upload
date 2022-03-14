package hello.upload.controller;

import hello.upload.domain.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data //상품 저장용 폼
public class ItemForm {

    private Long itemId;
    private String itemName;
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles; //이미지 다중 업로드를 위해 MultipartFile 를 사용

}
