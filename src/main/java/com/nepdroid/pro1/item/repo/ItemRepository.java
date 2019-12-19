package com.nepdroid.pro1.item.repo;

import com.nepdroid.pro1.item.entity.Item;
import com.nepdroid.pro1.item.projection.ItemDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findByCategoryId(int catId);

    // global
    @Query(value = "SELECT * FROM tbl_item where item_name like ?1", nativeQuery = true)
    List<Item> searchItemsByItemName(String itemName);

    // only for seller
    @Query(value = "SELECT * FROM tbl_item where item_name like ?1 and fk_user_id = ?2", nativeQuery = true)
    List<Item> searchItemsByItemNameAndUserId(String itemName, int userId);
    List<Item> findByUserId(int userId);

    @Query(value="select item_name as itemName,\n" +
            "description ,\n" +
            "image_url as imageUrl,\n" +
            "price, \n" +
            "negotable,\n" +
            "user_name as uploadeBy\n" +
            "from tbl_item i\n" +
            "inner join user u\n" +
            "on i.fk_user_id = u.user_id" +
            "where item_id = ?1", nativeQuery = true)
    ItemDetailsProjection selectItemDetailsWithUserName(int itemId);
}
