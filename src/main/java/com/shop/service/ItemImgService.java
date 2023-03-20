package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile)
            throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";


        //ファイルのアップロード
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    itemImgFile.getBytes());    //アップロードしたら貯蔵されたファイルの名前をimgName変数に貯蔵
            imgUrl = "/images/item/" + imgName; //貯蔵した商品のイメージをロードする経路を設定
        }

        //商品イメージ情報貯蔵
        itemImg.updateItemImg(oriImgName, imgName, imgUrl); //imgName : 実際貯蔵された商品のイメージファイルの名前
                                                            //oriImgName : アップロードした商品イメージファイルの元の名前
                                                            //imgUrl : アップロード結果貯蔵された商品イメージファイルをロードする経路
        itemImgRepository.save(itemImg);


    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile)
        throws Exception{
        if(!itemImgFile.isEmpty()){                                         //商品のイメージを修正して場合アップデータ
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)    //商品のイメージIDを利用して既存の商品イメージEntityを紹介
                    .orElseThrow(EntityNotFoundException::new);

            //既存のイメージファイル削除
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {           //既存の登録された商品のファイルがあった場合該当ファイルを消す
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getOriImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation,
                    oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" +imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

        }
    }
}
