package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";     //商品の必須値がなかったら商品登録ペイジへ

        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {    //最初のイメージがなかったらエラーメセージと登録ペイジへ
            model.addAttribute("errorMessage", "最初の商品イメージは必須です。");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "商品登録にエラーが発生しました。");
            return "item/itemForm";
        }

        return "redirect:/";    //まともにできたらメインペイジへ

    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);   //紹介した商品データをmodelに込めてviewに送る
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {                             //存在しない場合
            model.addAttribute("errorMessage", "存在しない商品です。");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }


    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto,
                             BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile>
                                         itemImgFileList, Model model) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

            if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
                model.addAttribute("errorMessage", "最初の商品のイメージは必須です。");
                return "item/itemForm";
            }

            try {
                itemService.updateItem(itemFormDto, itemImgFileList);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "商品の修正中にエラーが発生しました。");
                return "item/itemForm";
            }


        return "redirect:/";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})        //valueに商品管理ページに入る時、番号がないのとある時の二つマッピング
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);   //1: 紹介するページ番号　2: 一気にとるデータ数

        Page<Item> items =
                itemService.getAdminItemPage(itemSearchDto, pageable);   //紹介条件とページの情報をパラメータで送ってPage<Item> オブジェクトを貰う
        model.addAttribute("items", items);                        //Viewに伝達
        model.addAttribute("itemSearchDto", itemSearchDto);        //ページ転換の時既存検索条件を維持したまま移動できるようVIEWniまた送る
        model.addAttribute("maxPage", 5);             //ページ番号のマキシマム
        return "item/itemMng";

    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }

}
