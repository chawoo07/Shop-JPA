package com.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();  //UUIDは互いに違う個体を区別するために名前を与える、実際に使う時重複する可能性がないのでファイル名で使ったら重複問題を解決できる
        String extension = originalFileName.substring(originalFileName
                .lastIndexOf("."));
        String savedFileName = uuid.toString() + extension; //UUIDでもらった値と元の名前の拡張子を総合して貯蔵するファイルの名を作る
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); //ファイルの出力stream
        fos.write(fileData);    //fileDataを出力Streamに入力
        fos.close();
        return savedFileName;   //アップロードされたファイルの名前をreturn
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);   //ファイルが貯蔵された経路を利用してファイルのオブジェクトを生成

        if(deleteFile.exists()){    //該当ファイルが存在したら削除
            log.info("ファイルを削除しました。");
        }else {
            log.info("フィイルが存在しません。");
        }
    }

}
