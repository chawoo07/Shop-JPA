package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter @Setter
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; //イメージファイル名

    private String oriImgName; //原本いめーじファイル名

    private String imgUrl;  //イメージ紹介経路

    private String repimgYn;  //代表イメージ存在

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){

        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        //原本イメージファイル名、アップデートするイメージ名、イメージ経路をパラメータに入力されて情報をアップデート
    }

}
